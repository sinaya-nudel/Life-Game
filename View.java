package q2;
/*
 * Author: Sinaya Nudel 203191663
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * this class manages the visualization of the data for the life game matrix
 */

public class View {
	
	private static final Color WHITE = java.awt.Color.WHITE;
	private static final Color BLACK = java.awt.Color.BLACK;
	private static final int CELL_SIZE = 60;
	private JFrame frame;				
	private JPanel matrixPanel; 		
	private JButton restartButton;
	private int sizeY;
	private int sizeX;
	
	public View(int[][] matrix, int size_y ,int size_x) {
		
		this.sizeY = size_y;
		this.sizeX = size_x;
		this.frame = new JFrame();
		this.matrixPanel = new JPanel();
		this.restartButton = new JButton();
		Dimension matrixDim = new Dimension(CELL_SIZE,CELL_SIZE);
		Dimension frameDim = new Dimension(CELL_SIZE * sizeX,CELL_SIZE * sizeY);
		Dimension buttonDim = new Dimension(CELL_SIZE,CELL_SIZE);
		this.frame.setLayout(new BorderLayout());
		this.frame.setSize(frameDim);
		this.matrixPanel.setSize(matrixDim);
		GridLayout grid = new GridLayout(sizeX, sizeY);
		this.matrixPanel.setLayout(grid);
		this.matrixPanel.setBackground(WHITE);
		setStartGenMatrix(matrix, sizeX, sizeY);
		this.frame.add(this.matrixPanel, BorderLayout.CENTER);
		this.restartButton.setText("Restart");
		this.restartButton.setSize(buttonDim);
		this.frame.add(this.restartButton, BorderLayout.NORTH);
		this.frame.setVisible(true);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //will call the system exit method when the user press the exit button

	}
	
	public JButton getRestartBtn() {
		return this.restartButton;
	}
	
	
	private void setStartGenMatrix(int[][] startGenMatrix, int  size_x, int size_y) {
		Color cell_color;
		for(int i=0;i<size_y;i++) {
			for(int j=0;j<size_x;j++) {
				JPanel panel = new JPanel();
				if(startGenMatrix[i][j]==1) {
					cell_color = BLACK;
				}
				else {
					cell_color = WHITE;
				}
				panel.setBackground(cell_color);
				this.matrixPanel.add(panel);
			}
		}
		
	}


	public void setNewGenMatrix(int[][] nextGenMatrix) {
		Color cell_color;
		for(int i=0;i<sizeY;i++) {
			for(int j=0;j<sizeX;j++) {
				if(nextGenMatrix[i][j]==1) {
					cell_color = BLACK;
				}
				else {
					cell_color = WHITE;
				}
				this.matrixPanel.getComponent((i*sizeY)+j).setBackground(cell_color); 
			}
		}
	}
	

	
}
