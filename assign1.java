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
 * @lastModified: 19-10-2018
 */

public class assign1 {
	public static boolean isCriterionTime;

	private static List<Station> stations = new ArrayList<Station>();
	private static Station origin = new Station();
	private	static Station destination = new Station(); 
	private	static HashMap<String, Station> map = new HashMap<String, Station>();

	/**
	 * Main Method.
	 *
	 * javac assign1.java && java assign1 RailNetwork.xml
	 * javac assign1.java && java assign1 RailNetwork.xml 'Sydenham' 'Liverpool' time
	 * javac assign1.java && java assign1 RailNetwork.xml 'Denistone' 'Stanmore' changes
	 *
	 * List of Stations
	 * Wahroonga, Bexley North, Burwood, Como, Seven Hills, Macquarie University, Mascot, Heathcote,
	 * Wynyard, Blacktown, Glenfield, Yagoona, Hornsby, Westmead, International Airport, Birrong,
	 * Pymble, Edgecliff, Milsons Point, Wentworthville, Casula, Dundas, Chatswood, Engadine,
	 * Granville, Holsworthy, St Leonards, Museum, Parramatta, Tempe, Allawah, Miranda,
	 * Carlingford, Guildford, Oatley, Macarthur, Minto, Rhodes, Waterfall, Strathfield,
	 * Roseville, Rockdale, Kogarah, Cabramatta, Revesby, Hurstville, Petersham, Woolooware,
	 * Newtown, Lewisham, Sydenham, Gymea, Waitara, Carlton, Warwick Farm, Narwee,
	 * Kingsgrove, Denistone, Rydalmere, North Sydney, Circular Quay, Canley Vale, Pennant Hills, Turrella,
	 * Martin Place, Beecroft, Bondi Junction, Campbelltown, Beverly Hills, Turramurra, Campsie, Clyde,
	 * Sutherland, Merrylands, Macquarie Park, Wollstonecraft, Harris Park, Normanhurst, Domestic Airport, Homebush,
	 * Flemington, North Ryde, Villawood, Concord West, Central, Eastwood, Kings Cross, Mortdale,
	 * Pendle Hill, Penshurst, East Hills, Chester Hill, Macquarie Fields, Thornleigh, Epping, Liverpool,
	 * Berala, Arncliffe, Riverwood, Regents Park, Auburn, Bankstown, North Strathfield, Lakemba,
	 * Wolli Creek, Town Hall, Telopea, Jannali, Green Square, Panania, Caringbah, West Ryde,
	 * Marrickville, Ashfield, Carramar, Warrawee, Rosehill, Canterbury, Wiley Park, Meadowbank,
	 * Camellia, Lindfield, Banksia, St Peters, Padstow, Killara, Yennora, Stanmore,
	 * Cronulla, Waverton, Leightonfield, Hurlstone Park, Gordon, Punchbowl, Redfern, Ingleburn,
	 * Belmore, Artarmon, Dulwich Hill, Leumeah, Toongabbie, Lidcombe, Summer Hill, Macdonaldtown,
	 * Bardwell Park, Cheltenham, Loftus, Erskineville, Croydon, St James, Kirrawee, Fairfield, Sefton
	 * 
	 *
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		
		try {
			if ("time".equals(args[3])) {
				isCriterionTime = true;
			} else {
				isCriterionTime = false;
			}

			File file = new File(args[0]);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);

			doc.getDocumentElement().normalize();

			NodeList stationNodes = doc.getElementsByTagName("Station");

			System.out.println(stationNodes.getLength());
			
			for (int i = 0; i < stationNodes.getLength(); i++) {

				String name = "";
				String line = "";

				Node sNode = stationNodes.item(i);				
				
				if (sNode.getNodeType() == Node.ELEMENT_NODE) {

					// Create station
					Element station = (Element) sNode;
					name = station.getElementsByTagName("Name").item(0).getTextContent();
					line = station.getElementsByTagName("Line").item(0).getTextContent();
					
					Station s = new Station(name, line);
					map.put(name+line,s);

					if (name.equals(args[1])) {
						origin = s;
					} else if (name.equals(args[2])) {
						destination = s;
					}
					
					// Add edges to station
					NodeList edges = station.getElementsByTagName("StationEdge");
					for (int j = 0; j < edges.getLength(); j++) {

						Node eNode = edges.item(j);
						
						if (eNode.getNodeType() == Node.ELEMENT_NODE) {
							Element eEdge = (Element) eNode;
							name = eEdge.getElementsByTagName("Name").item(0).getTextContent();
							line = eEdge.getElementsByTagName("Line").item(0).getTextContent();
							int weight = Integer.parseInt(eEdge.getElementsByTagName("Duration").item(0).getTextContent());
							s.addEdge(new Edge(name, line, weight));
						}
						
					}

					// Add station to list of stations
					stations.add(s);
				}
			}

			dijkstra(origin);

			// Print out shortest path to destination
			Collections.sort(stations);


            for (Station s : stations) {
            	if (s.getName().equals(destination.getName())) {
					//System.out.print("[Time: "+s.getTime()+", Changes: "+s.getChanges()+"] "+origin.getName()+" - "+destination.getName()+"\n");
            		Station cur = s.getPath().get(0);
					Station next = s.getPath().get(0);
					for (int i = 0; i < s.getPath().size()-1; i++) {
						cur = s.getPath().get(i);
						next = s.getPath().get(i+1);
						
						if (cur.getName().equals(next.getName())) {
							cur = s.getPath().get(++i);
							next = s.getPath().get(i+1);
							System.out.println("then change to line "+cur.getLine()+", and continue to "+next.getName()+";");
						} else {
							System.out.println("From "+cur.getName()+", take line "+cur.getLine()+" to station "+next.getName()+";");
						}
					}
					System.out.println("From "+next.getName()+", take line "+next.getLine()+" to station "+s.getName()+".");

					if (isCriterionTime) {
						System.out.println("The total trip will take approximately "+s.getTime()+" minutes and will have "+s.getChanges()+" changes.");
					} else {
						System.out.println("The total trip will have "+s.getChanges()+" changes and will take approximately "+s.getTime()+" minutes.");
					}
					break;
					
					
            	}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void dijkstra(Station origin){

		origin.setTime(0);
		origin.setChanges(0);
		PriorityQueue<Station> queue = new PriorityQueue<Station>();
		queue.add(origin);

		while (!queue.isEmpty()) {
			Station s = queue.poll();
			
			for (Edge e : s.getEdges()) {
				e.updateStation(map);
				int newTime = s.getTime() + e.getDuration();
				int change = s.getName().equals(e.getName()) && e.getDuration() == 15 ? 1 : 0;
				int newChanges = s.getChanges() + change;
				
				if (isCriterionTime) {
					if (e.getStation().getTime() > newTime) {
						// Remove the station from the queue to update the time value.
						queue.remove(e.getStation());
						e.getStation().setTime(newTime);
						if (e.getStation().getChanges() > newChanges) {
							e.getStation().setChanges(newChanges);
						}
						
						// Take the path visited till now and add the new station
						e.getStation().setPath(new ArrayList<Station>(s.getPath()));
						e.getStation().addToPath(s);
						
						// Add back in the station
						queue.add(e.getStation());			
					}
				} else {
					if (e.getStation().getChanges() > newChanges) {
						// Remove the station from the queue to update the time value.
						queue.remove(e.getStation());
						e.getStation().setChanges(newChanges);
						if (e.getStation().getTime() > newTime) {
							e.getStation().setTime(newTime);
						}
						
						// Take the path visited till now and add the new station
						e.getStation().setPath(new ArrayList<Station>(s.getPath()));
						e.getStation().addToPath(s);
						
						// Add back in the station
						queue.add(e.getStation());					
					}
				}
				
			}
		}
	}
}