package configparser;

import modules.SimuletsState;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static exceptions.ExceptionCodes.NO_MINIATURE;
import static exceptions.ExceptionCodes.NO_PICTURE;

/**
 * Klasa odpowiadaj�ca za odczytywanie danych z pliku konfiguracyjnego
 */
public class ConfigParser {

    private final File fXmlFile;
    private final String documentName;
    private String className;
    private String serverPort;
    private String configPath;
    private String initialStateName;
    private List<SimuletsState> possibleStates;

    public ConfigParser(final String docName) {
        documentName = docName;
        configPath = "SimuletConfigs" + '/' + documentName + ".xml";
        fXmlFile = new File(configPath);
    }

    /**
     * Parsowanie konfiguracji
     */
    public ArrayList<ArrayList<String>> parse() {
        ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();
        serverPort = "";
        className = "";
        possibleStates = new ArrayList<>();
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("Simulet");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                System.out.println("\nCurrent Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    serverPort = eElement.getElementsByTagName("serverport").item(0).getTextContent();
                    className = eElement.getElementsByTagName("classname").item(0).getTextContent();
                }
            }

            initialStateName = doc.getElementsByTagName("states").item(0).getAttributes().item(0).getTextContent();
            final NodeList states = doc.getElementsByTagName("state");
            for (int x = 0; x < states.getLength(); x++) {
                final SimuletsState state = new SimuletsState(states.item(x).getTextContent(),
                        loadPictures(states.item(x).getTextContent()),
                        loadMiniatures(states.item(x).getTextContent()),
                        loadHighlightedMiniatures(states.item(x).getTextContent()));
                possibleStates.add(state);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return table;
    }

    private ImageIcon loadPictures(final String stateName) {
        final File directory = new File("pictures/" + documentName + "/" + stateName + "/main");
        if (directory.isDirectory()) {
            final File[] files = directory.listFiles();
            if (files.length > 0) {
                return new ImageIcon(files[0].getAbsolutePath());
            } else {
                throw new RuntimeException(NO_PICTURE);
            }
        }
        return new ImageIcon();
    }

    private BufferedImage loadMiniatures(final String stateName) {
        final File directory = new File("pictures/" + documentName + "/" + stateName + "/mini");
        if (directory.isDirectory()) {
            final File[] files = directory.listFiles();
            if (files.length > 0) {
                BufferedImage buffImg;
                try {
                    buffImg = ImageIO.read(files[0]);
                    return buffImg;
                } catch (IOException e) {
                }
            } else {
                throw new RuntimeException(NO_MINIATURE);
            }
        }
        return null;
    }

    private BufferedImage loadHighlightedMiniatures(final String stateName) {
        final File directory = new File("pictures/" + documentName + "/" + stateName + "/highlighted_mini");
        if (directory.isDirectory()) {
            final File[] files = directory.listFiles();
            if (files.length > 0) {
                BufferedImage buffImg;
                try {
                    buffImg = ImageIO.read(files[0]);
                    return buffImg;
                } catch (IOException e) {
                }
            } else {
                throw new RuntimeException(NO_MINIATURE);
            }
        }
        return null;
    }

    public int getPort() {
        return Integer.parseInt(serverPort);
    }

    public String getClassName() {
        return className;
    }

    public String getInitialStateName() {
        return initialStateName;
    }

    public List<SimuletsState> getPossibleStates() {
        return possibleStates;
    }

}
