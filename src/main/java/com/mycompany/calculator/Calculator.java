/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.calculator;

/**
 *
 * @author salaA_11
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class Calculator extends JFrame implements ActionListener {
    private final JTextField display;
    private final JPanel panel;
    private final JPanel settingsPanel;
    private final String[] buttons = {
            "7", "8", "9", "C", 
            "4", "5", "6", "*", 
            "1", "2", "3", "-",
            "0", "=", "+", "/",
    };
    private final DecimalFormat df = new DecimalFormat("0.00");
    
    private String currentInput = "";
    private double firstNumber = 0;
    private String operator = "";
    private boolean isNewOperation = true;
    private String divisionByZeroMessage = "Error! Dzielenie przez zero.";
    

    public Calculator() {
        setTitle("Kalkulator");
        setSize(350, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Preferencje");
        JMenuItem preferencesItem = new JMenuItem("Otwórz panel preferencji");
        preferencesItem.addActionListener(e -> toggleSettingsPanel());

        menu.add(preferencesItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        display = new JTextField();
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.PLAIN, 24));
        add(display, BorderLayout.NORTH);
        
        JPanel mainPanel = new JPanel(new BorderLayout());

        panel = new JPanel();
        panel.setLayout(new GridLayout(5, 4, 5, 5));
        for (String button : buttons) {
            JButton btn = new JButton(button);
            btn.setPreferredSize(new Dimension(80, 80));
            btn.addActionListener(e -> actionPerformed(e));
            panel.add(btn);
        }
        mainPanel.add(panel, BorderLayout.CENTER);
        
        settingsPanel = new JPanel();
        settingsPanel
                .setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));
        settingsPanel.setPreferredSize(new Dimension(250, getHeight()));
        settingsPanel
                .setBorder(BorderFactory.createTitledBorder("Preferencje"));
        settingsPanel.setVisible(false);
        settingsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel selectLabel = new JLabel("Komunikat przy dzieleniu przez zero:");
        String[] messages = {
            "Error! Dzielenie przez zero.",
            "Nie można dzielić przez zero!",
            "Błąd: dzielenie przez zero."
        };
        JComboBox<String> messageSelector = new JComboBox<>(messages);
        messageSelector.setSelectedItem(divisionByZeroMessage);
        messageSelector.setPreferredSize(new Dimension(180, 30)); 
        messageSelector.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        settingsPanel.add(selectLabel);
        settingsPanel.add(messageSelector);

        JButton saveButton = new JButton("Zapisz");
        saveButton.addActionListener(e -> {
            divisionByZeroMessage = (String) messageSelector.getSelectedItem();
            settingsPanel.setVisible(false);
            int newWidth = getWidth() - settingsPanel.getPreferredSize().width;
            setSize(newWidth, getHeight());
            revalidate();
            repaint();
        });
        settingsPanel.add(saveButton);
        
        mainPanel.add(settingsPanel, BorderLayout.EAST);
        add(mainPanel, BorderLayout.CENTER);
    }

     private void toggleSettingsPanel() {
        settingsPanel.setVisible(!settingsPanel.isVisible());
        int newWidth = 
                settingsPanel.isVisible() ? 
                getWidth() + settingsPanel.getPreferredSize().width : 
                getWidth() - settingsPanel.getPreferredSize().width;
        setSize(newWidth, getHeight());
        revalidate();
        repaint();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.charAt(0) == 'C') {
            display.setText("");
            currentInput = "";
            firstNumber = 0;
            operator = "";
            isNewOperation = true;
        } else if (command.charAt(0) == '=') {
            if (!currentInput.isEmpty() && !operator.isEmpty()) {
                double secondNumber = Double.parseDouble(currentInput);
                calculateResult(secondNumber);
                operator = ""; 
                isNewOperation = true;
            }
        } else if ("+-*/".contains(command)) {
            if (!currentInput.isEmpty()) {
                if (!operator.isEmpty()) {
                    double secondNumber = Double.parseDouble(currentInput);
                    calculateResult(secondNumber);
                } else {
                    firstNumber = Double.parseDouble(currentInput);
                }
            }
            operator = command;
            isNewOperation = true;
        } else {
            if (isNewOperation) {
                currentInput = "";
                isNewOperation = false;
            }
            currentInput += command;
            display.setText(currentInput);
        }
    }


    private void calculateResult(double secondNumber) {
        switch (operator) {
            case "+" -> firstNumber += secondNumber;
            case "-" -> firstNumber -= secondNumber;
            case "*" -> firstNumber *= secondNumber;
            case "/" -> {
                if (secondNumber != 0) {
                    firstNumber /= secondNumber;
                } else {
                    display.setText(divisionByZeroMessage);
                    currentInput = "";
                    return;
                }
            }
            default -> display.setText("Nieznany error z operatorem");
        }
        display.setText(df.format(firstNumber));
        currentInput = String.valueOf(firstNumber);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Calculator calculator = new Calculator();
            calculator.setVisible(true); 
        });
    }
}


