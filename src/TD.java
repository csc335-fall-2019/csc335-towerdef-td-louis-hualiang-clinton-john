import javafx.application.Application;

/******************************************************************************
 * FILE: Connect4.java
 * <pre>
 * ASSIGNMENT: Project 5: Networked Connect 4
 * COURSE: CSc 335, Fall 2019
 * PURPOSE: Launches a game of tower defense.
 * 
 * USAGE:
 * java TD
 * </pre>
 * 
 * @author Hualiang Qin
 * @author Louis Galluzzi
 * @author Clinton Kral
 * @author John Stockey
 *****************************************************************************/
public class TD {
	/**
	 * Purpose: Launches a TDView.
	 * 
	 * @param args A String[] of commands passed via command line.
	 */
	public static void main(String[] args) {
		Application.launch(TDView.class, args);
	}
}
