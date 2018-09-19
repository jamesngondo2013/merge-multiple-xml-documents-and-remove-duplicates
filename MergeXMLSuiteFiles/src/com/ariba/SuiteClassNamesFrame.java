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
    
    private static final long serialVersionUID = 1L;
    private JButton btnSubmit = new JButton("Merge Class Names Only");
    private JButton btnExit = new JButton("Exit");
    private JButton btnInputFilePathBrowse = new JButton("Browse...");
    private JButton btnOutFilePathBrowse = new JButton("Browse...");
    
    private StringBuilder sb;
    private String inputFilePath;
    private String outputFilePath;

    JFileChooser chooser;
    String choosertitle;

    private JTextField txt1A = new JTextField();
    private JTextField txt1B = new JTextField();

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
        btnInputFilePathBrowse.setFont(new Font("Arial", Font.PLAIN, 16));
        btnOutFilePathBrowse.setFont(new Font("Arial", Font.PLAIN, 16));
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
        btnInputFilePathBrowse.setBounds(600, 30, 120, 40);
        btnOutFilePathBrowse.setBounds(600, 90, 120, 40);

        txt1A.setBounds(280, 30, 300, 40);
        txt1B.setBounds(280, 90, 300, 40);

        lblA.setBounds(20, 40, 200, 30);
        lblB.setBounds(20, 90, 210, 30);

        add(btnSubmit);
        add(btnExit);
        add(btnInputFilePathBrowse);
        add(btnOutFilePathBrowse);

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
        
        btnInputFilePathBrowse.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e)
            {
                btnBrowseClick(e);
            }
        });
        
        btnOutFilePathBrowse.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e)
            {
                btnOutFilePathBrowseClick(e);
            }
        });
    }
    
    private void btnBrowseClick (ActionEvent evt)
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
        txt1A.setText(getIputFilePath());
        
        System.out.println("File Path: " + getIputFilePath());    

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
        txt1B.setText(getOutputFilePath());
        
        System.out.println("Output File Path: " + getOutputFilePath());    

    }
    

    private void btnSubmitClick (ActionEvent evt)
    {
        String folderPath = getIputFilePath();
        String outputDir = getOutputFilePath();

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
        
        txt1A.setText("");
        txt1B.setText("");

    }
    
    public void setInputFilePath (String path)
    {
        this.inputFilePath = path;
    }
    
    public void setOutputFilePath (String outputFilePath)
    {
        this.outputFilePath = outputFilePath;
    }


    private void btnExitClick (ActionEvent evt)
    {
        System.exit(0);

    }
    
    public String getIputFilePath ()
    {
        return inputFilePath;
    }

    
    public String getOutputFilePath ()
    {
        return outputFilePath;
    }

}
