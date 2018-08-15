package com.ariba;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class SuiteXMLFrame extends JFrame {

    /**
     * @author james.ngondo
     */
    private String placeholder = "C:/Users";
    
    private static final long serialVersionUID = 1L;
    private JButton btnSubmit = new JButton("Submit");
    private JButton btnExit = new JButton("Exit");

    private JTextField txtA = new JTextField(placeholder);
    private JTextField txtB = new JTextField(placeholder);

    private JLabel lblA = new JLabel("Enter Folder Directory :");
    private JLabel lblB = new JLabel("Enter Output Directory :");

    public SuiteXMLFrame ()
    {
        setTitle("SAP Ariba GPD Merge XML Suite Files");
        setSize(800, 400);
        setLocation(new Point(600, 500));
        lblA.setFont(new Font("Arial", Font.BOLD, 16));
        lblB.setFont(new Font("Arial", Font.BOLD, 16));
        btnSubmit.setFont(new Font("Arial", Font.PLAIN, 16));
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
        // 240=downwards
        btnSubmit.setBounds(350, 180, 180, 40);
        btnExit.setBounds(350, 240, 180, 40);

        txtA.setBounds(280, 30, 300, 40);
        txtB.setBounds(280, 90, 300, 40);

        lblA.setBounds(20, 40, 200, 20);
        lblB.setBounds(20, 90, 200, 20);

        add(btnSubmit);
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

    private void btnExitClick (ActionEvent evt)
    {
        System.exit(0);

    }

}
