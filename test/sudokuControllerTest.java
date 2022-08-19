import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

/**
 * Mock model to test the controller with a different board
 * duplicate functions are necessary as helper functions
 *
 */
class MockModel implements IModel{

    private StringBuilder log;
    private int[][] board;
    private boolean moved;

    /**
     * constructor for the mock model for testing purpose
     * it's a different board compared to the default board
     * for checking purpose, sets the moved to false, and turns to true to end the testing
     */
     public MockModel(){
        log = new StringBuilder();
        this.board = new int [][] { {0, 0, 0, 2, 6, 0, 7, 0, 1},
                                    {6, 8, 0, 0, 7, 0, 0, 9, 0},
                                    {1, 9, 0, 0, 0, 4, 5, 0, 0},
                                    {8, 2, 0, 1, 0, 0, 0, 4, 0},
                                    {0, 0, 4, 6, 0, 2, 9, 0, 0},
                                    {0, 5, 0, 0, 0, 3, 0, 2, 8},
                                    {0, 0, 9, 3, 0, 0, 0, 7, 4},
                                    {0, 4, 0, 0, 5, 0, 0, 3, 6},
                                    {7, 0, 3, 0, 1, 8, 0, 0, 0}
                                };
         this.moved = false;
    }

    /**
     * getting the copy of the sudoku grid
     */
    @Override
    public int[][] copyBoard() {
        return board.clone();
    }

    /**
     * Places 1 - 9 in the specified cell
     *
     * @param number the player wants to plug in
     * @param row    the row of the cell
     * @param col    the col of the cell
     * @throws IllegalStateException     if the game is over, or if there is already a mark in the cell
     * @throws IndexOutOfBoundsException if the cell is out of bounds
     *                                   Legal values for col and row would are 0 - 8
     */
    @Override
    public void move(int number, int row, int col) throws IllegalStateException, IndexOutOfBoundsException {
        if (row < 0 || row > 8 || col < 0 || col > 8){
            throw new IndexOutOfBoundsException("cell is out of bound");
        }
        else if (isGameOver()){
            throw new IllegalStateException("It's game over");
        }
        else if (board[row][col] != 0){
            throw new IllegalStateException("cell is occupied!");
        }
        else{
            board[row][col] = number;
        }
        log.append("Input: number ").append(number).append(" row: ").append(row).append(" col: ").append(col);
        moved = true;
    }

    /**
     * checks if the number exist in the row
     *
     * @param board  current board
     * @param number number plugging in the board
     * @param row    the row the player picks
     * @return true if the number is already in the row
     */
    @Override
    public boolean isNumberInRow(int[][] board, int number, int row) {
        for (int i = 0; i < 9; i++){
            if (board[row][i] == number){
                return true;
            }
        }
        return false;
    }

    /**
     * checks if the number exist in the col
     *
     * @param board  current board
     * @param number number plugging in the board
     * @param col    the col the player picks
     * @return true if the number is already in the col
     */
    @Override
    public boolean isNumberInCol(int[][] board, int number, int col) {
        for (int i = 0; i < 9; i++){
            if (board[i][col] == number){
                return true;
            }
        }
        return false;
    }

