package q2;
/*
 * Author: Sinaya Nudel 203191663
 */


import java.util.ArrayList;
import java.util.Collections;


	public class Model {
		private int[][] matrix; 
		private int sizeX; //the number of cell for each row
		private int sizeY; //the number of cell for each column
		private ThreadController thread_control;


		public Model(int size_y, int size_x){
			this.sizeX=size_x;
			this.sizeY=size_y;
			matrix = new int[sizeY][sizeX];
			randMatrix(matrix); //puts a random 50:50 life or death values to the cells in the matrix. life as '1' and death as '0'.
			thread_control = new ThreadController(sizeX,sizeY,this.matrix); //creation of the new thread controller that will create and manage the cell threads.
			
		}
		
		public void randMatrix(int[][] mat){   //puts a random 50:50 life or death values to the cells in the matrix. life as '1' and death as '0'.
			
			ArrayList<Integer> rand_nums = new ArrayList<Integer>(); //creating an arraylist with the same number of cells as the matrix. puts 0 or 1 50:50.
			for(int i=0;i<(this.sizeX*this.sizeY);i++){
				if(i<((this.sizeX*this.sizeY)/2))
					rand_nums.add(0);
				else
					rand_nums.add(1);
			}
			Collections.shuffle(rand_nums); //making the order of '0' and '1' random in the arraylist, and moving all the values to the matrix.
			for(int j=0;j<mat.length;j++)
				for(int i=0;i<mat[j].length;i++)
					mat[j][i]= rand_nums.remove(0);
		
		}
		
		public void restart(){
			matrix=thread_control.getNextGen(true);
		}
		
		public void updateNextGen(){
			matrix=thread_control.getNextGen(false);
		}
		public int[][] getMatrix(){
			return this.matrix;
		}
		

}