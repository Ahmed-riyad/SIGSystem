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


public class InvoCall extends JDialog {
    private JTextField custNametxt;
    private JTextField datetxt;
    private JLabel cusNameJlbl;
    private JLabel invoDateJlbl;
    private JButton addBt;
    private JButton cancelBt;

    public InvoCall(InvoMainFrame frame) {
        initComponents();
        
        Toolkit toolkit=getToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width/2 - getWidth()/2, size.height/2 - getHeight()/2);
        
        cusNameJlbl = new JLabel("Customer Name:");
        custNametxt = new JTextField(20);
        invoDateJlbl = new JLabel("Invoice Date:");
        datetxt = new JTextField(20);
        addBt = new JButton("OK");
        cancelBt = new JButton("Cancel");
        
        addBt.setActionCommand("invoiceAdded");
        cancelBt.setActionCommand("invoiceDelete");
        
        addBt.addActionListener(frame.getController());
        cancelBt.addActionListener(frame.getController());
        setLayout(new GridLayout(3, 2));
        
        add(invoDateJlbl);
        add(datetxt);
        add(cusNameJlbl);
        add(custNametxt);
        add(addBt);
        add(cancelBt);
        
        pack();
        
    }

    public JTextField getCustNameField() {
        return custNametxt;
    }

    public JTextField getInvDateField() {
        return datetxt;
    }

    private void initComponents() {
         }
    
}
