import java.util.*;

/**
 * Edge Class
 * 
 * @author Brice Purton
 * @studentID 3180044
 * @lastModified: 19-10-2018
 */

public class Edge {
	
	private Station station;
	private int duration;
	
	/**
	 * Constructs an edge from parameters.
	 *
	 * @param station the connecting station
	 * @param duration time to connect to station
	 */
	public Edge(Station station, int duration) {
		this.station = station;
		this.duration = duration;
	}

	/**
	 * Returns name of connecting station.
	 *
	 * @return name
	 */
	public String getName() {
		return station.getName();
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
	 * Returns duration.
	 *
	 * @return duration
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * Override toString to output edge name, line and duration.
	 *
	 * @return string
	 */
	@Override   
	public String toString() {
		return station+": "+duration;
	}
}

