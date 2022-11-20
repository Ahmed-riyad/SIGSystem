/**
 *
 * @author Ahmed Riyad
 */

package com.sig.frame;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class LineCall extends JDialog{
    private JTextField itemNameField;
    private JTextField itemCountField;
    private JTextField itemPriceField;
    private JLabel itemNameLbl;
    private JLabel itemCountLbl;
    private JLabel itemPriceLbl;
    private JButton okBtn;
    private JButton cancelBtn;
    
    public LineCall(InvoMainFrame frame) {
        initComponents();
        
        Toolkit toolkit=getToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width/2 - getWidth()/2, size.height/2 - getHeight()/2);
        
        itemNameField = new JTextField(20);
        itemNameLbl = new JLabel("Item Name");
        
        itemCountField = new JTextField(20);
        itemCountLbl = new JLabel("Item Count");
        
        itemPriceField = new JTextField(20);
        itemPriceLbl = new JLabel("Item Price");
        
        okBtn = new JButton("OK");
        cancelBtn = new JButton("Cancel");
        
        okBtn.setActionCommand("lineAdded");
        cancelBtn.setActionCommand("lineCanceld");
        
        okBtn.addActionListener(frame.getController());
        cancelBtn.addActionListener(frame.getController());
        setLayout(new GridLayout(4, 2));
        
        
        
        add(itemNameLbl);
        add(itemNameField);
        add(itemCountLbl);
        add(itemCountField);
        add(itemPriceLbl);
        add(itemPriceField);
        add(okBtn);
        add(cancelBtn);
        
        pack();
        
    }
    
    

    public JTextField getItemNameField() {
        return itemNameField;
    }

    public JTextField getItemCountField() {
        return itemCountField;
    }

    public JTextField getItemPriceField() {
        return itemPriceField;
    }

    private void initComponents() {
        }
}
