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
    private final CalculatorLogic logic;
    private final JFrame parentFrame;

    public CalculatorPreferences(CalculatorLogic logic, JFrame parentFrame) {
        this.logic = logic;
        this.parentFrame = parentFrame;

        setLayout(new FlowLayout(FlowLayout.LEFT));
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
        saveButton.addActionListener(e -> {
            logic.setDivisionByZeroMessage((String) messageSelector.getSelectedItem());
            setVisible(false);
            this.parentFrame.setSize(this.parentFrame.getWidth() - getPreferredSize().width, this.parentFrame.getHeight());
            this.parentFrame.revalidate();
            this.parentFrame.repaint();
        });
        add(saveButton);
    }
}

