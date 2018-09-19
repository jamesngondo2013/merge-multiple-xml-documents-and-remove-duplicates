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
    
    private static final long serialVersionUID = 1L;
    private JButton btnClassAndMethods = new JButton("Merge Class Names And Methods");
    private JButton btnClassNames = new JButton("Merge Class Names Only");
    private JButton btnExit = new JButton("Exit");
    private JButton btnInputFilePathBrowse = new JButton("Browse...");
    private JButton btnOutFilePathBrowse = new JButton("Browse...");
    
    private StringBuilder sb;
    private String inputFilePath;
    private String outputFilePath;

    JFileChooser chooser;
    String choosertitle;

    private JTextField txtA = new JTextField();
    private JTextField txtB = new JTextField();

    private JLabel lblA = new JLabel("Enter File Input Directory:");
    private JLabel lblB = new JLabel("Enter File Output Directory:");

    public SuiteXMLFrame ()
    {
        setTitle("SAP Ariba GPD Merge XML Suite Files");
        setSize(800, 400);
        setLocation(new Point(600, 500));
        lblA.setFont(new Font("Arial", Font.BOLD, 16));
        lblB.setFont(new Font("Arial", Font.BOLD, 16));
        btnClassAndMethods.setFont(new Font("Arial", Font.PLAIN, 16));
        btnClassNames.setFont(new Font("Arial", Font.PLAIN, 16));
        btnInputFilePathBrowse.setFont(new Font("Arial", Font.PLAIN, 16));
        btnOutFilePathBrowse.setFont(new Font("Arial", Font.PLAIN, 16));
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
        //310 = x horizontal  240=y vertical
        btnClassAndMethods.setBounds(310, 190, 280, 40);
        btnClassNames.setBounds(310, 240, 280, 40);
        btnExit.setBounds(310, 300, 280, 40);
        btnInputFilePathBrowse.setBounds(600, 30, 120, 40);
        btnOutFilePathBrowse.setBounds(600, 90, 120, 40);

        txtA.setBounds(290, 30, 300, 40);
        txtB.setBounds(290, 90, 300, 40);

        lblA.setBounds(20, 40, 200, 30);
        lblB.setBounds(20, 90, 210, 30);

        add(btnClassAndMethods);
        add(btnClassNames);
        add(btnExit);
        add(btnInputFilePathBrowse);
        add(btnOutFilePathBrowse);

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

        btnClassAndMethods.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e)
            {
                btnClassAndMethodsClick(e);
            }
        });
        
        btnClassNames.addActionListener(new ActionListener() {
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
        
        btnInputFilePathBrowse.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e)
            {
                btnInputFilePathBrowseClick(e);
            }
        });
        
        btnOutFilePathBrowse.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e)
            {
                btnOutFilePathBrowseClick(e);
            }
        });
    }

    private void btnClassAndMethodsClick (ActionEvent evt)
    {
        TestingXMLDuplicates xml = new TestingXMLDuplicates();
        String folderPath = getInputFilePath();
        String outputDir = getOutputFilePath();

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
        
        txtA.setText("");
        txtB.setText("");

    }
    
    private void btnClassNamesClick (ActionEvent evt)
    {
        SuiteXMLFrame f = new SuiteXMLFrame();
        f.setVisible(false);
        
        SuiteClassNamesFrame frame = new SuiteClassNamesFrame();
        frame.setVisible(true);

    }
    public void setInputFilePath (String path)
    {
        this.inputFilePath = path;
    }
    
    public void setOutputFilePath (String outputFilePath)
    {
        this.outputFilePath = outputFilePath;
    }

    private void btnInputFilePathBrowseClick (ActionEvent evt)
    {
        sb = new StringBuilder();

        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle(choosertitle);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //
        // disable the "All files" option.
        //
        chooser.setAcceptAllFileFilterUsed(false);
        //
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
           // System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
            inputFilePath = chooser.getSelectedFile().toString();

           // System.out.println("getSelectedFile() : " + path);
        }
        else {
            //System.out.println("No Selection ");
        }

        String[] words = inputFilePath.split(" ");
        for (int i = 0; i < words.length; i++) {

            String str = words[i].replace("\\", "/");
            sb.append(str);

        }
        setInputFilePath (sb.toString());
        txtA.setText(getInputFilePath());
        
        System.out.println("Input File Path: " + getInputFilePath());    

    }
    
    private void btnOutFilePathBrowseClick (ActionEvent evt)
    {
        sb = new StringBuilder();

        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle(choosertitle);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //
        // disable the "All files" option.
        //
        chooser.setAcceptAllFileFilterUsed(false);
        //
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
           // System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
            outputFilePath = chooser.getSelectedFile().toString();

           // System.out.println("getSelectedFile() : " + path);
        }
        else {
            //System.out.println("No Selection ");
        }

        String[] words = outputFilePath.split(" ");
        for (int i = 0; i < words.length; i++) {

            String str = words[i].replace("\\", "/");
            sb.append(str);

        }
        setOutputFilePath (sb.toString());
        txtB.setText(getOutputFilePath());
        
        System.out.println("Output File Path: " + getOutputFilePath());    

    }
    
    
    private void btnExitClick (ActionEvent evt)
    {
        System.exit(0);

    }
    
    
    public String getOutputFilePath ()
    {
        return outputFilePath;
    }

    public String getInputFilePath ()
    {
        return inputFilePath;
    }


}
