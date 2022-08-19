/**
 * This is the interface of my Sudoku Model
 */
public interface IModel {
    /**
     * getting the copy of the sudoku grid
     */
    int[][] copyBoard();

    /**
     * Places 1 - 9 in the specified cell
     *
     * @param number the player wants to plug in
     * @param row the row of the cell
     * @param col the col of the cell
     * @throws IllegalStateException if the game is over, or if there is already a mark in the cell
     * @throws IndexOutOfBoundsException if the cell is out of bounds
     * Legal values for col and row would are 0 - 8
     */
    void move(int number, int row, int col) throws IllegalStateException, IndexOutOfBoundsException;

    /**
     * checks if the number exist in the row
     * @param board current board
     * @param number number plugging in the board
     * @param row the row the player picks
     * @return true if the number is already in the row
     */
    boolean isNumberInRow(int[][] board, int number, int row);

    /**
     * checks if the number exist in the col
     * @param board current board
     * @param number number plugging in the board
     * @param col the col the player picks
     * @return true if the number is already in the col
     */
    boolean isNumberInCol(int[][] board, int number, int col);

    /**
     * checks if the number exist in the box
     * @param board current board
     * @param number number plugging in the board
     * @param row the row the number is plugging in
     * @param col the col the number is plugging in
     * @return true if the number is already in the col
     */
    boolean isNumberInBox(int[][] board, int number, int row, int col);

    /**
     * check if the player places their number in the valid place
     * @param board current board
     * @param number number the player plugs in
     * @param row row the player puts
     * @param col col the player puts
     *
     * @return true if it's valid
     */
    boolean isValidPlacement(int[][] board, int number, int row, int col);

    /**
     * check if the player solved the board
     * @param board the current board
     * @return true if the player successfully solved the board
     */
    boolean solveBoard(int[][] board);

    /**
     * Returns the Player whose mark is in the cell at the given coordinates
     * 0 if the cell is empty.
     *
     * @param col the column of the cell
     * @param row the row of the cell
     * @return a {@code Player} or {@code null}
     * @throws IndexOutOfBoundsException if the cell is out of bounds.
     */
    int getMarkAt(int row, int col);

    /**
     * helps to check if the player wins, the winning board matches with the player's move
     * @param board current board at the state of the game
     * @return true if the player wins
     */
    boolean winningBoard(int [][] board);

    /**
     * Determines if the game is over
     * @return boolean, true iff the game is over, either because the player wins or there are no
     * squares left empty
     */
    boolean isGameOver();

    /**
     * Determines if the player wins the game
     * @return boolean, true iff the player wins
     * false if it's a tie
     * @throws IllegalStateException if the game is not over
     */
    boolean result(int[][] currBoard) throws IllegalStateException;
}
