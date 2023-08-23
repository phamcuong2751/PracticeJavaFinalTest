package org.example;

import org.example.cau1.URLDownloaderApp;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            URLDownloaderApp app = new URLDownloaderApp();
            app.setVisible(true);
        });
    }
}