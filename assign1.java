import java.io.File;
import java.util.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.*;


/**
 * This class takes in a file ...
 * 
 * @author Brice Purton
 * @studentID 3180044
 * @lastModified: 19-10-2018
 */

public class assign1 {
	public static boolean isCriterionTime;

	private static List<Station> origins = new ArrayList<Station>();
	private static List<Station> destinations = new ArrayList<Station>();
	private	static HashMap<String, Station> stations = new HashMap<String, Station>();

	/**
	 * Main Method.
	 *
	 * javac assign1.java && java assign1 RailNetwork.xml 'Sydenham' 'Petersham' time
	 * javac assign1.java && java assign1 RailNetwork.xml 'Denistone' 'Stanmore' changes
	 * javac assign1.java && java assign1 RailNetwork.xml 'Macarthur' 'Warrawee' time
	 * javac assign1.java && java assign1 RailNetwork.xml 'Circular Quay' 'North Sydney' time
	 * javac assign1.java && java assign1 RailNetwork.xml 'Waterfall' 'Carlingford' time
	 * javac assign1.java && java assign1 RailNetwork.xml 'Glenfield' 'Rydalmere' time
	 * javac assign1.java && java assign1 RailNetwork.xml 'Glenfield' 'International Airport' time
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
			// Set criterion
			if ("time".equals(args[3])) {
				isCriterionTime = true;
			} else {
				isCriterionTime = false;
			}

			// Read in and create graph
			ParseXML(args[0], args[1], args[2]);
			
			// Create single source origin station with 0 weight edges to all lines at origin station
			Station origin = new Station(origins.get(0).getName(), "");
			for (Station s : origins) {
				origin.addEdge(new Edge(s, 0));
			}

			// Perform dijkstra's algorithm on origin
			dijkstra(origin);

			// Sort destinations so optimal destination line at index 0
			Collections.sort(destinations);
			
			// Print out optimal path to destination
			output2(destinations.get(0));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Parse an xml rail network file of stations and station edges to create a
	 * graph of stations with adjacency lists for it's edges.
	 *
	 * @param fileName name of rail network xml file
	 * @param origin station name
	 * @param destination station name
	 */
	public static void ParseXML(String fileName, String origin, String destination) throws Exception {
		// Open XML file and build DOM
		File file = new File(fileName);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(file);
		doc.getDocumentElement().normalize();

		// Get list of stations
		NodeList stationNodes = doc.getElementsByTagName("Station");

		// Number of Stations
		System.out.println("Stations: "+stationNodes.getLength());
		int l = 0;
		
		// Loop through stations 
		for (int i = 0; i < stationNodes.getLength(); i++) {

			String name = "";
			String line = "";
			Node sNode = stationNodes.item(i);
			if (sNode.getNodeType() == Node.ELEMENT_NODE) {
				Element station = (Element) sNode;
				
				// Create station
				name = station.getElementsByTagName("Name").item(0).getTextContent();
				line = station.getElementsByTagName("Line").item(0).getTextContent();
				
				Station s;

				// Check if station already exists
				if (stations.containsKey(name+line)) {
					s = stations.get(name+line);
				} else {
					// Create station and store in hashmap
					s = new Station(name, line);
					stations.put(name+line,s);
				}

				// Check if station is origin or destination
				if (name.equals(origin)) {
					origins.add(s);
				} else if (name.equals(destination)) {
					destinations.add(s);
				}
				
				// Loop through edges
				NodeList edges = station.getElementsByTagName("StationEdge");
				
				for (int j = 0; j < edges.getLength(); j++) {
					l++;
					Node eNode = edges.item(j);
					
					if (eNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eEdge = (Element) eNode;

						// Add edge to station
						name = eEdge.getElementsByTagName("Name").item(0).getTextContent();
						line = eEdge.getElementsByTagName("Line").item(0).getTextContent();
						int weight = Integer.parseInt(eEdge.getElementsByTagName("Duration").item(0).getTextContent());

						// If edge station not read in yet then create it and store in hashmap
						if (!stations.containsKey(name+line)) {
							stations.put(name+line, new Station(name, line));
						}

						// Create edge and add to station
						s.addEdge(new Edge(stations.get(name+line), weight));
					}
				}
			}
		}
		System.out.println("Edges: "+l+"\n");
	}

	/**
	 * Dijkstra's algorithm implentation using adjency list graph and 
	 * indirect heap priority queue. It calculates the optimal path from the
	 * origin station to every other station optimised by either minimum time or
	 * least number of line changes.
	 *
	 * @param origin station
	 */
	public static void dijkstra(Station origin) {

		// No time or changes to go from origin to origin
		origin.setTime(0);
		origin.setChanges(0);

		// Add origin to queue
		PriorityQueue<Station> queue = new PriorityQueue<Station>();
		queue.add(origin);

		// While there are edges that haven't been visited
		while (!queue.isEmpty()) {

			Station station = queue.poll();
			
			// Loop through all edges of station
			for (Edge edge : station.getEdges()) {

				// Time to reach edge station from current station
				int newTime = station.getTime() + edge.getDuration();

				// If station names are the same and duration is 15 then it's a line change
				int change = station.getName().equals(edge.getName()) && edge.getDuration() == 15 ? 1 : 0;
				int newChanges = station.getChanges() + change;
				
				// Optimise for time
				if (isCriterionTime) {
					
					if (edge.getStation().getTime() > newTime) {

						// Remove the station from the queue to update the time and changes.
						queue.remove(edge.getStation());
						edge.getStation().setTime(newTime);
						edge.getStation().setChanges(newChanges);
						
						// Add the new station to path
						edge.getStation().setPath(new ArrayList<Station>(station.getPath()));
						edge.getStation().addToPath(station);
						
						// Add back in the station
						queue.add(edge.getStation());
					}
				// Optimise for changes
				} else {
					if (edge.getStation().getChanges() > newChanges) {
						// Remove the station from the queue to update the changes and time.
						queue.remove(edge.getStation());
						edge.getStation().setChanges(newChanges);
						edge.getStation().setTime(newTime);
						
						// Add the new station to path
						edge.getStation().setPath(new ArrayList<Station>(station.getPath()));
						edge.getStation().addToPath(station);
						
						// Add back in the station
						queue.add(edge.getStation());
					}
				}	
			}
		}
	}

