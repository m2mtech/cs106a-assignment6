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
	 * Creates a new NameSurferGraph object that displays the data.
	 */
	public NameSurferGraph() {
		addComponentListener(this);
		// You fill in the rest //
	}


	/**
	 * Clears the list of name surfer entries stored inside this class.
	 */
	public void clear() {
		// You fill this in //
	}


	/* Method: addEntry(entry) */
	/**
	 * Adds a new NameSurferEntry to the list of entries on the display.
	 * Note that this method does not actually draw the graph, but
	 * simply stores the entry; the graph is drawn by calling update.
	 */
	public void addEntry(NameSurferEntry entry) {
		// You fill this in //
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
	}

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