    /**
     * checks if the number exist in the box
     *
     * @param board  current board
     * @param number number plugging in the board
     * @param row    the row the number is plugging in
     * @param col    the col the number is plugging in
     * @return true if the number is already in the col
     */
    @Override
    public boolean isNumberInBox(int[][] board, int number, int row, int col) {
        int localBoxRow = row - row % 3;
        int localBoxCol = col - col % 3;

        for (int i = localBoxRow; i < localBoxRow + 3; i++){
            for (int j = localBoxCol; j < localBoxCol + 3; j++){
                if (board[i][j] == number){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * check if the player places their number in the valid place
     *
     * @param board  current board
     * @param number number the player plugs in
     * @param row    row the player puts
     * @param col    col the player puts
     * @return true if it's valid
     */
    @Override
    public boolean isValidPlacement(int[][] board, int number, int row, int col) {
        return !isNumberInRow(board, number, row) &&
                !isNumberInCol(board, number, col) &&
                !isNumberInBox(board, number, row, col);
    }

    /**
     * check if the player solved the board
     *
     * @param board the current board
     * @return true if the player successfully solved the board
     */
    @Override
    public boolean solveBoard(int[][] board) {
        for (int row = 0; row < 9; row++){
            for (int col = 0; col < 9; col++){
                if (board[row][col] == 0){
                    // start trying all the numbers 1 - 9 to see if it's valid
                    for (int numberToTry = 1; numberToTry <= 9; numberToTry++){
                        board[row][col] = numberToTry;
                        // check if the number to try is valid
                        if (isValidPlacement(board, numberToTry, row, col)){
                            // recursively trying to solve
                            if (solveBoard(board)){
                                return true;
                            }
                            else {
                                // we cannot solve the rest of the board with
                                // the current number we are trying to plug in
                                // thus, we clear out and resets that cell,
                                // and it tries again from the for loop
                                // where it's going to try another number
                                board[row][col] = 0;
                            }
                            return false; // unable to solve the board
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Returns the Player whose mark is in the cell at the given coordinates
     * 0 if the cell is empty.
     *
     * @param row the row of the cell
     * @param col the column of the cell
     * @return a {@code Player} or {@code null}
     * @throws IndexOutOfBoundsException if the cell is out of bounds.
     */
    @Override
    public int getMarkAt(int row, int col) {
        if (row < 0 || row > 8 || col < 0 || col > 8){
            throw new IndexOutOfBoundsException("cell is out of bound");
        }
        if (board[row][col] == 0){
            return 0;
        }
        return board[row][col];
    }

    /**
     * helps to check if the player wins, the winning board matches with the player's move
     *
     * @param board current board at the state of the game
     * @return true if the player wins
     */
    @Override
    public boolean winningBoard(int[][] board) {
        return (1 == getMarkAt(2, 2) &&
                4 == getMarkAt(0, 1) &&
                5 == getMarkAt(0, 2) &&
                2 == getMarkAt(1, 1) &&
                6 == getMarkAt(0, 8) &&
                8 == getMarkAt(0, 4) &&
                4 == getMarkAt(1, 3) &&
                3 == getMarkAt(1, 4) &&
                5 == getMarkAt(2, 3) &&
                7 == getMarkAt(2, 7) &&
                5 == getMarkAt(1, 7) &&
                1 == getMarkAt(1, 8) &&
                9 == getMarkAt(8, 7) &&
                8 == getMarkAt(4, 7) &&
                2 == getMarkAt(7, 8) &&
                8 == getMarkAt(6, 8) &&
                5 == getMarkAt(8, 1) &&
                5 == getMarkAt(6, 6) &&
                7 == getMarkAt(8, 8) &&
                6 == getMarkAt(8, 4) &&
                3 == getMarkAt(5, 1) &&
                6 == getMarkAt(3, 1) &&
                3 == getMarkAt(6, 2) &&
                7 == getMarkAt(6, 5) &&
                7 == getMarkAt(7, 2) &&
                6 == getMarkAt(7, 0) &&
                4 == getMarkAt(4, 0) &&
                1 == getMarkAt(3, 0) &&
                9 == getMarkAt(4, 2) &&
                8 == getMarkAt(5, 2) &&
                1 == getMarkAt(7, 3) &&
                9 == getMarkAt(7, 4) &&
                5 == getMarkAt(7, 5) &&
                4 == getMarkAt(5, 5) &&
                2 == getMarkAt(4, 5) &&
                7 == getMarkAt(3, 6) &&
                6 == getMarkAt(4, 6) &&
                5 == getMarkAt(4, 8) &&
                9 == getMarkAt(5, 8) &&
                9 == getMarkAt(3, 3) &&
                6 == getMarkAt(5, 3) &&
                7 == getMarkAt(5, 4) &&
                5 == getMarkAt(3, 4));
    }

    /**
     * Determines if the game is over
     *
     * @return boolean, true iff the game is over, either because the player wins or there are no
     * squares left empty
     */
    @Override
    public boolean isGameOver() {
        return moved;
    }

    /**
     * Determines if the player wins the game
     *
     * @return boolean, true iff the player wins
     * false if it's a tie
     * @throws IllegalStateException if the game is not over
     */
    @Override
    public boolean result(int [][] currBoard) throws IllegalStateException {
        if (!isGameOver()){
            throw new IllegalStateException("Game is not over");
        }
        return winningBoard(currBoard);
    }

    /**
     * converting the get log to toString
     * @return a to string of log
     */
    public String getLog(){
        return log.toString();
    }
}

/**
 * testing the controller for sudoku using the above mock model
 */
public class sudokuControllerTest {
    /**
     * testing the controller with a sudoku mock from the above
     */
    @Test
    public void controllerTest() {
        MockModel model = new MockModel();
        String input = "301";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outStream);
        IView view = new SudokuView(out);

        IController controller = new sudokuController(model, in, view);
        controller.go();
        // assert on what the controller wrote with the expected answer
        String actualOutput = outStream.toString();
        // verify that the mock model got 301
        assertEquals("Input: number 3 row: 0 col: 1", model.getLog());
    }

    /**
     * testing if def would catch numberformatexception error then another correct input
     */
    @Test
    public void controllerTestNumberFormatException(){
        MockModel model = new MockModel();
        String input = "def\n123";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outStream);
        IView view = new SudokuView(out);

        IController controller = new sudokuController(model, in, view);
        controller.go();
        // assert on what the controller wrote with the expected answer
        String actualOutput = outStream.toString();
        assertEquals("Input: number 1 row: 2 col: 3", model.getLog());
        assertEquals(1, model.getMarkAt(2, 3), 0.01);
    }

    /**
     * Testing the wrong Length e.g. exceeding the length of three
     */
    @Test
    public void controllerTestWrongLength(){
        MockModel model = new MockModel();
        String input = "12345\n123";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outStream);
        IView view = new SudokuView(out);

        IController controller = new sudokuController(model, in, view);
        controller.go();
        // assert on what the controller wrote with the expected answer
        String actualOutput = outStream.toString();
        assertEquals("Input: number 1 row: 2 col: 3", model.getLog());
        assertEquals(1, model.getMarkAt(2, 3), 0.01);
    }
}