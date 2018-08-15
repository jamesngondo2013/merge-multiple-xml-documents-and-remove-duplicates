package com.ariba;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestingXMLDuplicates {

    public void mergeMultipleXMLAndRemoveDuplicates (String folderPath, String outputDir) throws TransformerException, ParserConfigurationException
    {      
        Map<String, List<String>> map = new HashMap<>();

        try {
            // read two files
            File folder = new File(folderPath);
            File[] listOfFiles = folder.listFiles();

            for (File file : listOfFiles) {
                
                if (file.getName().endsWith(".xml")) {
                    
                    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                    Document doc1 = dBuilder.parse(folderPath +"/"+file.getName());
                    doc1.getDocumentElement().normalize();
                    // System.out.print("Root element: ");
                    // System.out.println(doc1.getDocumentElement().getNodeName());
                    NodeList nList = doc1.getElementsByTagName("class");
                    // System.out.println("----------------------------");

                    for (int temp = 0; temp < nList.getLength(); temp++) {
                        Node nNode = nList.item(temp);
                        // System.out.println("\nCurrent Element :");
                        // node name -> class
                        // System.out.print(nNode.getNodeName() + ": ");

                        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element eElement = (Element) nNode;
                            // attribute of class based on name
                            // System.out.println(eElement.getAttribute("name"));

                            // list of include methods
                            NodeList includeMethods = eElement.getElementsByTagName("include");

                            for (int count = 0; count < includeMethods.getLength(); count++) {
                                Node node1 = includeMethods.item(count);

                                if (node1.getNodeType() == node1.ELEMENT_NODE) {
                                    Element methods = (Element) node1;
                                    // System.out.print("method: ");
                                    // method name
                                    // System.out.println(methods.getAttribute("name"));

                                    List<String> current = map.get(eElement.getAttribute("name"));
                                    if (current == null) {
                                        current = new ArrayList<String>();
                                        map.put(eElement.getAttribute("name"), current);
                                    }
                                    if (!(current.contains(methods.getAttribute("name")))) {
                                        current.add(methods.getAttribute("name"));
                                    }

                                }
                            }//inner inner for includeMethods
                        }

                    }// inner for NodeList

                }
               
            }//for files
        }//try
        catch (Exception e) {
            e.printStackTrace();
        }
        // end try catch
        BufferedWriter bw = null;
        try {

            TransformerFactory transformer = TransformerFactory.newInstance();
            Transformer t = transformer.newTransformer();
            

            for (String key : map.keySet()) {

                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.newDocument();

                Element rootElement = doc.createElement("class");
                doc.appendChild(rootElement);

                Attr attr = doc.createAttribute("name");

                attr.setValue(key);
                rootElement.setAttributeNode(attr);

                // methods element
                Element methods = doc.createElement("methods");
                rootElement.appendChild(methods);

               // i++;
                for (String value : map.get(key)) {

                    // include element
                    Element include = doc.createElement("include");
                    Attr attrType = doc.createAttribute("name");
                    attrType.setValue(value);
                    include.setAttributeNode(attrType);
                    methods.appendChild(include);

                }
                transformer = TransformerFactory.newInstance();
                t = transformer.newTransformer();
                t.setOutputProperty(OutputKeys.INDENT, "yes");
                t.setOutputProperty(OutputKeys.INDENT, "yes");
                t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

                File f = new File(outputDir+"/merged_output.txt");
                StreamResult sr = new StreamResult(f);
                Node node = doc.getDocumentElement();
                DOMSource source = new DOMSource(node);
                //t.transform(source, sr);

                StringWriter writer = new StringWriter();
                StreamResult result = new StreamResult(writer);
                
                t.transform(source, result);
               //convert to string
                String strResult = writer.toString();
                System.out.print(strResult);
                
                bw = new BufferedWriter(new FileWriter(outputDir+"/merged_output.txt", true));
                bw.write(strResult);
                //bw.newLine();
                bw.flush();
                
            }
        }
        catch (Exception e) {
            // TODO: handle exception
        }

       
    }

}
