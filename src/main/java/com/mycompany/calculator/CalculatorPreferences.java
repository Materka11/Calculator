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
    private final MessageSelectorPanel messageSelectorPanelFontsStyle;
    private final MessageSelectorPanel messageSelectorPanelButtonsColor;
    private final MessageSelectorPanel messageSelectorPanelFullscreen;
    private final MessageSelectorPanel messageSelectorPanelDecimalFormat;

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
                new String[] {"wierszy: 4, kolumn: 4", "wierszy: 8, kolumn: 2"},
                "wierszy: 4, kolumn: 4"
        );
         this.messageSelectorPanelFontsStyle = new MessageSelectorPanel(
                "Styl czcionek",
                new String[] {"PLAIN", "BOLD"},
                "PLAIN"
        );
        this.messageSelectorPanelButtonsColor = new MessageSelectorPanel(
                "Kolor przycisków", 
                new String[] {"Windows Color", "Czarny"}, 
                "Windows Color"
        );
       
        this.messageSelectorPanelFullscreen = new MessageSelectorPanel(
            "Komunikat o trybie pełnoekranowym:", 
            new String[]{
                "Tryb pełnoekranowy włączony.",
                "Pełny ekran aktywowany!",
                "Tryb pełnoekranowy został uruchomiony."
            },
            "Tryb pełnoekranowy włączony."
        );
        
        this.messageSelectorPanelDecimalFormat = new MessageSelectorPanel(
                "Liczba miejsc po przecinku:", 
                new String[] {"2", "3", "4"}, 
                "2"
        );

        setLayout(new FlowLayout(FlowLayout.LEFT));
        setPreferredSize(new Dimension(250, 700));
        setBorder(BorderFactory.createTitledBorder("Preferencje"));

        add(this.messageSelectorPanelDivisionByZero);
        add(this.messageSelectorPanelFonts);
        add(this.messageSelectorPanelSize);
        add(this.messageSelectorPanelOperator);
        add(this.messageSelectorPanelGrid);
        add(this.messageSelectorPanelFontsStyle);
        add(this.messageSelectorPanelButtonsColor);
        add(this.messageSelectorPanelFullscreen);
        add(this.messageSelectorPanelDecimalFormat);
       

        JButton saveButton = new JButton("Zapisz");
        saveButton.addActionListener(e -> {
            System.out.println(this.messageSelectorPanelDecimalFormat.getSelectedMessage());
            this.logic.setDivisionByZeroMessage(
                    this.messageSelectorPanelDivisionByZero
                            .getSelectedMessage()
            );
            
            int fontStyle;
            switch (this.messageSelectorPanelFontsStyle.getSelectedMessage()) {
                case "BOLD":
                    fontStyle = Font.BOLD;
                    break;
                case "ITALIC":
                    fontStyle = Font.ITALIC;
                    break;
                case "PLAIN":
                default:
                    fontStyle = Font.PLAIN;
                    break;
            }
            Font newFont = new Font(
                this.messageSelectorPanelFonts.getSelectedMessage(),
                fontStyle,
                Integer.parseInt(this.messageSelectorPanelSize
                        .getSelectedMessage())
            );
            this.display.setFont(newFont);
            
            this.logic.setUnknownOperatorMessage(
                    this.messageSelectorPanelOperator.getSelectedMessage()
            );
            
            try {
                String gridSelection = 
                        this.messageSelectorPanelGrid.getSelectedMessage();

                if (
                    !(gridSelection.contains("wierszy") 
                        && gridSelection.contains("kolumn")) &&
                    !(gridSelection.contains("rows") 
                        && gridSelection.contains("cols"))
                ) {
                    throw new IllegalArgumentException(
                        "Niepoprawny format wyboru siatki: " + gridSelection
                    );
                }

                String[] parts = gridSelection.split(",");
                if (parts.length == 2) {
                    String[] rowPart = parts[0].split(": ");
                    String[] colPart = parts[1].split(": ");
                    if (rowPart.length == 2 && colPart.length == 2) {
                        int rows = Integer.parseInt(rowPart[1].trim());
                        int cols = Integer.parseInt(colPart[1].trim());

                        if (
                             this.parentFrame instanceof CalculatorDisplay calculatorDisplay
                            ) {
                            calculatorDisplay.updateGrid(rows, cols);
                        }
                    } else {
                        throw new IllegalArgumentException(
                                "Niepoprawny format części: " + gridSelection);
                    }
                } else {
                    throw new IllegalArgumentException(
                            "Niepoprawny format gridSelection: " + gridSelection);
                }
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(
                        this, "Błąd przetwarzania siatki: " + ex.getMessage()
                );
            }
            
            String selectedColor = this.messageSelectorPanelButtonsColor
                    .getSelectedMessage();
            Color backgroundColor;
            Color foregroundColor = Color.WHITE;

            switch (selectedColor) {
                case "Czarny":
                    backgroundColor = Color.BLACK;
                    break;
                case "Windows Color":
                default:
                    backgroundColor = UIManager.getColor("Button.background");
                    foregroundColor = UIManager.getColor("Button.foreground");
                    break;
            }
            if (this.parentFrame instanceof CalculatorDisplay calculatorDisplay) {
                    calculatorDisplay
                        .updateButtonColors(backgroundColor, foregroundColor);
            }
            
            this.logic.setFullscreenMessage(
                this.messageSelectorPanelFullscreen.getSelectedMessage()
            );
            
            String selectedValue = messageSelectorPanelDecimalFormat.getSelectedMessage();

            if (selectedValue == null) {
                System.err.println("Wybrano wartość null, ignoruję.");
                return;
            }

            try {
                int decimalPlaces = Integer.parseInt(selectedValue);
                StringBuilder pattern = new StringBuilder("0.");
                for (int i = 0; i < decimalPlaces; i++) {
                    pattern.append("0");
                }

                logic.setDecimalFormat(pattern.toString());
                System.out.println("Zaktualizowano format na: " + pattern);
            } catch (NumberFormatException ex) {
                System.err.println("Nieprawidłowa wartość: " + selectedValue);
            }
            
            int panelWidth = this.getPreferredSize().width;
            boolean isPreferencesVisible = 
                    this.preferencesScrollPane.isVisible();
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

