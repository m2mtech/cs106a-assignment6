/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes
 * or the window is resized.
 */

import acm.graphics.*;

import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class NameSurferGraph extends GCanvas
implements NameSurferConstants, ComponentListener {

	/**
	 * private data types
	 */
	private enum Marker {
		CIRCLE, SQUARE, TRIANGLE, DIAMOND
	}

	/**
	 * private constants
	 */
	private static final int LABEL_OFFSET = 3;
	private static final Color[] COLORS = {Color.BLACK, Color.RED, Color.BLUE, Color.MAGENTA};
	private static final int N_COLORS = 4;
	private static final int N_MARKERS = 4;
	private static final double SIZE_MARKER = 10.0;

	/**
	 * private instance variables
	 */
	private ArrayList<NameSurferEntry> data = new ArrayList<NameSurferEntry>();
	private TreeMap<Integer,String> topName = new TreeMap<Integer,String>();
	private TreeMap<Integer,Integer> topRank = new TreeMap<Integer,Integer>();
	private HashMap<String,Color> colors = new HashMap<String,Color>();
	private int colorIndex = 0;
	private HashMap<String,Marker> markers = new HashMap<String,Marker>();
	private int markerIndex = 0;

	/**
	 * Creates a new NameSurferGraph object that displays the data.
	 */
	public NameSurferGraph() {
		addComponentListener(this);
	}


	/**
	 * Clears the list of name surfer entries stored inside this class.
	 */
	public void clear() {
		data.clear();
		update();
	}


	/* Method: addEntry(entry) */
	/**
	 * Adds a new NameSurferEntry to the list of entries on the display.
	 * Note that this method does not actually draw the graph, but
	 * simply stores the entry; the graph is drawn by calling update.
	 */
	public void addEntry(NameSurferEntry entry) {
		if (entry == null) return;
		if (data.contains(entry)) return;
		data.add(entry);
		//System.out.println(entry);
		
		for (int i = 0; i < NDECADES; i++) {
			addTopRank(entry, i);
		}	
		
		String name = entry.getName();
		colors.put(name, nextColor());
		markers.put(name, nextMarker());
	
		update();
	}
	
	/**
	 * helper method to get the next unused color
	 */
	private Color nextColor() {
		return COLORS[colorIndex++ % N_COLORS];	
	}

	/** 
	 * helper method to get the next unused marker style
	 */
	private Marker nextMarker() {
		return Marker.values()[markerIndex++  % N_MARKERS];	
	}
	
	/**
	 * add the top rank if applicable
	 */
	private void addTopRank(NameSurferEntry entry, int i) {
		int rank = entry.getRank(i);
		if (rank == 0) return;
		if (!topRank.containsKey(i)) {
			topRank.put(i, rank);
			topName.put(i, entry.getName());
		} else if (rank < topRank.get(i)) {
			topRank.put(i, rank);
			topName.put(i, entry.getName());				
		}
	}
	
	/**
	 * remove entry from display
	 * @param entry
	 */
	public void removeEntry(NameSurferEntry entry) {
		if (!data.contains(entry)) return;
		data.remove(entry);
		
		String name = entry.getName();
		for (int i = 0; i < NDECADES; i++) {
			if (!topRank.containsKey(i)) continue;
			if (!name.equals(topName.get(i))) continue;
			topRank.remove(i);
			topName.remove(i);
			Iterator<NameSurferEntry> it = data.iterator();
			while (it.hasNext()) {
				addTopRank(it.next(), i);
			}
		}
		
		colors.remove(name);
		markers.remove(name);
	
		update();
	}

	/**
	 * Updates the display image by deleting all the graphical objects
	 * from the canvas and then reassembling the display according to
	 * the list of entries. Your application must call update after
	 * calling either clear or addEntry; update is also called whenever
	 * the size of the canvas changes.
	 */
	public void update() {
		removeAll();
		drawGrid();
		Iterator<NameSurferEntry> it = data.iterator();
		while (it.hasNext()) {
			drawEntry(it.next());
		}
	}

	/**
	 * draw graph of entry
	 * @param entry
	 */
	private void drawEntry(NameSurferEntry entry) {
		String name = entry.getName();
		int rank = entry.getRank(0);
		double dx = getWidth() * 1.0 / NDECADES;
		double x0 = 0;
		double y0 = yValue(rank);
		Color color = colors.get(name);
		Marker marker = markers.get(name);
		drawLabel(name, rank, 0, x0, y0, color);
		drawMarker(marker, x0, y0, color);
		for (int i = 1; i < NDECADES; i++) {
			rank = entry.getRank(i);
			double x1 = x0 + dx;
			double y1 = yValue(rank);
			GLine line = new GLine(x0, y0, x1, y1);
			line.setColor(color);
			add(line);
			x0 = x1;
			y0 = y1;
			drawLabel(name, rank, i, x0, y0, color);
			drawMarker(marker, x0, y0, color);
		}
	}

	/**
	 * draw a marker of type centered at x/y in given color
	 * @param type
	 * @param x
	 * @param y
	 * @param color
	 */
	private void drawMarker(Marker type, double x, double y, Color color) {
		GObject marker;
		GPen helper;
		double halfSize = SIZE_MARKER / 2.0; 
		double x0 = x - halfSize - 0.5;
		double y0 = y - halfSize - 0.5;
		switch (type) {
		case CIRCLE:
			marker = new GOval(x0, y0, SIZE_MARKER, SIZE_MARKER);
			break;
		case SQUARE:
			marker = new GRect(x0, y0, SIZE_MARKER, SIZE_MARKER);
			break;
		case TRIANGLE:
			helper = new GPen(x, y0);
			helper.drawLine(halfSize, SIZE_MARKER);
			helper.drawLine(-SIZE_MARKER, 0);
			helper.drawLine(halfSize, -SIZE_MARKER);
			marker = helper;
			break;
		case DIAMOND:
			helper = new GPen(x, y0);
			helper.drawLine(halfSize, halfSize);
			helper.drawLine(-halfSize, halfSize);
			helper.drawLine(-halfSize, -halfSize);
			helper.drawLine(halfSize, -halfSize);
			marker = helper;
			break;
		default:
			return;
		}
		marker.setColor(color);
		add(marker);		
	}

	/**
	 * calculate y value from rank
	 * @param rank
	 * @return
	 */
	private double yValue(int rank) {
		if (rank == 0) rank = MAX_RANK;
		return (getHeight() - 2 * GRAPH_MARGIN_SIZE) * rank / MAX_RANK + GRAPH_MARGIN_SIZE;
	}

	/**
	 * draw name label with rank at x/y in color
	 * @param name
	 * @param rank
	 * @param index
	 * @param x
	 * @param y
	 * @param color
	 */
	private void drawLabel(String name, int rank, int index, double x, double y, Color color) {
		String text = name;
		if (rank == 0) {
			text += " *";
		} else {
			text += " " + rank;
		}
		GLabel label = new GLabel(text, x + LABEL_OFFSET, y - LABEL_OFFSET);
		if (isTopRank(name, index)) {
			label.setFont(label.getFont().deriveFont(Font.BOLD));
		}
		label.setColor(color);
		add (label);
	}
	
	/**
	 * check if name has the top rank of the given decade
	 * @param name
	 * @param index
	 * @return
	 */
	private boolean isTopRank(String name, int index) {
		if (!topName.containsKey(index)) return false;
		return name.equals(topName.get(index));
	}

	/**
	 * draw grid
	 */
	private void drawGrid() {
		double height = getHeight();
		double width = getWidth();
		double x = 0;
		double columnWidth = width * 1.0 / NDECADES;
		int year = START_DECADE;
		double yText = height - GRAPH_MARGIN_SIZE / 3;
		for (int i = 0; i < NDECADES; i++) {
			add(new GLine(x, 0, x, height));
			add(new GLabel(" " + year, x, yText));
			x += columnWidth;
			year += DECADE;
		}
		double yTopLine = GRAPH_MARGIN_SIZE;
		double yBottomLine = height - GRAPH_MARGIN_SIZE;
		add(new GLine(0, yTopLine, width, yTopLine));
		add(new GLine(0, yBottomLine, width, yBottomLine));
	}


	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) { }
	public void componentMoved(ComponentEvent e) { }
	public void componentResized(ComponentEvent e) { update(); }
	public void componentShown(ComponentEvent e) { }
}
