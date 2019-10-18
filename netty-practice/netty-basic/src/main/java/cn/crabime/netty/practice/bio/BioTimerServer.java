package cn.crabime.netty.practice.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class BioTimerServer {

    private static class TimerHandler implements Runnable {
        private Socket socket;

        public TimerHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            BufferedReader reader = null;
            PrintWriter writer = null;
            try {
                reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                writer = new PrintWriter(this.socket.getOutputStream(), true);

                while (true) {
                    String body = reader.readLine();
                    if (body == null) {
                        break;
                    }
                    System.out.println("Timer server receive : " + body);
                    String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date().toString() : "BAD ORDER";
                    writer.println(currentTime);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    reader = null;
                }
                if (writer != null) {
                    writer.close();
                    writer = null;
                }
            }
        }
    }

    public static void main(String[] args) {
        int port = 8080;
        ServerSocket serverSocket = null;
        try {
            Socket socket = null;
            serverSocket = new ServerSocket(port);
            while (true) {
                socket = serverSocket.accept();

                new Thread(new TimerHandler(socket)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                serverSocket = null;
            }
        }

    }
}
