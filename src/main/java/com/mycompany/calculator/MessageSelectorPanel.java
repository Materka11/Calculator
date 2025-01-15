/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.calculator;

/**
 *
 * @author salaA_11
 */
import javax.swing.*;
import java.awt.*;

public class MessageSelectorPanel extends JPanel {
    private final JLabel selectLabel;
    private final JComboBox<String> messageSelector;

    public MessageSelectorPanel(String label, String[] messages, String selectedItem) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        selectLabel = new JLabel(label);
        selectLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        messageSelector = new JComboBox<>(messages);
        messageSelector.setSelectedItem(selectedItem);
        messageSelector.setPreferredSize(new Dimension(180, 30));
        messageSelector.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 30)
        );
        messageSelector.setAlignmentX(Component.LEFT_ALIGNMENT);

        add(selectLabel);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(messageSelector);
    }

    public String getSelectedMessage() {
        return (String) messageSelector.getSelectedItem();
    }
    
    public void updateLabel(String newLabel) {
        this.selectLabel.setText(newLabel);
        revalidate();
        repaint();
    }
    
    public void updateOptions(String[] newMessages, String selectedItem) {
        messageSelector.removeAllItems();
        for (String message : newMessages) {
            messageSelector.addItem(message);
        }
        messageSelector.setSelectedItem(selectedItem);
        revalidate();
        repaint();
    }
}

