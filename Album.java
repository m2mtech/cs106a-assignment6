/*
 * File: Album.java
 * ----------------
 * Keeps track of all the information for one album 
 * in the music shop, including its name, the band that 
 * its by, and the list of songs it contains.
 */
import java.util.*;

public class Album {

	/** Constructor
	 * Note that the album name and band name are immutable * once the album is created.
	 */
	public Album(String albumName, String bandName) { 
		title = albumName;
		band = bandName;
	}

	// Constructor for "old" MusicShop application
	public Album(String album, String bandName, int stock) {
		title = album;
		band = bandName;
		numStocked = stock;
	}


	public String getAlbumName() {
		return title;
	}
	public String getBandName() {
		return band;
	}

	public int getNumStocked() {
		return numStocked;
	}

	/** Adds a song to this album.  There is no duplicate
	 *  checking for songs that are added.
	 */
	public void addSong(Song song) {
		songs.add(song);
	}

	/** Returns an iterator over all the songs that are
	 *  on this album.
	 */
	public Iterator<Song> getSongs() {
		return songs.iterator();
	}
	
	/** Returns a string representation of an album, listing 
	 * the album name and the band name.
	 */
	public String toString() {
		return ("\"" + title + "\" by " + band);
	}

	// Returns a string representation of an album, listing
	// the album name, the band name, and the number in stock 
	/*public String toString() {
		return ("\"" + title +
				"\" by " + band +
				": " + numStocked + " in stock");
	}*/

	/* private instance variables */
	private String title;
	private String band;
	private int numStocked;
	private ArrayList<Song> songs = new ArrayList<Song>();

}