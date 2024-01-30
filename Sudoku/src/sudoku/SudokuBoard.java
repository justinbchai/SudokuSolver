package sudoku;

import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
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
	
	public int[][] getCells() {
		int[][] copy = new int[9][];
		for (int i = 0; i < 9; i++) {
			copy[i] = sudokuBoard[i].clone();
		}
		return copy;
	}
	
	public void setCells(int[][] newBoard) {
		sudokuBoard = newBoard;
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
		
		// remove random cells and check if puzzle has 1 solution
		removeCellsFromFilledBoard(sudokuBoard, 0, 0, numClues);
		
		return sudokuBoard;
	}

	
	public static int[] randomCell(Random random) {
		int[] arr = new int[2];
		int cell = random.nextInt(81);
		arr[0] = cell / 9;
		arr[1] = cell % 9;
		return arr;
	}
	
	private static void removeCellsFromFilledBoard(SudokuBoard board, int numRemoved, int recur, int minClues) {
		System.out.println(board + "\nRecurs " + recur + "\nRemoved: " + numRemoved);
		Random random = new Random();
		int[][] cells = board.getCells();
		int[] cell = new int[2];
		if (recur > 50 || 81 - numRemoved - 1 < minClues) {
			return;
		} else if (numRemoved < 20) {
			for (int i = 0; i < 2; i++) {
				do {
					cell = randomCell(random);
				} while (cells[cell[0]][cell[1]] == 0);
				cells[cell[0]] [cell[1]] = 0;
				cells[8 - cell[0]] [8 - cell[1]] = 0;
			}
			if (findNumSolutions(0, 0, cells, (byte) 0) > 1) {
				removeCellsFromFilledBoard(board, numRemoved, recur + 1, minClues);
			} else {
				board.setCells(cells);
				removeCellsFromFilledBoard(board, numRemoved + 4, recur + 1, minClues);
			}
		} else if (numRemoved < 30) {
			do {
				cell = randomCell(random);
			} while (cells[cell[0]][cell[1]] == 0);
			cells[cell[0]] [cell[1]] = 0;
			cells[8 - cell[0]] [8 - cell[1]] = 0;
			if (findNumSolutions(0, 0, cells, (byte) 0) > 1) {
				removeCellsFromFilledBoard(board, numRemoved, recur + 1, minClues);
			} else {
				board.setCells(cells);
				removeCellsFromFilledBoard(board, numRemoved + 2, recur + 1, minClues);
			}
		} else {
			do {
				cell = randomCell(random);
			} while (cells[cell[0]][cell[1]] == 0);
			cells[cell[0]] [cell[1]] = 0;
			if (findNumSolutions(0, 0, cells, (byte) 0) > 1) {
				removeCellsFromFilledBoard(board, numRemoved, recur + 1, minClues);
			} else {
				board.setCells(cells);
				removeCellsFromFilledBoard(board, numRemoved + 1, recur + 1, minClues);
			}
		}
	}
	
	// https://stackoverflow.com/questions/24343214/determine-whether-a-sudoku-has-a-unique-solution
	// returns 0, 1 or more than 1 depending on whether 0, 1 or more than 1 solutions are found
	public static byte findNumSolutions(int i, int j, int[][] cells, byte count /*initailly called with 0*/) {
	    if (i == 9) {
	        i = 0;
	        if (++j == 9) {
	            return (byte) (1 + count);
	        }
	    }
	    if (cells[i][j] != 0)  // skip filled cells
	        return findNumSolutions(i+1,j,cells, count);
	    // search for 2 solutions instead of 1
	    // break, if 2 solutions are found
	    for (int val = 1; val <= 9 && count < 2; ++val) {
	        if (legalMove(i, j, cells, val)) {
	            cells[i][j] = val;
	            // add additional solutions
	            count = findNumSolutions(i+1,j,cells, count);
	        }
	    }
	    cells[i][j] = 0; // reset on backtrack
	    return count;
	}
	
	private static boolean legalMove(int row, int col, int[][] cells, int num) {
		// checks move along row/column
		for (int i = 0; i < 9; i++) {
			if(cells[i][col] == num || cells[row][i] == num) {
				return false; // invalid guess
			}
		}
		
		// checks move in 3x3 subgrid
		int startRow = row - (row % 3), startCol = col - (col % 3);
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (cells[startRow + i][startCol + j] == num) {
					return false; // invalid guess
				}
			}
		}
		return true; // valid move
	}
}
