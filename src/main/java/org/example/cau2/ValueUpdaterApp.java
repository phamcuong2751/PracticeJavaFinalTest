package org.example.cau2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ValueUpdaterApp extends JFrame {
    private int value = 0;
    private int delta = 1;
    private boolean isRunning = false;
    private Timer timer;

    private JLabel valueLabel;
    private JLabel deltaLabel;
    private JButton addButton;
    private JButton runButton;
    private JButton pauseButton;
    private JButton resetButton;

    public ValueUpdaterApp() {
        setTitle("Value Updater");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        valueLabel = new JLabel("Value: " + value);
        deltaLabel = new JLabel("Delta: " + delta);
        addButton = new JButton("Add");
        runButton = new JButton("Run");
        pauseButton = new JButton("Pause");
        resetButton = new JButton("Reset");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                delta++;
                updateDeltaLabel();
            }
        });

        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isRunning) {
                    isRunning = true;
                    timer.start();
//                    resetButton.setEnabled(false);
                }
            }
        });

        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isRunning) {
                    isRunning = false;
                    timer.stop();
//                    resetButton.setEnabled(true);
                }
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isRunning = false;
                timer.stop();
                value = 0;
                delta = 1;
                updateValueLabel();
                updateDeltaLabel();
            }
        });

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                value += delta;
                updateValueLabel();
            }
        });

        add(valueLabel);
        add(deltaLabel);
        add(addButton);
        add(runButton);
        add(pauseButton);
        add(resetButton);

        pack();
        setLocationRelativeTo(null);
    }

    private void updateValueLabel() {
        valueLabel.setText("Value: " + value);
    }

    private void updateDeltaLabel() {
        deltaLabel.setText("Delta: " + delta);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ValueUpdaterApp app = new ValueUpdaterApp();
            app.setVisible(true);
        });
    }
}
/***************************************MONOTOR***************************************************/