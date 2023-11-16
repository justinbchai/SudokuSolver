package sudoku;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class SudokuBoard {
	private int[][] sudokuBoard;

	public SudokuBoard(int[][] sudokuBoard) {
		this.sudokuBoard = sudokuBoard;
	}
	
	public SudokuBoard(String filepath) {
		this.sudokuBoard = new int[9][9];
		try {
			File file = new File(filepath);
			Scanner scanner = new Scanner(file);
			for (int row = 0; row < 9; row++) {
				for (int col = 0; col < 9; col++) {
					this.sudokuBoard[row][col] = scanner.nextInt();
				}
				if (scanner.hasNextLine()) {
					scanner.nextLine();
				}
			}
			scanner.close();
		} catch (Exception e) {
			System.err.println("Invalid File");
			e.printStackTrace();
		}
		
	}

	public SudokuBoard() {
		this.sudokuBoard = new int[][] {{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
										{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },
										{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
										{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
										{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },
										{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
										{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
										{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },
										{ 0, 0, 0, 0, 0, 0, 0, 0, 0 } };
	}
	

	public void setCellValue(int row, int col, int value) {
		this.sudokuBoard[row][col] = value;
	}

	public int getCellValue(int row, int col) {
		return this.sudokuBoard[row][col];
	}

	public String toString() {
		StringBuffer str = new StringBuffer();
		for (int[] row : this.sudokuBoard) {
			str.append(Arrays.toString(row));
			str.append("\n");
		}
		return str.toString().trim();
	}
}
