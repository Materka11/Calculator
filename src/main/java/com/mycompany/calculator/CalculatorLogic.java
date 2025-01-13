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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class CalculatorLogic implements ActionListener {
    private final JTextField display;
    private final DecimalFormat df = new DecimalFormat("0.00");

    private String currentInput = "";
    private double firstNumber = 0;
    private String operator = "";
    private boolean isNewOperation = true;
    private String divisionByZeroMessage = "Error! Dzielenie przez zero.";
    private String unknownOperatorMessage = "Nieznany error z operatorem";

    public CalculatorLogic(JTextField display) {
        this.display = display;
    }

    public void setDivisionByZeroMessage(String message) {
        this.divisionByZeroMessage = message;
    }
    
    public void setUnknownOperatorMessage(String message) {
        this.unknownOperatorMessage = message;
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
            default -> display.setText(unknownOperatorMessage);
        }
        display.setText(df.format(firstNumber));
        currentInput = String.valueOf(firstNumber);
    }
}

