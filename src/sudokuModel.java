/**
 * This is the model class for sudoku that is hidden from the player
 * it generates the board and has the rule for the players
 */
public class sudokuModel implements IModel{
    /**
     * private default board in our tic-tac-toe
     */
    private int[][] board;
    private int move;

    /**
     * default constructor for the model of sudoku
     * setting the default value to be 0 on the board
     *
     */
    public sudokuModel(){
        this.board = new int[][]{
                                    {3, 0, 0, 7, 0, 1, 9, 2, 0},
                                    {7, 0, 6, 0, 0, 9, 8, 0, 0},
                                    {8, 9, 0, 0, 2, 6, 4, 0, 3},
                                    {0, 0, 2, 0, 0, 8, 0, 3, 4},
                                    {0, 7, 0, 3, 1, 0, 0, 0, 0},
                                    {5, 0, 0, 0, 0, 0, 2, 1, 0},
                                    {9, 1, 0, 2, 4, 0, 0, 6, 0},
                                    {0, 8, 0, 0, 0, 0, 3, 4, 0},
                                    {2, 0, 4, 8, 0, 3, 1, 0, 0}
                                };
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
     * @param row the row of the cell
     * @param col the col of the cell
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
            move++;
        }
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
                  for (int numberToTry = 1; numberToTry <= 9; numberToTry++) {
                    // check if the number to try is valid
                    if (isValidPlacement(board, numberToTry, row, col)) {
                      board[row][col] = numberToTry;
                      if (solveBoard(board)) {
                        // recursively trying to solve
                        return true;
                      } else {
                        // we cannot solve the rest of the board with
                        // the current number we are trying to plug in
                        // thus, we clear out and resets that cell,
                        // and it tries again from the for loop
                        // where it's going to try another number
                        board[row][col] = 0;
                      }
                    }
                  }
                  return false; // unable to solve the board
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
     * @param board current board
     * @return true if the player wins
     */
    @Override
    public boolean winningBoard(int [][] board) {
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
     * the board should only consist of numbers 1 - 9, 0 represents empty
     *
     * @return boolean, true iff the game is over, either because the player wins or there are no
     * squares left empty
     */
    @Override
    public boolean isGameOver() {
        for (int row = 0; row < 9; row++){
            for (int col = 0; col < 9; col++){
                if (board[row][col] == 0){
                    return false;
                }
            }
        }
        // total empty slots = 43
        return winningBoard(board) || move == 43;
    }
    /**
     * Determines if the player wins the game
     *
     * @return boolean, true iff the player wins
     * false if it's a tie
     * @throws IllegalStateException if the game is not over
     */
    @Override
    public boolean result(int[][] currBoard) throws IllegalStateException {
        if (!isGameOver()){
            throw new IllegalStateException("Game is not over");
        }
        return winningBoard(currBoard);
    }
}

