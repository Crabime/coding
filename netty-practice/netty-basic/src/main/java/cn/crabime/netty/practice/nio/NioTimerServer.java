package cn.crabime.netty.practice.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class NioTimerServer {

    public static void main(String[] args) {
        int port = 8080;

        MultiplexerTimerServer timeServer = new MultiplexerTimerServer(port);
        new Thread(timeServer).start();
    }

    private static class MultiplexerTimerServer implements Runnable {

        private int port;

        private ServerSocketChannel serverSocketChannel;

        private Selector selector;

        private volatile boolean stop;

        public MultiplexerTimerServer(int port) {
            try {
                this.port = port;

                // 创建多路复用器
                selector = Selector.open();

                // 打开ServerSocketChannel，用于监听客户端连接，所有客户端连接的父管道
                serverSocketChannel = ServerSocketChannel.open();

                // 设置为非阻塞模式
                serverSocketChannel.configureBlocking(false);

                // 绑定端口
                serverSocketChannel.socket().bind(new InetSocketAddress(this.port));

                // 将ServerSocketChannel注册到Reactor线程的多路复用器上，监听ACCEPT事件
                serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            while (!stop) {
                try {
                    // selector多路复用器休眠时间为1s，每隔1s被唤醒一次
                    this.selector.select(1000);

                    // selector多路复用器返回该Channel就绪的SelectionKey。
                    // 通过对就绪的Channel集合进行迭代，可以进行异步的网络读写操作
                    Set<SelectionKey> selectionKeys = this.selector.selectedKeys();

                    Iterator<SelectionKey> it = selectionKeys.iterator();

                    SelectionKey key = null;

                    while (it.hasNext()) {
                        key = it.next();
                        it.remove();

                        // 处理当前轮询到的I/O线程，处理I/O事件
                        handleInput(key);

                        // 在多路复用器上取消该I/O线程的事件注册
                        key.cancel();
                        if (key.channel() != null)
                            key.channel().close();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // 如果多路复用器关闭后，所有注册在上面的Channel和Pipe等资源均会自动去注册并关闭，所以不需要重复释放资源
            if (selector != null) {
                try {
                    selector.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void stop() {
            this.stop = true;
        }

        private void handleInput(SelectionKey key) throws IOException {

            if (key.isValid()) {
                // 处理新接入的请求消息
                if (key.isAcceptable()) {

                    ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);

                    // 在多路复用器上添加一个新的连接
                    sc.register(this.selector, SelectionKey.OP_READ);
                }

                if (key.isReadable()) {
                    SocketChannel sc = (SocketChannel) key.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

                    // 通过SocketChannel读取字节缓冲区内的字节
                    int readBytes = sc.read(byteBuffer);
                    if (readBytes > 0) {
                        byteBuffer.flip();

                        // 拿到缓冲区中剩余的字节
                        byte[] bytes = new byte[byteBuffer.remaining()];

                        byteBuffer.get(bytes);
                        String body = new String(bytes, "UTF-8");

                        System.out.println("The time server receive order : " + body);

                        String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date().toString() : "BAD ORDER";

                        doWrite(sc, currentTime);
                    } else if (readBytes < 0) {
                        // 对端链路关闭
                        key.cancel();
                        sc.close();
                    } {
                        ; // 读到0字节，忽略
                    }
                }
            }
        }

        public void doWrite(SocketChannel sc, String resp) throws IOException {
            if (resp != null && resp.trim().length() > 0) {
                byte[] bytes = resp.getBytes();
                ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
                writeBuffer.put(bytes);
                writeBuffer.flip();
                sc.write(writeBuffer);
            }
        }
    }
}
