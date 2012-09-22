/*
 * File: MusicShopDisplay
 * ----------------------
 * This file handles the display of album inventory for the music shop.
 * It provides dynamic redrawing of window contents when display is * resized.
 */
import acm.graphics.*;
import java.awt.event.*;

public class MusicShopDisplay extends GCanvas implements ComponentListener {

	// Constructor
	public MusicShopDisplay() {
		addComponentListener(this);
		lastAlbum = null;
	}

	// Display the album name, band name, and number in stock
	// for a single album if it is in our inventory. Otherwise, 
	// just clear the display.
	public void displayInventory(Album album) {
		removeAll();
		lastAlbum = album;
		if (album != null) {
			int numStocked = album.getNumStocked();
			add(new GLabel("Album [" + album.getAlbumName() + "] by ["
					+ album.getBandName() + "]"),
					10, (getHeight() - BAR_HEIGHT) / 2 - SPACER);
			// Display squares in dicating how many inventory 
			double nextX = SPACER;
			for(int i = 0; i < numStocked; i++) {
				double barLength = (getWidth() / (double)MAX_INVENTORY) - SPACER;
				GRect rect = new GRect(nextX,
						(getHeight() - BAR_HEIGHT) / 2,
						barLength, BAR_HEIGHT);
				rect.setFilled(true);
				add(rect);
				nextX += barLength + SPACER;
			}
			GLabel label = new GLabel(numStocked + " in stock"); 
			add(label, 10, (getHeight() + BAR_HEIGHT) / 2 +
					SPACER + label.getAscent());
		} 
	}

	// Whenever we need to update the display, continue to 
	// display the last album shown
	public void update() {
		displayInventory(lastAlbum);
	}
	
	public void componentHidden(ComponentEvent e) { }
	public void componentMoved(ComponentEvent e) { }
	public void componentResized(ComponentEvent e) { update(); } 
	public void componentShown(ComponentEvent e) { }
	
	/* constants */
	private static final double BAR_HEIGHT = 20;
	private static final double SPACER = 10;
	private static final int MAX_INVENTORY = 20;
	
	/* private instance variables */
	private Album lastAlbum;
	
}