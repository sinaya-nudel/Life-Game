package q2;
/*
 * Author: Sinaya Nudel 203191663
 */
import javax.swing.JOptionPane;

/* 
 * The controller class translates the user's interactions with the view into actions that the model will perform. 
 * It keeps view and model separated.
 * the controller includes the main thread which runs the life game.
 */
public class Controller {
	
	
	private View view;
	private Model model;
	private int sizeY;
	private int sizeX;
	private Listener listener;
	private MainThread main_thread;
	private static final int MAX_SIZE=20;
	private static final int MIN_SIZE=3;
	private boolean isValid;
	
	public Controller() {
		if(setSize()){ //if the user chose to set the size of the matrix 
			isValid=true;
			model = new Model(this.sizeY, this.sizeX);								
			view = new View(model.getMatrix(), this.sizeY, this.sizeX);		
			listener = new Listener(this);										
			view.getRestartBtn().addActionListener(this.listener);				
			main_thread = new MainThread(this);		
		}
		else //if the user chose not to set the size of the matrix
			isValid=false;

	}
	
	public Model getModel() {
		return this.model;
	}
	
	public View getView() {
		return this.view;
	}
	public boolean get_isValid(){
		return this.isValid;
	}
	public MainThread getMainThread() {
		return this.main_thread;
	}
	
	public void startGame() {
		while(true){
			this.main_thread.run();
		}
	}

	public void restart() {
		this.main_thread.set_restart(true);
	}
	

	private boolean setSize() { //return true is the user chose to set the size of the matrix.
		boolean isValid=true;
		boolean askForInput=true;
		while(askForInput){ //this loop runs until the user's input is valid or if the user force the loop's ending by exit button. 
			isValid=true;
			String size = JOptionPane.showInputDialog(null, "Please enter the size of the matrix as positive integer between " + MIN_SIZE + " to "+MAX_SIZE+": ");
			if(!isInteger(size)){
				isValid=false;
			}
			if(isValid) {
				int _size= Integer.parseInt(size);
				sizeY = _size;
		        sizeX = _size;
		        if(!isInRange(_size)) {
		        	isValid = false;
		        }
		        else
		        	askForInput=false;
			}
			
			if(!isValid){
	    			int reply = JOptionPane.showConfirmDialog(null,
	    			    "Invalid input! Do you want to try again?", "message",
	    			    JOptionPane.YES_NO_OPTION);
	    			if(reply == JOptionPane.YES_OPTION){
	    				askForInput=true;
	    			}
	    			else
	    				askForInput=false;
	    		}
		}
        return isValid;
	}
	
	public boolean isInteger(String str){ //returns true if the string which given as parameter is an integer
		boolean value=true;
		if(str==null||str.equals(""))
			value=false;
		else{
			for(int i=0;i<str.length();i++) {
				if(!Character.isDigit(str.charAt(i))) {
					value = false;
				}
			}
		}
		return value;
	}
	
	public boolean isInRange(int x){ //return true of the integer which given as parameter is valid due to the maximum and minimum values.
		boolean value=true;
		if(x>MAX_SIZE||x<MIN_SIZE){
			value=false;
		}
		return value;
	}
	
}

