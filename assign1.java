import java.io.*;
import java.util.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;


/**
 * This class takes in a file ...
 * 
 * @author Brice Purton
 * @studentID 3180044
 * @lastModified: 08-10-2018
 */

public class assign1 {

	/**
	 * Main Method.
	 *
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		List<Station> stations = new ArrayList<Station>();
		try {

			File file = new File(args[0]);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);

			doc.getDocumentElement().normalize();

			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

			NodeList stationNodes = doc.getElementsByTagName("Station");
			
			System.out.println("----------------------------");

			for (int i = 0; i < stationNodes.getLength(); i++) {

				String name = "";
				String line = "";

				Node sNode = stationNodes.item(i);
						
				System.out.println("\nCurrent Element :" + sNode.getNodeName());
						
				
				if (sNode.getNodeType() == Node.ELEMENT_NODE) {

					Element station = (Element) sNode;

					name = station.getElementsByTagName("Name").item(0).getTextContent();
					line = station.getElementsByTagName("Line").item(0).getTextContent();

					NodeList edges = station.getElementsByTagName("StationEdge");
					System.out.println(name+" "+line);
					for (int j = 0; j < edges.getLength(); j++) {

						Node eNode = edges.item(j);
						
						if (eNode.getNodeType() == Node.ELEMENT_NODE) {
							Element eEdge = (Element) eNode;
							String edgeName = eEdge.getElementsByTagName("Name").item(0).getTextContent();
							String edgeLine = eEdge.getElementsByTagName("Line").item(0).getTextContent();
							int weight = Integer.parseInt(eEdge.getElementsByTagName("Duration").item(0).getTextContent());
							System.out.println(edgeName+" "+edgeLine+" "+weight);
						}
						
					}
					System.out.println("");
				}

				stations.add(new Station(name, line));
			}

			for (Station s : stations) {
                //System.out.println(s);
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}