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
    private final JScrollPane preferencesScrollPane;
    private final JMenuBar menuBar;
    private final JMenu menu;
    private final JMenuItem preferencesItem;

    public CalculatorDisplay() {
        setTitle("Kalkulator");
        setSize(350, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        addWindowStateListener(e -> {
            if ((e.getNewState() & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH) {
                showFullscreenMessage();
            }
        });

        menuBar = new JMenuBar();
        menu = new JMenu("Preferencje");
        preferencesItem = new JMenuItem("Otwórz panel preferencji");
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

        String[] buttons = {
            "7", "8", "9", "C", "4",
            "5", "6", "*", "1", "2",
            "3", "-", "0", "=", "+",
            "/"
        };
        for (String button : buttons) {
            JButton btn = new JButton(button);
            btn.setPreferredSize(new Dimension(80, 80));
            btn.addActionListener(logic);
            panel.add(btn);
        }
        mainPanel.add(panel, BorderLayout.CENTER);

        preferencesPanel = new CalculatorPreferences(logic, this, display);
        preferencesScrollPane = new JScrollPane(preferencesPanel);
        preferencesScrollPane
                .setHorizontalScrollBarPolicy(
                        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
                );
        preferencesScrollPane
                .setVerticalScrollBarPolicy(
                        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
                );
        preferencesScrollPane.setPreferredSize(new Dimension(250, 0));
        preferencesScrollPane.setVisible(false);
        
        preferencesPanel.setPreferencesScrollPane(preferencesScrollPane);

        mainPanel.add(preferencesScrollPane, BorderLayout.EAST);
        add(mainPanel, BorderLayout.CENTER);
    }

    private void togglePreferencesPanel() {
        boolean isVisible = preferencesScrollPane.isVisible();
        preferencesScrollPane.setVisible(!isVisible);

        int panelWidth = preferencesScrollPane.getPreferredSize().width;
        int newWidth = isVisible ? 
                getWidth() - panelWidth : 
                getWidth() + panelWidth;

        setSize(newWidth, getHeight());
        revalidate();
        repaint();
    }
    
    public void updateGrid(int rows, int cols) {
        panel.removeAll();
        panel.setLayout(new GridLayout(rows, cols, 5, 5));

        String[] buttons;
 
        if (rows == 4) {
            buttons = new String[]{
                "7", "8", "9", "C",
                "4", "5", "6", "*",
                "1", "2", "3", "-", 
                "0", "=", "+", "/"
            };
        } else if (rows == 8) {
            buttons = new String[]{
                "C", "*", 
                "-", "+",
                "/", "9", 
                "8", "7", 
                "6", "5",
                "4", "3", 
                "2", "1", 
                "0", "="
            };
        } else {
            buttons = new String[]{
                "7", "8", "9", "C",
                "4", "5", "6", "*",
                "1", "2", "3", "-", 
                "0", "=", "+", "/"
            };
        }

        for (String button : buttons) {
            JButton btn = new JButton(button);
            btn.setPreferredSize(new Dimension(80, 80));
            btn.addActionListener(logic);
            panel.add(btn);
        }

        panel.revalidate();
        panel.repaint();
    }
    
     private void showFullscreenMessage() {
        JOptionPane.showMessageDialog(this, logic.getFullscreenMessage());
    }

    public void updateButtonColors(Color backgroundColor, Color foregroundColor) {
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JButton button) {
                button.setBackground(backgroundColor);
                button.setForeground(foregroundColor);
            }
        }

        panel.revalidate();
        panel.repaint();
    }
    
    public void setDisplayFont(Font font) { 
        display.setFont(font); 
        display.revalidate(); 
        display.repaint(); 
    }
    
    public void enableFullscreenMode() {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        JOptionPane.showMessageDialog(
            this, logic.getFullscreenMessage()
        );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalculatorDisplay calculator = new CalculatorDisplay();
            calculator.setVisible(true);
        });
    }
}

