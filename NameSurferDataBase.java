import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.TreeMap;

/*
 * File: NameSurferDataBase.java
 * -----------------------------
 * This class keeps track of the complete database of names.
 * The constructor reads in the database from a file, and
 * the only public method makes it possible to look up a
 * name and get back the corresponding NameSurferEntry.
 * Names are matched independent of case, so that "Eric"
 * and "ERIC" are the same names.
 */

public class NameSurferDataBase implements NameSurferConstants {
	
	/**
	 * private instance variables
	 */
	private TreeMap<String,NameSurferEntry> data = new TreeMap<String,NameSurferEntry>();
	
	/* Constructor: NameSurferDataBase(filename) */
	/**
	 * Creates a new NameSurferDataBase and initializes it using the
	 * data in the specified file.  The constructor throws an error
	 * exception if the requested file does not exist or if an error
	 * occurs as the file is being read.
	 */
	public NameSurferDataBase(String filename) {
		File file = new File(filename);
		try {
			Scanner scanner = new Scanner(new FileReader(file));
			while (scanner.hasNextLine()) {
				NameSurferEntry entry = new NameSurferEntry(scanner.nextLine());
				String name = entry.getName().toLowerCase();
				if (!name.equals("error"))
					data.put(name, entry);
			}
			scanner.close();
		} catch (IOException ex) {
			// don't care do nothing
		}
	}
	
	/* Method: findEntry(name) */
	/**
	 * Returns the NameSurferEntry associated with this name, if one
	 * exists.  If the name does not appear in the database, this
	 * method returns null.
	 */
	public NameSurferEntry findEntry(String name) {
		return data.get(name.toLowerCase());
	}
}