	/**
	 * Print out the optimal path from origin to destination station
	 * in specific format stating from station, to station, line used, line changes
	 * and time and changes of total trip.
	 *
	 * @param destination station name
	 */
	public static void output(Station destination) {
		int time = 0;
		//System.out.print("[Time: "+destination.getTime()+", Changes: "+destination.getChanges()+"] "+origin.getName()+" - "+destination.getName()+"\n");
		ArrayList<Station> path = destination.getPath();

		Station cur = path.get(0);
		Station next = path.get(0);

		// Loop through stations along path
		for (int i = 1; i < path.size()-1; i++) {
			cur = path.get(i);
			next = path.get(i+1);

			// === Only for testing correct time===
			for (Edge e : cur.getEdges()) {
				if (e.getStation() == next) {
					time += cur.getEdges().get(cur.getEdges().indexOf(e)).getDuration();
				}
			}
			
			// If current and next station name is the same then it's a line change
			if (cur.getName().equals(next.getName())) {
				cur = path.get(++i);
				next = path.get(i+1);

				System.out.println("then change to line "+cur.getLine()+", and continue to "+next.getName()+";");

				// === Only for testing correct time===
				for (Edge e : cur.getEdges()) {
					if (e.getStation() == next) {
						time += cur.getEdges().get(cur.getEdges().indexOf(e)).getDuration();
					}
				}
			} else {
				System.out.println("From "+cur.getName()+", take line "+cur.getLine()+" to station "+next.getName()+";");
			}
		}
		System.out.println("From "+next.getName()+", take line "+next.getLine()+" to station "+destination.getName()+".");

		// === Only for testing correct time===
		for (Edge e : next.getEdges()) {
			if (e.getStation() == destination) {
				time += next.getEdges().get(next.getEdges().indexOf(e)).getDuration();
			}
		}

		if (isCriterionTime) {
			System.out.println("The total trip will take approximately "+destination.getTime()+" minutes and will have "+destination.getChanges()+" changes.");
		} else {
			System.out.println("The total trip will have "+destination.getChanges()+" changes and will take approximately "+destination.getTime()+" minutes.");
		}
		
		// === Only for testing correct time===
		System.out.println("\nTime it should have taken: "+time);
	}
	
	/**
	 * Print out the optimal path from origin to destination station
	 * in specific format stating from station, to station, line used, line changes
	 * and time and changes of total trip.
	 *
	 * @param destination station name
	 */
	public static void output2(Station destination) {
		int time = 0;
		ArrayList<Station> path = destination.getPath();

		Station cur = path.get(0);
		Station next = path.get(0);

		// Loop through stations along path
		for (int i = 1; i < path.size()-1; i++) {
			cur = path.get(i);
			next = path.get(i+1);

			// === Only for testing correct time===
			for (Edge e : cur.getEdges()) {
				if (e.getStation() == next) {
					time += cur.getEdges().get(cur.getEdges().indexOf(e)).getDuration();
				}
			}
			
			// If current and next station name is the same then it's a line change
			if (cur.getName().equals(next.getName())) {
				System.out.print(cur.getName()+";\nthen change to line "+next.getLine()+", and continue to ");
				cur = path.get(++i);
				next = path.get(i+1);

				//System.out.println("then change to line "+cur.getLine()+", and continue to "+next.getName()+";");

				// === Only for testing correct time===
				for (Edge e : cur.getEdges()) {
					if (e.getStation() == next) {
						time += cur.getEdges().get(cur.getEdges().indexOf(e)).getDuration();
					}
				}
			} else if (i == 1) {
				System.out.print("From "+cur.getName()+", take line "+cur.getLine()+" to station ");
			}
		}
		if (next == path.get(0)) {
			System.out.println("From "+next.getName()+", take line "+next.getLine()+" to station "+destination.getName()+".");
		}else {
			System.out.println(destination.getName()+".");	
		}
		

		// === Only for testing correct time===
		for (Edge e : next.getEdges()) {
			if (e.getStation() == destination) {
				time += next.getEdges().get(next.getEdges().indexOf(e)).getDuration();
			}
		}

		if (isCriterionTime) {
			System.out.println("The total trip will take approximately "+destination.getTime()+" minutes and will have "+destination.getChanges()+" changes.");
		} else {
			System.out.println("The total trip will have "+destination.getChanges()+" changes and will take approximately "+destination.getTime()+" minutes.");
		}
		
		// === Only for testing correct time===
		System.out.println("\nTime it should have taken: "+time);
	}
}