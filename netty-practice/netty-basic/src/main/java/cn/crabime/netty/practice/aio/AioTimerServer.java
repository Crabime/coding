package cn.crabime.netty.practice.aio;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

public class AioTimerServer {

    private static class AsyncTimerServerHandler implements Runnable {
        private int port;

        private CountDownLatch latch;

        private AsynchronousServerSocketChannel serverSocketChannel;

        public AsyncTimerServerHandler(int port) {
            this.port = port;
            try {
                // 打开AIO异步ServerSocket通道
                serverSocketChannel = AsynchronousServerSocketChannel.open();
                serverSocketChannel.bind(new InetSocketAddress(this.port));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            latch = new CountDownLatch(1);
            doAccept();
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void doAccept() {
            serverSocketChannel.accept(this, new AcceptCompletionHandler());
        }
    }

    private static class AcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, AsyncTimerServerHandler> {

        @Override
        public void completed(AsynchronousSocketChannel result, AsyncTimerServerHandler attachment) {
            attachment.serverSocketChannel.accept(attachment, this);
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            result.read(buffer, buffer, new ReadCompletionHandler(result));
        }

        @Override
        public void failed(Throwable exc, AsyncTimerServerHandler attachment) {
            exc.printStackTrace();
            attachment.latch.countDown();
        }
    }

    private static class ReadCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {

        private AsynchronousSocketChannel socketChannel;

        public ReadCompletionHandler(AsynchronousSocketChannel socketChannel) {
            this.socketChannel = socketChannel;
        }

        @Override
        public void completed(Integer result, ByteBuffer attachment) {
            attachment.flip();
            byte[] body = new byte[attachment.remaining()];
            attachment.get(body);
            try {
                String req = new String(body, "UTF-8");
                System.out.println("The time server receive order : " + req);

                String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(req) ? new Date().toString() : "BAD ORDER";
                doWrite(currentTime);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void failed(Throwable exc, ByteBuffer attachment) {
            try {
                this.socketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        public void doWrite(String currentTime) {
            if (currentTime != null && currentTime.trim().length() > 0) {
                byte[] bytes = currentTime.getBytes();
                ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
                writeBuffer.put(bytes);
                writeBuffer.flip();
                this.socketChannel.write(writeBuffer, writeBuffer, new CompletionHandler<Integer, ByteBuffer>() {
                    @Override
                    public void completed(Integer result, ByteBuffer attachment) {
                        // 如果还有报没有发送完，继续发送
                        if (attachment.hasRemaining()) {
                            socketChannel.write(attachment, attachment, this);
                        }
                    }

                    @Override
                    public void failed(Throwable exc, ByteBuffer attachment) {
                        try {
                            socketChannel.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }

    public static void main(String[] args) {
        int port = 8080;

        new Thread(new AsyncTimerServerHandler(port)).start();
    }
}
