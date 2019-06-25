package cn.crabime.netty.practice.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

// TODO: 2019/6/25 处理服务端发送过来的resp消息
public class NioTimerClient {

    private static class TimerClientHandler implements Runnable {

        private String host;

        private int port;

        private Selector selector;

        private SocketChannel socketChannel;

        private volatile boolean stop;

        public TimerClientHandler(String host, int port) {
            this.host = host;
            this.port = port;
            try {
                // 初始化多路复用器
                this.selector = Selector.open();

                // 打开Socket通道，并设置为异步非阻塞模式
                this.socketChannel = SocketChannel.open();
                this.socketChannel.configureBlocking(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            doConnect();

            while (!stop) {
                try {
                    this.selector.select(1000);
                    Set<SelectionKey> selectionKeys = this.selector.selectedKeys();
                    Iterator<SelectionKey> it = selectionKeys.iterator();
                    SelectionKey key = null;

                    while (it.hasNext()) {
                        key = it.next();
                        it.remove();

                        // 当有就绪的Channel时，执行handleInput处理
                        handleInput(key);

                        if (key != null) {
                            key.cancel();
                            if (key.channel() != null) {
                                key.channel().close();
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (selector != null) {
                try {
                    this.selector.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void handleInput(SelectionKey key) throws IOException {
            if (key.isValid()) {
                SocketChannel sc = (SocketChannel) key.channel();

                // 如果处于连接状态，说明服务端返回了ACK应答消息，这时需要对连接结果进行判断
                if (key.isConnectable()) {

                    // 判断TCP连接结果，返回true，说明连接成功
                    if (sc.finishConnect()) {

                        // 将SocketChannel注册到多路复用器上，监听网络读操作
                        sc.register(this.selector, SelectionKey.OP_READ);
                        doWrite(sc);
                    } else {
                        // 连接失败
                        System.exit(1);
                    }

                    if (key.isReadable()) {
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        int readBytes = sc.read(byteBuffer);
                        if (readBytes > 0) {

                            byteBuffer.flip();
                            byte[] bytes = new byte[byteBuffer.remaining()];
                            byteBuffer.get(bytes);

                            String body = new String(bytes, "UTF-8");
                            System.out.println("Now is : " + body);
                            this.stop = true;
                        } else if (readBytes < 0) {
                            key.cancel();
                            sc.close();
                        } else {
                            ;
                        }
                    }
                }
            }
        }

        public void stop() {
            this.stop = true;
        }

        private void doConnect() {
            try {
                // 如果能直接连接成功，直接注册到多路复用器Selector上，注册OP_READ操作
                if (socketChannel.connect(new InetSocketAddress(this.host, this.port))) {
                    this.socketChannel.register(this.selector, SelectionKey.OP_READ);
                    doWrite(this.socketChannel);
                } else  {

                    // 如果没有成功，说明服务端没有返回TCP握手应答消息，但这并不代表失败
                    // 我们需要将SocketChannel注册到多路复用器Selector上，注册OP_CONNECT事件
                    // 当服务端返回sync-ack消息后，Selector就能轮询到这个SocketChannel处于就绪状态
                    this.socketChannel.register(this.selector, SelectionKey.OP_CONNECT);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void doWrite(SocketChannel sc) throws IOException {
            byte[] req = "QUERY TIME ORDER".getBytes();
            ByteBuffer byteBuffer = ByteBuffer.allocate(req.length);
            byteBuffer.put(req);
            byteBuffer.flip();
            sc.write(byteBuffer);

            // 防止写入TCP"半包写"问题
            if (!byteBuffer.hasRemaining()) {
                System.out.println("Send to server successfully");
            }
        }
    }

    public static void main(String[] args) {
        int port = 8080;

        new Thread(new TimerClientHandler("127.0.0.1", port)).start();
    }
}
