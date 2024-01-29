package sudoku;

import java.util.Random;

public class SudokuSolver {
	public static boolean solveSudoku(SudokuBoard board) {
		int[] cell = findEmptyCell(board);
		// if there is no empty cell, the board is solved
		if(cell == null) {
			return true;
		}
		int row = cell[0], col = cell[1];
		boolean[] guessed = new boolean[9];
		Random random = new Random();
		// make guesses 1-9 for the empty cell
		for (int i = 0; i < 9; i++) {
			int val = 0;
			do {
				val = random.nextInt(9) + 1;
			} while (guessed[val - 1]);
			guessed[val - 1] = true;
			if (isValidGuess(board, val, row, col)) {
				// make guess
				board.setCellValue(row, col, val);
				// recursively see if this guess results in a solved board
				if(solveSudoku(board)) {
					return true;
				}
				// guess doesn't result in a solved board, so backtrack
				board.setCellValue(row, col, 0);
			}
		}
		// there are no valid guesses for current cell, so previous cell must be
		// invalid. backtrack.
		return false;
	}
	
	private static int[] findEmptyCell(SudokuBoard board) {
		// finds an empty cell (value of 0)
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				if (board.getCellValue(row, col) == 0) {
					return new int[] {row, col};
				}
			}
		}
		return null; // there are no empty cells
	}
	
	private static boolean isValidGuess(SudokuBoard board, int num, int row, int col) {
		// checks move along row/column
		for (int i = 0; i < 9; i++) {
			if(board.getCellValue(i, col) == num || board.getCellValue(row, i) == num) {
				return false; // invalid guess
			}
		}
		
		// checks move in 3x3 subgrid
		int startRow = row - (row % 3), startCol = col - (col % 3);
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (board.getCellValue(startRow + i, startCol + j) == num) {
					return false; // invalid guess
				}
			}
		}
		return true; // valid move
	}
}
