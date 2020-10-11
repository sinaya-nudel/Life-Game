package q2;
/*
 * Author: Sinaya Nudel 203191663
 */
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
/* the cell thread class is manages the behavior of each thread which should calculate and update the life-game matrix.
 * 
 */
public class CellThread extends Thread{

	private int ind_x; //index x in the matrix
	private int ind_y; //index y in the matrix
	private CyclicBarrier barrier; //the barrier that will manage this thread with all the other cell threads.
	private int nextValue; //this instance variable will keep the value of the next generation after the calculation. 1=life, 0=death.
	private int[][] currentMatrix; //the matrix of the life-game
	private ThreadController con; //the thread controller that will manage this thread with all the other cell threads. 
	private boolean restart; 
	
	
	public CellThread(int[][] current_matrix, int x, int y, CyclicBarrier barrierX,ThreadController controller) {
		this.ind_x = x;
		this.ind_y = y;
		this.barrier = barrierX;
		this.nextValue=0;
		this.currentMatrix=current_matrix;
		this.con= controller;
		this.restart=false;
	}
	public void run() {
		while(true) {
			synchronized (con) { //waiting for a signal from the thread controller to notify the thread
			    try {
					con.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} 
			try {
				barrier.await();  //waiting for a signal from the barrier. Will notify the threads only when all the cell threads reached to this point. 
			} catch (BrokenBarrierException | InterruptedException e) {
				e.printStackTrace();
			}
			if(!restart) {
				calculateValue();		 //updates the nextValue of this thread.
			}
			else {
				restartValue();
				this.restart = false;
			}
			try {
				barrier.await();		 //waiting for a signal from the barrier. Will notify the threads only when all the cell threads reached to this point = all the calculations for the next generation have done.				
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
			updateCurrCell(); //Updates the life-game matrix.
			con.finished(); //signal for the thread controller that this thread finished the updates for this generation.
		}
	}
	
	private int howManyNeighbors(int i,int j,int[][] mat){ //counts the number of neighbors for the given cell (due its indexes)
		int sum=0;
		if(isValid(i-1,j-1)&&mat[i-1][j-1]==1)
			sum+=1;
		if(isValid(i-1,j)&&mat[i-1][j]==1)
			sum+=1;
		if(isValid(i,j-1)&&mat[i][j-1]==1)
			sum+=1;
		if(isValid(i+1,j+1)&&mat[i+1][j+1]==1)
			sum+=1;
		if(isValid(i+1,j)&&mat[i+1][j]==1)
			sum+=1;
		if(isValid(i,j+1)&&mat[i][j+1]==1)
			sum+=1;
		if(isValid(i-1,j+1)&&mat[i-1][j+1]==1)
			sum+=1;
		if(isValid(i+1,j-1)&&mat[i+1][j-1]==1)
			sum+=1;
		
		return sum;
	}
	
	private boolean isValid(int i,int j){//checks if the cell is in the matrix's borders
		return (j>=0&&i>=0&&i<this.currentMatrix.length&&j<this.currentMatrix[0].length);
	}
	
	public int nextGenerationCell(int neighbors,int[][] mat ,int i, int j){ //gets the number of neighbors of the cell, the matrix and the indexes of the current cell- and check what should be it next value. 
		int state=0;
		if(mat[i][j]!=1){
				if(neighbors==3){
					state=1;
				}
				else{
					state= 0;
				}
			}
		else { //if(mat[i][j]==1)
				if(neighbors<=1)
					state=0;
				else if(neighbors>=4)
					state=0;
				else if(neighbors==3||neighbors==2)
					state=1;
			}
		return state;
	}
	
	public void calculateValue(){ //calculates the next value for this cell in the matrix and updates the variable nextValue.
		int neighbors = howManyNeighbors(ind_y,ind_x,currentMatrix);
		this.nextValue = nextGenerationCell(neighbors,currentMatrix ,ind_y, ind_x);
	}
	public void restartValue(){ //updates the variable nextValue with random value - 1 or 0 (life or death).
		Random rand = new Random(); 
		this.nextValue = rand.nextInt(1-0+1) +1;
	}
	public void updateCurrCell(){ //updates the cell in the matrix due to the next value. 
		this.currentMatrix[ind_y][ind_x]=this.nextValue;
	}
	public void set_restart(boolean res){
		this.restart=res;
	}
}