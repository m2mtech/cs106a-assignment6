/*
 * File: NameSurferEntry.java
 * --------------------------
 * This class represents a single entry in the database.  Each
 * NameSurferEntry contains a name and a list giving the popularity
 * of that name for each decade stretching back to 1900.
 */

import acm.util.*;
import java.util.*;

public class NameSurferEntry implements NameSurferConstants {

	/**
	 * private constants
	 */
	private static final String NAMESTRING_DELIMITER = " ";
	private static final String TOSTRING_START_RANK = "[";
	private static final String TOSTRING_STOP_RANK = "]";
	private static final String TOSTRING_DELIMITER = " ";
	
	/**
	 * private instance variables
	 */
	private String name;
	private TreeMap<Integer,Integer> ranks = new TreeMap<Integer,Integer>();
	
	/* Constructor: NameSurferEntry(line) */
	/**
	 * Creates a new NameSurferEntry from a data line as it appears
	 * in the data file.  Each line begins with the name, which is
	 * followed by integers giving the rank of that name for each
	 * decade.
	 */
	public NameSurferEntry(String line) {
		Scanner scanner = new Scanner(line);
		scanner.useDelimiter(NAMESTRING_DELIMITER);
		if (!scanner.hasNext()) {
			name = "error";
			return;
		}
		name = scanner.next();
		int year = START_DECADE;
		while (scanner.hasNext()) {
			ranks.put(year, Integer.parseInt(scanner.next()));
			year += DECADE;
		}
	}

	/* Method: getName() */
	/**
	 * Returns the name associated with this entry.
	 */
	public String getName() {
		return name;
	}

	/* Method: getRank(decade) */
	/**
	 * Returns the rank associated with an entry for a particular
	 * decade.  The decade value is an integer indicating how many
	 * decades have passed since the first year in the database,
	 * which is given by the constant START_DECADE.  If a name does
	 * not appear in a decade, the rank value is 0.
	 */
	public int getRank(int decade) {
		Integer rank = ranks.get(START_DECADE + DECADE * decade);
		if (rank == null) return 0;
		return rank;
	}

	/* Method: toString() */
	/**
	 * Returns a string that makes it easy to see the value of a
	 * NameSurferEntry.
	 */
	public String toString() {
		String result = name + TOSTRING_DELIMITER + TOSTRING_START_RANK;
		String next = "";
		Iterator<Integer> it = ranks.keySet().iterator(); 
		while (it.hasNext()) {
			result += next + ranks.get(it.next());
			next = TOSTRING_DELIMITER;
		}
		result += TOSTRING_STOP_RANK;
		return result;
	}
}

