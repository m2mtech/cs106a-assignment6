/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.program.*;
import java.awt.event.*;
import javax.swing.*;

public class NameSurfer extends Program implements NameSurferConstants {

	/**
	 * private constants
	 */
	private static final String POSITION_OF_INTERACTORS = NORTH;
	private static final int N_CHARS_NAMEFIELD = 20;
	
	/**
	 * private instance variables
	 */
	private JTextField nameField;
	private JButton graphButton;
	private JButton clearButton;
	private NameSurferDataBase dataBase;
	private NameSurferGraph graph;
	
	/* Method: init() */
	/**
	 * This method has the responsibility for reading in the data base
	 * and initializing the interactors at the top of the window.
	 */
	public void init() {
		dataBase = new NameSurferDataBase(NAMES_DATA_FILE);
		
		graph = new NameSurferGraph();
		add(graph);
		
		nameField = new JTextField(N_CHARS_NAMEFIELD);
	    nameField.addActionListener(this);
	    graphButton = new JButton("Graph");
	    clearButton = new JButton("Clear");
	   
	    add(new JLabel("Name:"), POSITION_OF_INTERACTORS);
	    add(nameField, POSITION_OF_INTERACTORS);
	    add(graphButton, POSITION_OF_INTERACTORS);
	    add(clearButton, POSITION_OF_INTERACTORS);
	    
		addActionListeners();
	}

	/* Method: actionPerformed(e) */
	/**
	 * This class is responsible for detecting when the buttons are
	 * clicked, so you will have to define a method to respond to
	 * button actions.
	 */
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if ((source == nameField) || (source == graphButton)) {
			//println("Graph: \"" + nameField.getText() + "\"");			
			/*NameSurferEntry entry = new NameSurferEntry(nameField.getText());
			println(entry.getName());
			println(entry.getRank(0));
			println(entry.getRank(NDECADES - 2));
			println(entry.getRank(NDECADES));
			println(entry.toString());*/
			//println("Graph: \"" + dataBase.findEntry(nameField.getText()) + "\"");
		} else if (source == clearButton) {
			//println("Clear");
		}
	}

}
