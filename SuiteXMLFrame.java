package com.ariba;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import java.awt.*;
import java.awt.event.*;


class SuiteXMLFrame extends JFrame {

    /**
     * @author james.ngondo
     */
    private String placeholder = "C:/Users";
    
    private static final long serialVersionUID = 1L;
    private JButton btnSubmit = new JButton("Merge Class Names And Methods");
    private JButton classNames = new JButton("Merge Class Names Only");
    private JButton btnExit = new JButton("Exit");

    private JTextField txtA = new JTextField(placeholder);
    private JTextField txtB = new JTextField(placeholder);

    private JLabel lblA = new JLabel("Enter File Input Directory:");
    private JLabel lblB = new JLabel("Enter File Output Directory:");

    public SuiteXMLFrame ()
    {
        setTitle("SAP Ariba GPD Merge XML Suite Files");
        setSize(800, 400);
        setLocation(new Point(600, 500));
        lblA.setFont(new Font("Arial", Font.BOLD, 16));
        lblB.setFont(new Font("Arial", Font.BOLD, 16));
        btnSubmit.setFont(new Font("Arial", Font.PLAIN, 16));
        classNames.setFont(new Font("Arial", Font.PLAIN, 16));
        btnExit.setFont(new Font("Arial", Font.PLAIN, 16));
        txtA.setFont(new Font("Arial", Font.PLAIN, 15));
        txtB.setFont(new Font("Arial", Font.PLAIN, 15));
        setLayout(null);
        setResizable(false);

        initComponent();
        initEvent();
    }

    private void initComponent ()
    {
        // 240=y vertical 310 = x horizontal
        btnSubmit.setBounds(310, 190, 280, 40);
        classNames.setBounds(310, 240, 280, 40);
        btnExit.setBounds(310, 300, 280, 40);

        txtA.setBounds(290, 30, 300, 40);
        txtB.setBounds(290, 90, 300, 40);

        lblA.setBounds(20, 40, 200, 30);
        lblB.setBounds(20, 90, 210, 30);

        add(btnSubmit);
        add(classNames);
        add(btnExit);

        add(lblA);
        add(lblB);

        add(txtA);
        add(txtB);
    }

    private void initEvent ()
    {

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing (WindowEvent e)
            {
                System.exit(1);
            }
        });

        btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e)
            {
                btnSubmitClick(e);
            }
        });
        
        classNames.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e)
            {
                btnClassNamesClick(e);
            }
        });

        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e)
            {
                btnExitClick(e);
            }
        });
    }

    private void btnSubmitClick (ActionEvent evt)
    {
        TestingXMLDuplicates xml = new TestingXMLDuplicates();
        String folderPath = txtA.getText();
        String outputDir = txtB.getText();

        try {
            xml.mergeMultipleXMLAndRemoveDuplicates(folderPath, outputDir);
        }
        catch (TransformerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        txtA.setText(placeholder);
        txtB.setText(placeholder);

    }
    
    private void btnClassNamesClick (ActionEvent evt)
    {
        SuiteXMLFrame f = new SuiteXMLFrame();
        f.setVisible(false);
        
        SuiteClassNamesFrame frame = new SuiteClassNamesFrame();
        frame.setVisible(true);

    }

    private void btnExitClick (ActionEvent evt)
    {
        System.exit(0);

    }

}
