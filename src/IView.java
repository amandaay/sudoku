/**
 * This is the interface for the view of the sudoku game
 */
public interface IView {
    /**
     * displays input instructions
     */
    void showInstruction();

    /**
     * display at every entry point for the player
     */
    void showEntryMessage();

    /**
     * displays the board for every time the user inputs something
     * @param board current status of the board
     */
    void displayBoard(int[][] board);


    /**
     * this shows if the player successfully solves the game!
     * @param result passes in the results of the player
     */
    void showResultMessage(boolean result);

    /**
     * this shows if the player put in an invalid move
     */
    void showErrorMessage();
}
