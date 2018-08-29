package com.ariba;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.util.HashSet;
import java.util.Set;

/**
 * @author james.ngondo
 */
public class TestingXMLDuplicateSuiteClassNames {

    private static Boolean value;

    public static void mergeMultipleXMLAndRemoveDuplicateClassNames (String folderPath, String outputDir) throws TransformerException, ParserConfigurationException
    {

        Set<String> classNames = new HashSet<String>();

        try {
            // read all files from folder
            File folder = new File(folderPath);

            if (folder.exists()) {

                File[] listOfFiles = folder.listFiles();

                for (File file : listOfFiles) {

                    if (file.getName().endsWith(".xml")) {
                        value = true;
                        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                        Document doc1 = dBuilder.parse(folderPath + "/" + file.getName());
                        doc1.getDocumentElement().normalize();

                        NodeList nList = doc1.getElementsByTagName("class");

                        for (int temp = 0; temp < nList.getLength(); temp++) {
                            Node nNode = nList.item(temp);

                            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                                Element eElement = (Element) nNode;

                                // add to a set
                                classNames.add(eElement.getAttribute("name"));
                            }

                        } // inner for NodeList

                    }
                    else {
                        value = false;
                    }

                } // for files

                if (value == false) {
                    JOptionPane.showMessageDialog(null, "Invalid file input from specified directory", "File Input Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    JOptionPane.showMessageDialog(null, "Successfully created file in specified directory. Click OK", "File Created", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "Folder does not exist, please create a folder", "File Input Error", JOptionPane.ERROR_MESSAGE);
            }
        } // try
        catch (Exception e) {
            e.printStackTrace();
        }
        // end try catch
        BufferedWriter bw = null;
        try {

            TransformerFactory transformer = TransformerFactory.newInstance();
            Transformer t = transformer.newTransformer();

            // access via new for-loop
            for (Object object : classNames) {
                String element = (String) object;

                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.newDocument();

                Element rootElement = doc.createElement("class");
                doc.appendChild(rootElement);

                Attr attr = doc.createAttribute("name");

                attr.setValue(element);
                rootElement.setAttributeNode(attr);

                transformer = TransformerFactory.newInstance();
                t = transformer.newTransformer();
                t.setOutputProperty(OutputKeys.INDENT, "yes");
                t.setOutputProperty(OutputKeys.INDENT, "yes");
                t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

                File f = new File(outputDir + "/merged_classes_output.txt");
                StreamResult sr = new StreamResult(f);
                Node node = doc.getDocumentElement();
                DOMSource source = new DOMSource(node);
                // t.transform(source, sr);

                StringWriter writer = new StringWriter();
                StreamResult result = new StreamResult(writer);

                t.transform(source, result);
                // convert to string
                String strResult = writer.toString();
                System.out.print(strResult);

                bw = new BufferedWriter(new FileWriter(outputDir + "/merged_classes_output.txt", true));
                bw.write(strResult);
                bw.flush();
                bw.close();

            }

        }
        catch (Exception e) {
            // TODO: handle exception
        }

    }

}
