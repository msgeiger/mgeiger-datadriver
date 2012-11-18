package com.mgeiger.datadriver;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlParser {

    private static String pathToConfig = "c:\\Xebium\\config\\config.xml";

    public static void setPathToConfig(String pathToConfigString) {
        pathToConfig = pathToConfigString;
    }

    public static String getPathToConfig() {
        return pathToConfig;
    }

    private static String getTagValue(String sTag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();

        Node nValue = (Node) nlList.item(0);

        return nValue.getNodeValue();
    }

    public static Document setDocumentParameters() throws ParserConfigurationException {
        File fXmlFile = new File(pathToConfig);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = null;
        try {
            doc = dBuilder.parse(fXmlFile);
        } catch (SAXException ex) {
            Logger.getLogger(XmlParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XmlParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        doc.getDocumentElement().normalize();

        return doc;
    }

    public static boolean useParser() {
        boolean use_parser = false;
        Document doc = null;
        try {
            doc = XmlParser.setDocumentParameters();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XmlParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        // System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
        NodeList nList = doc.getElementsByTagName("use_parser");
        System.out.println("-----------------------");

        for (int temp = 0; temp < nList.getLength(); temp++) {

            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                Element eElement = (Element) nNode;
                String answer = getTagValue("parser", eElement);
                use_parser = answer == "true" ? true : false;
            }

        }

        return use_parser;
    }

    public static void getMappedDbConfigurationConnection() {

        String db_user = null;
        String db_password = null;
        String db_connect = null;
        String db_driver = null;

        try {

            Document doc = XmlParser.setDocumentParameters();
            // System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("database");
            System.out.println("-----------------------");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    db_user = getTagValue("db_username", eElement);
                    db_password = getTagValue("db_password", eElement);
                    db_connect = getTagValue("db_connect", eElement);
                    db_driver = getTagValue("db_driver", eElement);
                }
            }

            // System.out.println(db_user + " " + db_password + " " + db_connect + " " + db_driver);
            DBConnection c = new DBConnection();
            c.setUser(db_user);
            c.setPassword(db_password);
            c.setDbConnect(db_connect);
            c.setDbDriver(db_driver);
            c.getConnect();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}