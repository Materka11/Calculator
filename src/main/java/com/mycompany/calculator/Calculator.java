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

public class Calculator extends JFrame implements ActionListener {
    private final JTextField display;
    private final JPanel panel;
    private final String[] buttons = {
            "7", "8", "9", "C", 
            "4", "5", "6", "*", 
            "1", "2", "3", "-",
            "0", "=", "+", "/",
    };
    private String currentInput = "";
    private double firstNumber = 0;
    private String operator = "";
    private boolean isNewOperation = true;

    public Calculator() {
        setTitle("Kalkulator");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        display = new JTextField();
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.PLAIN, 24));
        add(display, BorderLayout.NORTH);

        panel = new JPanel();
        panel.setLayout(new GridLayout(5, 4, 10, 10));
        for (String button : buttons) {
            JButton btn = new JButton(button);
            btn.setFont(new Font("Arial", Font.PLAIN, 24));
            btn.addActionListener(this);
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
            double secondNumber = Double.parseDouble(currentInput);
            calculateResult(secondNumber);
            operator = "";
            isNewOperation = true;
        } else if ("+-*/".contains(command)) {
            if (!operator.isEmpty()) {
                double secondNumber = Double.parseDouble(currentInput);
                calculateResult(secondNumber);
            } else {
                firstNumber = Double.parseDouble(currentInput);
            }
            operator = command;
            currentInput = "";
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
            case "+":
                firstNumber += secondNumber;
                break;
            case "-":
                firstNumber -= secondNumber;
                break;
            case "*":
                firstNumber *= secondNumber;
                break;
            case "/":
                if (secondNumber != 0) {
                    firstNumber /= secondNumber;
                } else {
                    display.setText("Error! Dzielenie przez zero.");
                    return;
                }
                break;
            default:
                display.setText("Nieznany error z operatorem");
        }
        display.setText(String.valueOf(firstNumber));
        currentInput = String.valueOf(firstNumber);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Calculator calculator = new Calculator();
            calculator.setVisible(true); 
        });
    }
}


