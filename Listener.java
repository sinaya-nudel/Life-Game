package q2;
/*
 * Author: Sinaya Nudel 203191663
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/*
 * This listener will catch the event of the restart's pressing and will use its controller's method to make an action.
 */
public class Listener implements ActionListener {
	
	private Controller controller;
	
	public Listener(Controller controllerX) {
		this.controller = controllerX;
	}
	public void actionPerformed(ActionEvent e) {
		this.controller.restart();
	}

}
