import java.util.*;

/**
 * Edge Class
 * 
 * @author Brice Purton
 * @studentID 3180044
 * @lastModified: 19-10-2018
 */

public class Edge {
	
	private String name;
	private String line;
	private int duration;
	private Station station;
	
	/**
	 * Constructs an edge from parameters.
	 *
	 * @param name of the connecting station
	 * @param line the connecting station is on
	 * @param duration time to connect to station
	 */
	public Edge(String name, String line, int duration) {
		this.name = name;
		this.line = line;
		this.duration = duration;
		this.station = null;
	}

	/**
	 * Returns name.
	 *
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns name.
	 *
	 * @return line
	 */
	public String getLine() {
		return line;
	}

	/**
	 * Returns duration.
	 *
	 * @return duration
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * Returns station.
	 *
	 * @return station
	 */
	public Station getStation() {
		return station;
	}

	/**
	 * ___________________________________________________
	 *
	 * @param map
	 */
	public void updateStation(HashMap<String, Station> map) {
		station = map.get(name+line);
	}

	/**
	 * Override toString to output edge name, line and duration.
	 *
	 * @return string
	 */
	@Override   
	public String toString() {
		return name+": "+line+": "+duration;
	}
}

