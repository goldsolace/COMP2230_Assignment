import java.util.*;

/**
 * Station Class
 * 
 * @author Brice Purton
 * @studentID 3180044
 * @lastModified: 08-10-2018
 */

public class Station {
	
	private String name;
	private String line;
	private ArrayList<Station> edges;
	
	/**
	 * Constructs a client from parameters.
	 *
	 * @param id name of the client
	 * @param brewTime time the client will brew their coffee for
	 * @param hot whether the client wants hot or cold coffee
	 * @param coffeeMachine that the client will use
	 */
	public Station(String name, String line) {
		this.name = name;
		this.line = line;
		edges = new ArrayList<Station>();
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
	 * Override toString to output client id, brewTime and hot.
	 *
	 * @return string
	 */
	@Override   
	public String toString() {
		return name+": "+line;
	}
}

