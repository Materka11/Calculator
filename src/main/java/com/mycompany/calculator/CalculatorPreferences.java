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

public class CalculatorPreferences extends JPanel {
    public CalculatorPreferences(CalculatorLogic logic) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(250, 0));
        setBorder(BorderFactory.createTitledBorder("Preferencje"));
        setVisible(false);

        JLabel selectLabel = new JLabel("Komunikat przy dzieleniu przez zero:");
        String[] messages = {
            "Error! Dzielenie przez zero.",
            "Nie można dzielić przez zero!",
            "Błąd: dzielenie przez zero."
        };
        JComboBox<String> messageSelector = new JComboBox<>(messages);
        messageSelector.setSelectedItem("Error! Dzielenie przez zero.");
        messageSelector.setPreferredSize(new Dimension(180, 30));
        messageSelector.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        add(selectLabel);
        add(messageSelector);

        JButton saveButton = new JButton("Zapisz");
        saveButton.addActionListener(e -> logic.setDivisionByZeroMessage((String) messageSelector.getSelectedItem()));
        add(saveButton);
    }
}

