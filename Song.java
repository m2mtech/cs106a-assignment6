/*
 * File: Song.java
 * ---------------
 * Keeps track of the information for one song
 * in the music shop, including its name, the band
 * that it is by, and its price.
 */
public class Song {

	/** Constructor
	 * Note that the song name and band name are immutable 
	 * once the song is created.
	 */
	public Song(String songName, String bandName, double songPrice) { 
		title = songName;
		band = bandName;
		price = songPrice;
	}
	
	public String getSongName() {
		return title;
	}
	
	public String getBandName() {
		return band;
	}
	
	public void setPrice(double songPrice) {
		price = songPrice;
	}
	
	public double getPrice() {
		return price;
	}
	
	/** Returns a string representation of a song, listing 
	 * the song name, the band name, and its price.
	 */
	public String toString() {
		return ("\"" + title + "\" by " + band + " costs $" + price);
	}
	
	/* private instance variables */
	private String title;
	private String band;
	private double price;
}