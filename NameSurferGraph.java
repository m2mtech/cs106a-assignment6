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
	 * private constants
	 */
	private static final int LABEL_OFFSET = 3;
	private static final Color[] COLORS = {Color.BLACK, Color.RED, Color.BLUE, Color.MAGENTA};
	private static final int N_COLORS = 4;
	
	/**
	 * private instance variables
	 */
	private ArrayList<NameSurferEntry> data = new ArrayList<NameSurferEntry>();

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
		Color color = COLORS[data.indexOf(entry) % N_COLORS];
		drawLabel(name, rank, x0, y0, color);
		for (int i = 1; i < NDECADES; i++) {
			rank = entry.getRank(i);
			double x1 = x0 + dx;
			double y1 = yValue(rank);
			GLine line = new GLine(x0, y0, x1, y1);
			line.setColor(color);
			add(line);
			x0 = x1;
			y0 = y1;
			drawLabel(name, rank, x0, y0, color);
		}
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
	 * @param x
	 * @param y
	 * @param color
	 */
	private void drawLabel(String name, int rank, double x, double y, Color color) {
		String text = name;
		if (rank == 0) {
			text += " *";
		} else {
			text += " " + rank;
		}
		GLabel label = new GLabel(text, x + LABEL_OFFSET, y - LABEL_OFFSET);
		label.setColor(color);
		add (label);
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
