package sudoku;

import java.io.*;
import java.util.Arrays;
import java.util.Random;
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
	
	public static SudokuBoard generateBoard(int numClues) {
		// randomly seed 9 cells 1-9
		int[][] board = new int[9][9];
		Random random = new Random();
		for (int i = 1; i <= 9; i++) {
			int[] cell = randomCell(random);
			int row = cell[0], col = cell[1];
			if (board[row][col] != 0) {
				i--;
				break;
			}
			board[row][col] = i;
		}
		SudokuBoard sudokuBoard = new SudokuBoard(board);
		SudokuSolver.solveSudoku(sudokuBoard);
		return sudokuBoard;
	}

	
	public static int[] randomCell(Random random) {
		int[] arr = new int[2];
		int cell = random.nextInt(81);
		arr[0] = cell / 9;
		arr[1] = cell % 9;
		return arr;
	}
}
