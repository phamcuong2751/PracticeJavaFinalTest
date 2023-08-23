package org.example.qlhs;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientApp extends JFrame {
    private JTextArea logArea;

    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public ClientApp() {
        setTitle("Student List - Client");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        logArea = new JTextArea(20, 50);
        logArea.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(logArea);

        add(logScrollPane, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);

        connectToServer();
        retrieveStudentList();
    }

    private void connectToServer() {
        try {
            socket = new Socket("127.0.0.1", 12345);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            logArea.append("Error connecting to the server: " + e.getMessage() + "\n");
        }
    }

    private void retrieveStudentList() {
        writer.println("GET_STUDENT_LIST");
        try {
            String response = reader.readLine();
            if (response.equals("STUDENT_LIST_START")) {
                List<String> studentList = new ArrayList<>();
                String studentInfo;
                while (!(studentInfo = reader.readLine()).equals("STUDENT_LIST_END")) {
                    studentList.add(studentInfo);
                }
                displayStudentList(studentList);
            }
        } catch (IOException e) {
            logArea.append("Error retrieving student list: " + e.getMessage() + "\n");
        }
    }

    private void displayStudentList(List<String> studentList) {
        for (String studentInfo : studentList) {
            logArea.append(studentInfo + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ClientApp app = new ClientApp();
            app.setVisible(true);
        });
    }
}
