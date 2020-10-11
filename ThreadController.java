package q2;
/*
 * Author: Sinaya Nudel 203191663
 */

import java.util.concurrent.CyclicBarrier;

/*
 * the thread controller will manage all the cell threads.
 */
public class ThreadController {
	private int numOfThreads; //the total threads = the total cells in the matrix.
	private int activeThreads; //the number of threads that still calculating and updating the next generation.
	private int sizeX;
	private int sizeY;
	private int[][] currMatrix;
	private CyclicBarrier barrier; //this barrier is manage the waiting for all the threads to reach the same running point.
	private CellThread[][] threads;
	
	public ThreadController(int x, int y, int[][] curr){
		sizeX=x;
		sizeY=y;
		currMatrix=curr;
		numOfThreads=x*y;
		barrier = new CyclicBarrier(numOfThreads);
		threads= new CellThread[y][x];
		for(int i=0;i<x;i++){
			for(int j=0;j<y;j++){
				threads[j][i]=new CellThread(this.currMatrix, i, j, this.barrier,this);
				threads[j][i].start();
			}
		}
		activeThreads=numOfThreads;
		
	}
	
	public int[][] getNextGen(boolean restart){ //return the new generation matrix due to the boolean restart parameter. 
		synchronized (this) {
		    this.notifyAll(); //signal that the threads should start running and calculate the next generation.
		} 
		if(restart) { 
			for(int i = 0; i<sizeY;i++) {
				for(int j = 0;j<sizeX;j++) {
					this.threads[i][j].set_restart(true); //set all the cell threads to restart mode.
				}
			}
		}
		waitForAll(); //wait until all the threads finished the calculating and the updating for the next generation.
		restartActiveThreads(); //as all the threads finished the current round and should counts as non-active for the next round.
		return currMatrix;
	}
	
	public synchronized void finished(){ 
		activeThreads--; //the thread has finished its work, so 1 less thread is running right now.
		notifyAll();//the thread notice all that it finished its round
	}
	
	public synchronized void waitForAll(){ //waiting all the threads to finish the current round
		while(activeThreads>0){
			try{
				this.wait();
			}
			catch(InterruptedException e){}
		}
	}
	public void restartActiveThreads(){
		this.activeThreads=this.numOfThreads;
	}
	
}
