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
    private final MessageSelectorPanel messageSelectorPanelDivisionByZero;
    private final MessageSelectorPanel messageSelectorPanelFonts;
    private final MessageSelectorPanel messageSelectorPanelSize;

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

        setLayout(new FlowLayout(FlowLayout.LEFT));
        setPreferredSize(new Dimension(250, 0));
        setBorder(BorderFactory.createTitledBorder("Preferencje"));
        setVisible(false);

        add(this.messageSelectorPanelDivisionByZero);
        add(this.messageSelectorPanelFonts);
        add(this.messageSelectorPanelSize);

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
                    Integer.parseInt(this.messageSelectorPanelSize.getSelectedMessage())
            ); 
            this.display.setFont(newFont);
            setVisible(false);
            this.parentFrame.setSize(
                    this.parentFrame.getWidth() - getPreferredSize().width,
                    this.parentFrame.getHeight());
            this.parentFrame.revalidate();
            this.parentFrame.repaint();
        });
        add(saveButton);
    }
}

