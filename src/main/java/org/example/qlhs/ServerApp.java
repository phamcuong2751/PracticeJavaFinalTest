package org.example.qlhs;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ServerApp {
    private static final List<String> studentList = new ArrayList<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            while (true) {
                Socket socket = serverSocket.accept();
                new ClientHandler(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private BufferedReader reader;
        private PrintWriter writer;

        public ClientHandler(Socket socket) {
            this.socket = socket;
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new PrintWriter(socket.getOutputStream(), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                String request = reader.readLine();
                if (request.equals("GET_STUDENT_LIST")) {
                    sendStudentList();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void sendStudentList() {
            writer.println("STUDENT_LIST_START");
            for (String studentInfo : studentList) {
                writer.println(studentInfo);
            }
            writer.println("STUDENT_LIST_END");
        }
    }
}

