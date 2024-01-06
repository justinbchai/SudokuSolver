package sudoku;

public class SudokuSolverApp {

	public static void main(String[] args) {
		SudokuBoard sudokuBoard = new SudokuBoard("backtrackChallenge.txt");
		System.out.println(sudokuBoard);
		long startTime = System.currentTimeMillis();
		if (SudokuSolver.solveSudoku(sudokuBoard)) {
			long endTime = System.currentTimeMillis(), elapsedTime = endTime - startTime;
			System.out.println("\n" + sudokuBoard);
			System.out.println("Time taken: " + elapsedTime + "ms");
		} else {
			System.out.println("There is no solution");
		}
	}

}
