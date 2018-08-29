package com.ariba;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.*;
import java.awt.event.*;


class SuiteClassNamesFrame extends JFrame {

    /**
     * @author james.ngondo
     */
    private String placeholder = "C:/Users";
    
    private static final long serialVersionUID = 1L;
    private JButton btnSubmit = new JButton("Merge Class Names Only");
    private JButton btnExit = new JButton("Exit");

    private JTextField txt1A = new JTextField(placeholder);
    private JTextField txt1B = new JTextField(placeholder);

    private JLabel lblA = new JLabel("Enter File Input Directory :");
    private JLabel lblB = new JLabel("Enter File Output Directory :");

    public SuiteClassNamesFrame ()
    {
        setTitle("SAP Ariba GPD Merge XML Suite Files");
        setSize(800, 400);
        setLocation(new Point(600, 500));
        lblA.setFont(new Font("Arial", Font.BOLD, 16));
        lblB.setFont(new Font("Arial", Font.BOLD, 16));
        btnSubmit.setFont(new Font("Arial", Font.PLAIN, 16));
        btnExit.setFont(new Font("Arial", Font.PLAIN, 16));
        txt1A.setFont(new Font("Arial", Font.PLAIN, 15));
        txt1B.setFont(new Font("Arial", Font.PLAIN, 15));
        setLayout(null);
        setResizable(false);

        initComponent();
        initEvent();
    }

    private void initComponent ()
    {
        // 240=downwards
        btnSubmit.setBounds(310, 240, 280, 40);
        btnExit.setBounds(310, 300, 280, 40);

        txt1A.setBounds(280, 30, 300, 40);
        txt1B.setBounds(280, 90, 300, 40);

        lblA.setBounds(20, 40, 200, 30);
        lblB.setBounds(20, 90, 210, 30);

        add(btnSubmit);
        add(btnExit);

        add(lblA);
        add(lblB);

        add(txt1A);
        add(txt1B);
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

        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e)
            {
                btnExitClick(e);
            }
        });
    }

    private void btnSubmitClick (ActionEvent evt)
    {
        String folderPath = txt1A.getText();
        String outputDir = txt1B.getText();

        try {
            TestingXMLDuplicateSuiteClassNames.mergeMultipleXMLAndRemoveDuplicateClassNames(folderPath, outputDir);
        }
        catch (TransformerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        txt1A.setText(placeholder);
        txt1B.setText(placeholder);

    }

    private void btnExitClick (ActionEvent evt)
    {
        System.exit(0);

    }

}
