package q2;
/*
 * Author: Sinaya Nudel 203191663
 */
import java.util.concurrent.TimeUnit;
/*
 * this main thread class is responsible for the timing of the changes in the model and the view objects. In this class the delay timing is determined.
 */
public class MainThread extends Thread {
	
	private static final int DELAY = 1; //the delay in seconds between each generation.
	private Controller controller;
	private boolean isRestart;
	
	public MainThread(Controller controllerx) {
		this.controller = controllerx;
		isRestart=false;
	}
	/*the run method will keep running until the user will force it to end by clicking the exit button.
	 * the "isRestart" instance variable state will determine the behavior of the run method. 
	 * 
	 */
	public void run() {
		while(true) {
			try {
				TimeUnit.SECONDS.sleep(DELAY); //forcing a delay between each generation
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(isRestart){
				this.newGeneration(this.isRestart);
				this.isRestart=false;
			}
			else
				this.newGeneration(this.isRestart);
		}
		
	}
	/*This method creates a new generation - using the model and the view objects. 
	 * if the boolean restart parameter is true - a new random generation will be create.
	 * else, if false, the next generation will be the continuous of the last one.
	 */
	
	public void newGeneration(boolean restart) {
		if(restart==true)
			this.controller.getModel().restart();
		else
			this.controller.getModel().updateNextGen();
		int[][] nextGenMatrix = this.controller.getModel().getMatrix();
		this.controller.getView().setNewGenMatrix(nextGenMatrix); 				 	  
	}
	
	public void set_restart(boolean value){
		this.isRestart=value;
	}
}

