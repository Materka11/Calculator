/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.calculator;

/**
 *
 * @author Home
 */
import java.awt.event.ActionEvent;
import java.text.DecimalFormatSymbols;

public class CalculatorLogicTest {

    public static void runTest(CalculatorLogic calculator, String first, String op, String second, String expected, String testName) {
        char decimalSeparator = new DecimalFormatSymbols().getDecimalSeparator();
        String localizedExpected = expected.replace('.', decimalSeparator);

    
        calculator.actionPerformed(new TestActionEvent(first));
        calculator.actionPerformed(new TestActionEvent(op));
        calculator.actionPerformed(new TestActionEvent(second));
        calculator.actionPerformed(new TestActionEvent("="));

        String result = calculator.getDisplayText();

        if (result.equals(localizedExpected)) {
            System.out.println("Test '" + testName + "' przeszedl pomyslnie. Otrzymano: " + result);
        } else {
            System.out.println("Test '" + testName + "' nie przeszedl. Oczekiwano: " + localizedExpected + ", Otrzymano: " + result);
        }
    }

    // Klasa pomocnicza do symulowania zdarzeń
    private static class TestActionEvent extends ActionEvent {
        public TestActionEvent(String command) {
            super(new Object(), ActionEvent.ACTION_PERFORMED, command);
        }
    }
}

