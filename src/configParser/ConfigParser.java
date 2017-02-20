package configParser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;

/**
 * Klasa odpowiadaj�ca za odczytywanie danych z pliku konfiguracyjnego
 */
public class ConfigParser {

    private final File fXmlFile;
    private final String documentName;
    private String className;
    private String serverPort;
    private String configPath;

    public ConfigParser(String docName) {
        documentName = docName;
        configPath = "SimuletConfigs" + '/' + documentName + ".xml";
        fXmlFile = new File(configPath);
    }

    /**
     * Parsowanie konfiguracji
     */
    public ArrayList<ArrayList<String>> Parse() {
        ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();
        serverPort = "";
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = (Document) dBuilder.parse(fXmlFile);
            ((org.w3c.dom.Document) doc).getDocumentElement().normalize();

            NodeList nList = ((org.w3c.dom.Document) doc).getElementsByTagName("Simulet");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = (Node) nList.item(temp);

                System.out.println("\nCurrent Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    serverPort = eElement.getElementsByTagName("serverport").item(0).getTextContent();
                    className = eElement.getElementsByTagName("classname").item(0).getTextContent();

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return table;
    }

    public int getPort() {
        return Integer.parseInt(serverPort);
    }

    public String getClassName() {
        return className;
    }

}
