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
    private final JTextField display;
    private JScrollPane preferencesScrollPane;
    private final MessageSelectorPanel messageSelectorPanelDivisionByZero;
    private final MessageSelectorPanel messageSelectorPanelFonts;
    private final MessageSelectorPanel messageSelectorPanelSize;
    private final MessageSelectorPanel messageSelectorPanelOperator;
    private final MessageSelectorPanel messageSelectorPanelGrid;

    public CalculatorPreferences(
            CalculatorLogic logic, 
            JFrame parentFrame, 
            JTextField display
    ) {
        this.logic = logic;
        this.parentFrame = parentFrame;
        this.display = display;
        this.messageSelectorPanelDivisionByZero = new MessageSelectorPanel(
                "Komunikat przy dzieleniu przez zero:", 
                new String[]{ 
                    "Error! Dzielenie przez zero.", 
                    "Nie można dzielić przez zero!", 
                    "Błąd: dzielenie przez zero." 
                },
                "Error! Dzielenie przez zero."
        );
        this.messageSelectorPanelFonts = new MessageSelectorPanel(
                "Czcionka", 
                new String[] {"Arial", "Times New Roman"},
                "Arial"
        );
        this.messageSelectorPanelSize = new MessageSelectorPanel(
                "Rozmiar czcionki",
                new String[] {"24", "18"},
                "24"
        );
        this.messageSelectorPanelOperator = new MessageSelectorPanel(
                "Komunikat przy nieznanym operatorze",
                new String[] {"Nieznany error z operatorem", "Nieznany error"},
                "Nieznany error z operatorem"
        );
        this.messageSelectorPanelGrid = new MessageSelectorPanel(
                "Siatka przycisków w programie",
                new String[] {"wierszy: 5, kolumn: 4", "wieszy 4, kolumn: 5"},
                "wierszy: 5, kolumn: 4"
        );

        setLayout(new FlowLayout(FlowLayout.LEFT));
        setPreferredSize(new Dimension(250, 400));
        setBorder(BorderFactory.createTitledBorder("Preferencje"));

        add(this.messageSelectorPanelDivisionByZero);
        add(this.messageSelectorPanelFonts);
        add(this.messageSelectorPanelSize);
        add(this.messageSelectorPanelOperator);
        add(this.messageSelectorPanelGrid);

        JButton saveButton = new JButton("Zapisz");
        saveButton.addActionListener(e -> {
            this.logic.setDivisionByZeroMessage(
                    this.messageSelectorPanelDivisionByZero
                            .getSelectedMessage()
            );
            Font currentFont = this.display.getFont(); 
            Font newFont = new Font(
                    this.messageSelectorPanelFonts.getSelectedMessage(), 
                    currentFont.getStyle(), 
                    Integer.parseInt(
                            this.messageSelectorPanelSize.getSelectedMessage()
                    )
            ); 
            this.logic.setUnknownOperatorMessage(
                    this.messageSelectorPanelOperator.getSelectedMessage()
            );
            this.display.setFont(newFont);
            int panelWidth = this.getPreferredSize().width;
            boolean isPreferencesVisible = this.preferencesScrollPane.isVisible();
            this.preferencesScrollPane.setVisible(!isPreferencesVisible);

            if (isPreferencesVisible) {
                this.parentFrame.setSize(
                    this.parentFrame.getWidth() - panelWidth,
                    this.parentFrame.getHeight()
                );
            } else {
                this.parentFrame.setSize(
                    this.parentFrame.getWidth() + panelWidth,
                    this.parentFrame.getHeight()
                );
            }

            this.parentFrame.revalidate();
            this.parentFrame.repaint();
        });
        add(saveButton);
    }
    
    public void setPreferencesScrollPane(JScrollPane scrollPane) {
        this.preferencesScrollPane = scrollPane;
    }
}

