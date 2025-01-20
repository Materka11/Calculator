package com.mycompany.calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class Calculator extends JFrame implements ActionListener {
    private final JTextField display;
    private final JPanel panel;
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

    public Calculator() {
        setTitle("Kalkulator");
        // Zmieniamy rozmiar okna na taki sam jak CalculatorDisplay
        setSize(350, 400);  // Ustawienie rozmiaru okna
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        display = new JTextField();
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.PLAIN, 24));
        add(display, BorderLayout.NORTH);

        panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4, 5, 5)); // Ustawienie siatki przycisków

        // Ustawiamy rozmiar przycisków na taki sam jak w CalculatorDisplay
        for (String button : buttons) {
            JButton btn = new JButton(button);
            btn.setPreferredSize(new Dimension(80, 80));  // Ustawienie rozmiaru przycisku
            btn.addActionListener(e -> actionPerformed(e));
            panel.add(btn);
        }
        add(panel, BorderLayout.CENTER);
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
                operator = "";  // Reset operator after calculation
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
                    currentInput = "";
                    return;
                }
            }
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
