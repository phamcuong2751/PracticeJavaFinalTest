package org.example.cau1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLDownloaderApp extends JFrame {
    private JTextField urlField;
    private JTextArea contentArea;
    private JButton downloadButton;
    private JButton saveButton;
    private JTextArea linksArea;

    public URLDownloaderApp() {
        setTitle("URL Downloader with Links");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        urlField = new JTextField(40);
        contentArea = new JTextArea(20, 60);
        contentArea.setEditable(false);
        JScrollPane contentScrollPane = new JScrollPane(contentArea);

        downloadButton = new JButton("Download");
        downloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                downloadContent();
            }
        });

        saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveContent();
            }
        });

        linksArea = new JTextArea(10, 60);
        linksArea.setEditable(false);
        JScrollPane linksScrollPane = new JScrollPane(linksArea);

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("URL: "));
        inputPanel.add(urlField);
        inputPanel.add(downloadButton);
        inputPanel.add(saveButton);

        add(inputPanel, BorderLayout.NORTH);
        add(contentScrollPane, BorderLayout.CENTER);
        add(linksScrollPane, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    private void downloadContent() {
        String urlString = urlField.getText();

        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                StringBuilder content = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }

                reader.close();
                connection.disconnect();

                contentArea.setText(content.toString());
                extractAndDisplayLinks(content.toString());
            } else {
                contentArea.setText("Error: " + responseCode);
            }
        } catch (IOException e) {
            contentArea.setText("Error: " + e.getMessage());
        }
    }

    private void saveContent() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (PrintWriter writer = new PrintWriter(selectedFile)) {
                writer.write(contentArea.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void extractAndDisplayLinks(String content) {
        List<String> links = new ArrayList<String>();
        Pattern pattern = Pattern.compile("href=[\"\']([^\"\']+)[\"\']");
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            links.add(matcher.group(1));
        }

        StringBuilder linksText = new StringBuilder();
        for (String link : links) {
            linksText.append(link).append("\n");
        }

        linksArea.setText(linksText.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            URLDownloaderApp app = new URLDownloaderApp();
            app.setVisible(true);
        });
    }
}
