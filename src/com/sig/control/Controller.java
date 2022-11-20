/**
 *
 * @author Ahmed Riyad
 */

package com.sig.control;

import com.sig.pack.invo;
import com.sig.pack.invoTbPack;
import com.sig.pack.AdLines;
import com.sig.pack.LinesTbPack;
import com.sig.frame.InvoCall;
import com.sig.frame.InvoMainFrame;
import com.sig.frame.LineCall;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Controller implements ActionListener, ListSelectionListener {

    private InvoMainFrame frame;
    private InvoCall invoCall;
    private LineCall lineCall;

    public Controller(InvoMainFrame frame) {
        this.frame = frame;
    }

    @Override
    // Adding Action Comands Massage
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        System.out.println("Action: " + actionCommand);
        switch (actionCommand) {
            case "Load File":
                loadFile();
                break;
            case "Save File":
                saveFile();
                break;
            case "Adding New Invoice":
                addingNewInvoice();
                break;
            case "Delete Invoice":
                deleteInvoice();
                break;
            case "Adding New Item":
                addingNewItem();
                break;
            case "Delete Item":
                deleteItem();
                break;
            case "invoiceDelete":
                invoiceDelete();
                break;
            case "invoiceAdded":
                invoiceAdded();
                break;
            case "lineAdded":
                lineAdded();
                break;
            case "lineCanceld":
                lineCanceld();
                break;
        }
    }

    @Override
   
    
    public void valueChanged(ListSelectionEvent e) {
        int selectedIndex = frame.getInvoiceTable().getSelectedRow();
        if (selectedIndex != -1) {
            System.out.println("Row is selected : " + selectedIndex);
            invo currentInvoice = frame.getInvoices().get(selectedIndex);
            frame.getInvoiceNumLabel().setText("" + currentInvoice.getNum());
            frame.getInvoiceDateLabel().setText(currentInvoice.getDate());
            frame.getCustomerNameLabel().setText(currentInvoice.getCustomer());
            frame.getInvoiceTotalLabel().setText("" + currentInvoice.getInvoiceTotal());
            LinesTbPack linesTableModel = new LinesTbPack(currentInvoice.getLines());
            frame.getLineTable().setModel(linesTableModel);
            linesTableModel.fireTableDataChanged();
        }
    }

    private void loadFile() {
        JFileChooser fc = new JFileChooser();
        try {
            int result = fc.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File headerFile = fc.getSelectedFile();
                Path headerPath = Paths.get(headerFile.getAbsolutePath());
                List<String> headerLines = Files.readAllLines(headerPath);
                System.out.println("List Invoices Is Added");
              
                ArrayList<invo> invoicesArray = new ArrayList<>();
                for (String headerLine : headerLines) {
                    try {
                        String[] headerParts = headerLine.split(",");
                        int invoiceNum = Integer.parseInt(headerParts[0]);
                        String invoiceDate = headerParts[1];
                        String customerName = headerParts[2];

                        invo invoice = new invo(invoiceNum, invoiceDate, customerName);
                        invoicesArray.add(invoice);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frame, "Error Format TYPE !!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                
                result = fc.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File lineFile = fc.getSelectedFile();
                    Path linePath = Paths.get(lineFile.getAbsolutePath());
                    List<String> lineLines = Files.readAllLines(linePath);
                    System.out.println("File Lines Added");
                    for (String lineLine : lineLines) {
                        try {
                            String lineParts[] = lineLine.split(",");
                            int invoiceNum = Integer.parseInt(lineParts[0]);
                            String itemName = lineParts[1];
                            double itemPrice = Double.parseDouble(lineParts[2]);
                            int count = Integer.parseInt(lineParts[3]);
                            invo inv = null;
                            for (invo invoice : invoicesArray) {
                                if (invoice.getNum() == invoiceNum) {
                                    inv = invoice;
                                    break;
                                }
                            }

                            AdLines line = new AdLines(itemName, itemPrice, count, inv);
                            inv.getLines().add(line);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(frame, "Error Format TYPE !!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    
                }
                frame.setInvoices(invoicesArray);
                invoTbPack invoicesTableModel = new invoTbPack(invoicesArray);
                frame.setInvoicesTb(invoicesTableModel);
                frame.getInvoiceTable().setModel(invoicesTableModel);
                frame.getInvoTbl().fireTableDataChanged();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "File Can Not Been Added !!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveFile() {
        ArrayList<invo> invoices = frame.getInvoices();
        String headers = "";
        String lines = "";
        for (invo invoice : invoices) {
            String invCSV = invoice.getAsCSV();
            headers += invCSV;
            headers += "\n";

            for (AdLines line : invoice.getLines()) {
                String lineCSV = line.getAsCSV();
                lines += lineCSV;
                lines += "\n";
            }
        }
        
        try {
            JFileChooser fc = new JFileChooser();
            int result = fc.showSaveDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File headerFile = fc.getSelectedFile();
                FileWriter hfw = new FileWriter(headerFile);
                hfw.write(headers);
                hfw.flush();
                hfw.close();
                result = fc.showSaveDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File lineFile = fc.getSelectedFile();
                    FileWriter lfw = new FileWriter(lineFile);
                    lfw.write(lines);
                    lfw.flush();
                    lfw.close();
                }
            }
        } catch (Exception ex) {

        }
    }

    private void addingNewInvoice() {
        invoCall = new InvoCall(frame);
        invoCall.setVisible(true);
    }

    private void deleteInvoice() {
        int selectedRow = frame.getInvoiceTable().getSelectedRow();
        if (selectedRow != -1) {
            frame.getInvoices().remove(selectedRow);
            frame.getInvoTbl().fireTableDataChanged();
        }
    }

    private void addingNewItem() {
        lineCall = new LineCall(frame);
        lineCall.setVisible(true);
    }

    private void deleteItem() {
        int selectedRow = frame.getLineTable().getSelectedRow();

        if (selectedRow != -1) {
            LinesTbPack linesTableModel = (LinesTbPack) frame.getLineTable().getModel();
            linesTableModel.getLines().remove(selectedRow);
            linesTableModel.fireTableDataChanged();
            frame.getInvoTbl().fireTableDataChanged();
        }
    }

    private void invoiceDelete() {
        invoCall.setVisible(false);
        invoCall.dispose();
        invoCall = null;
    }

    private void invoiceAdded() {
        String date = invoCall.getInvDateField().getText();
        String customer = invoCall.getCustNameField().getText();
        int num = frame.getNextInvoiceNum();
        try {
            String[] dateParts = date.split("-");  
            if (dateParts.length < 3) {
                JOptionPane.showMessageDialog(frame, "Error Date TYPE !!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                int day = Integer.parseInt(dateParts[0]);
                int month = Integer.parseInt(dateParts[1]);
                int year = Integer.parseInt(dateParts[2]);
                if (day > 31 || month > 12) {
                    JOptionPane.showMessageDialog(frame, "ERROR Date TYPE !!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    invo invoice = new invo(num, date, customer);
                    frame.getInvoices().add(invoice);
                    frame.getInvoTbl().fireTableDataChanged();
                    invoCall.setVisible(false);
                    invoCall.dispose();
                    invoCall = null;
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "ERROR Date TYPE !!", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void lineAdded() {
        String item = lineCall.getItemNameField().getText();
        String countStr = lineCall.getItemCountField().getText();
        String priceStr = lineCall.getItemPriceField().getText();
        int count = Integer.parseInt(countStr);
        double price = Double.parseDouble(priceStr);
        int selectedInvoice = frame.getInvoiceTable().getSelectedRow();
        if (selectedInvoice != -1) {
            invo invoice = frame.getInvoices().get(selectedInvoice);
            AdLines line = new AdLines(item, price, count, invoice);
            invoice.getLines().add(line);
            LinesTbPack linesTableModel = (LinesTbPack) frame.getLineTable().getModel();
            
            linesTableModel.fireTableDataChanged();
            frame.getInvoTbl().fireTableDataChanged();
        }
        lineCall.setVisible(false);
        lineCall.dispose();
        lineCall = null;
    }

    private void lineCanceld() {
        lineCall.setVisible(false);
        lineCall.dispose();
        lineCall = null;
    }

}
