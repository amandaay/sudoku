import java.io.PrintStream;

/**
 * This is the main class of the view which presents the message and the board
 */
public class SudokuView implements IView{

    /**
     * a private member variable to get it out the players
     */
    private PrintStream out;

    /**
     * Constructor for the sudoku view that has a parameter of a generalized print
     * @param out at a higher level of abstract printing
     */
    public SudokuView(PrintStream out){
        this.out = out;
    }

    /**
     * show instruction function shows the initial instruction for user to input their number in the sudoku
     */
    @Override
    public void showInstruction() {
        out.println("""
                    Please enter the number and location you would like to place:
                    e.g. number = 1, row = 0, col = 0, enter: 100
                    """);
    }

    /**
     * Everytime the player finish a move, the computer will ask the player to input a new move
     */
    @Override
    public void showEntryMessage() {
        out.println("Please enter the number and location you would like to place: ");
    }

    /**
     * This displays the current state of the board
     * @param board current status of the board
     */
    @Override
    public void displayBoard(int[][] board) {
        // printing the board
        for (int row = 0; row < 9; row++){
            if (row % 3 == 0 && row != 0){
                System.out.println("----+-----+----");
            }
            for (int col = 0; col < 9; col++){
                if (col % 3 == 0 && col != 0){
                    System.out.print(" | ");
                }
                System.out.print(board[row][col]);
            }
            System.out.println();
        }
    }

    /**
     * this shows if the player successfully solves the game!
     * @param result passes in the results of the player
     */
    @Override
    public void showResultMessage(boolean result) {
        if (result){
            System.out.println("Congratulations, you won!");
        }
        else{
            System.out.println("You messed up!");
        }
    }

    /**
     * this shows if the player put in an invalid move
     */
    @Override
    public void showErrorMessage() {
        out.print("Invalid input. Try again!\n");
    }
}
