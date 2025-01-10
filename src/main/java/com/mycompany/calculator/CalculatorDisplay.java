/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.calculator;

/**
 *
 * @author Home
 */
import javax.swing.*;
import java.awt.*;

public class CalculatorDisplay extends JFrame {
    private final JTextField display;
    private final JPanel panel;
    private final CalculatorLogic logic;
    private final CalculatorPreferences preferencesPanel;

    public CalculatorDisplay() {
        setTitle("Kalkulator");
        setSize(350, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Preferencje");
        JMenuItem preferencesItem = new JMenuItem("Otwórz panel preferencji");
        preferencesItem.addActionListener(e -> togglePreferencesPanel());
        menu.add(preferencesItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        display = new JTextField();
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.PLAIN, 24));
        add(display, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new BorderLayout());
        panel = new JPanel(new GridLayout(4, 4, 5, 5));
        logic = new CalculatorLogic(display);

        String[] buttons = {"7", "8", "9", "C", "4", "5", "6", "*", "1", "2", "3", "-", "0", "=", "+", "/"};
        for (String button : buttons) {
            JButton btn = new JButton(button);
            btn.setPreferredSize(new Dimension(80, 80));
            btn.addActionListener(logic);
            panel.add(btn);
        }
        mainPanel.add(panel, BorderLayout.CENTER);

        preferencesPanel = new CalculatorPreferences(logic, this);
        mainPanel.add(preferencesPanel, BorderLayout.EAST);

        add(mainPanel, BorderLayout.CENTER);
    }

    private void togglePreferencesPanel() {
        preferencesPanel.setVisible(!preferencesPanel.isVisible());
        int newWidth = preferencesPanel.isVisible() ? getWidth() + preferencesPanel.getPreferredSize().width : getWidth() - preferencesPanel.getPreferredSize().width;
        setSize(newWidth, getHeight());
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalculatorDisplay calculator = new CalculatorDisplay();
            calculator.setVisible(true);
        });
    }
}

