import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * test for sudoku model
 */
public class sudokuModelTest {

    IModel game1, game2;
    int [][] board = {
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
    // invalid board
    int [][] invalidBoard = {
            {3, 3, 3, 7, 0, 1, 9, 2, 0},
            {7, 0, 6, 0, 0, 9, 8, 0, 0},
            {8, 9, 0, 0, 2, 6, 4, 0, 3},
            {0, 0, 2, 0, 0, 8, 0, 3, 4},
            {0, 7, 0, 3, 1, 0, 0, 0, 0},
            {5, 0, 0, 0, 0, 0, 2, 1, 0},
            {9, 1, 0, 2, 4, 0, 0, 6, 0},
            {0, 8, 0, 0, 0, 0, 3, 4, 0},
            {2, 0, 4, 8, 0, 3, 1, 0, 0}
    };
    /**
     * setting up the new sudoku model game
     */
    @Before
    public void setUp() {
        game1 = new sudokuModel();
        game2 = new sudokuModel();
    }

    /**
     * testing if it returns the copy of the board
     */
    @Test
    public void copyBoardTest() {
        assertArrayEquals(board, game1.copyBoard());
    }

    /**
     * testing if 1 - 9 is able to be placed at the right cell
     */
    @Test
    public void moveTest() {
        game1.move(1, 2, 2);
        assertTrue(game1.isValidPlacement(board, 1, 2, 2));
    }

    /**
     * failed test for move for index out of bounds
     */
    @Test (expected = IndexOutOfBoundsException.class)
    public void moveTestIndexOutBound(){
        game1.move(1, 2, 100);
    }

    /**
     * failed test for move for illegal state exception
     * trying to move when all the cells are occupied, it's game over
     */
    @Test (expected = IllegalStateException.class)
    public void moveTestFailedGameOver(){
        // moving to complete the board
        game1.move(1, 2, 2);
        game1.move(4, 0, 1);
        game1.move(5, 0, 2);
        game1.move(2, 1, 1);
        game1.move(6, 0, 8);
        game1.move(8, 0, 4);
        game1.move(4, 1, 3);
        game1.move(3, 1, 4);
        game1.move(5, 2, 3);
        game1.move(7, 2, 7);
        game1.move(5, 1, 7);
        game1.move(1, 1, 8);
        game1.move(9, 8, 7);
        game1.move(8, 4, 7);
        game1.move(2, 7, 8);
        game1.move(8, 6, 8);
        game1.move(5, 8, 1);
        game1.move(5, 6, 6);
        game1.move(7, 8, 8);
        game1.move(6, 8, 4);
        game1.move(3, 5, 1);
        game1.move(6, 3, 1);
        game1.move(3, 6, 2);
        game1.move(7, 6, 5);
        game1.move(7, 7, 2);
        game1.move(6, 7, 0);
        game1.move(4, 4, 0);
        game1.move(1, 3, 0);
        game1.move(9, 4, 2);
        game1.move(8, 5, 2);
        game1.move(1, 7, 3);
        game1.move(9, 7, 4);
        game1.move(5, 7, 5);
        game1.move(4, 5, 5);
        game1.move(2, 4, 5);
        game1.move(7, 3, 6);
        game1.move(6, 4, 6);
        game1.move(5, 4, 8);
        game1.move(9, 5, 8);
        game1.move(9, 3, 3);
        game1.move(6, 5, 3);
        game1.move(7, 5, 4);
        game1.move(5, 3, 4);

        // trying to move again when the player already wins the game
        game1.move(2, 8, 8);
    }

    /**
     * testing for cell occupied illegal state exception
     */
    @Test (expected = IllegalStateException.class)
    public void moveTestFailedOccupied(){
        game1.move(1, 0, 0);
    }

    /**
     * testing if the check of if number in row is valid
     */
    @Test
    public void isNumberInRowTest() {
        assertTrue(game1.isNumberInRow(board, 3, 0));
        assertFalse(game1.isNumberInRow(board, 1, 1));
    }

    /**
     * testing if the check of number in col is valid
     */
    @Test
    public void isNumberInColTest() {
        assertTrue(game1.isNumberInCol(board, 3, 0));
        assertFalse(game1.isNumberInCol(board, 6, 0));
    }

    /**
     * testing if the check of number in box is valid
     */
    @Test
    public void isNumberInBoxTest() {
        assertTrue(game1.isNumberInBox(board, 3, 0, 0));
        assertFalse(game1.isNumberInBox(board, 6, 0, 8));
    }

    /**
     * testing if the valid placement is working with all row, col, and box
     */
    @Test
    public void isValidPlacementTest() {
        assertTrue(game1.isValidPlacement(board, 1, 2, 2));
        assertFalse(game1.isValidPlacement(board, 3, 0, 1));
    }


    /**
     * checks if the player is able to solve the board
     */
    @Test
    public void solveBoardTest() {
        // moving to complete the board
        game1.move(1, 2, 2);
        game1.move(4, 0, 1);
        game1.move(5, 0, 2);
        game1.move(2, 1, 1);
        game1.move(6, 0, 8);
        game1.move(8, 0, 4);
        game1.move(4, 1, 3);
        game1.move(3, 1, 4);
        game1.move(5, 2, 3);
        game1.move(7, 2, 7);
        game1.move(5, 1, 7);
        game1.move(1, 1, 8);
        game1.move(9, 8, 7);
        game1.move(8, 4, 7);
        game1.move(2, 7, 8);
        game1.move(8, 6, 8);
        game1.move(5, 8, 1);
        game1.move(5, 6, 6);
        game1.move(7, 8, 8);
        game1.move(6, 8, 4);
        game1.move(3, 5, 1);
        game1.move(6, 3, 1);
        game1.move(3, 6, 2);
        game1.move(7, 6, 5);
        game1.move(7, 7, 2);
        game1.move(6, 7, 0);
        game1.move(4, 4, 0);
        game1.move(1, 3, 0);
        game1.move(9, 4, 2);
        game1.move(8, 5, 2);
        game1.move(1, 7, 3);
        game1.move(9, 7, 4);
        game1.move(5, 7, 5);
        game1.move(4, 5, 5);
        game1.move(2, 4, 5);
        game1.move(7, 3, 6);
        game1.move(6, 4, 6);
        game1.move(5, 4, 8);
        game1.move(9, 5, 8);
        game1.move(9, 3, 3);
        game1.move(6, 5, 3);
        game1.move(7, 5, 4);
        game1.move(5, 3, 4);
        assertTrue(game1.solveBoard(board));

        // trying to solve an invalid board
        assertFalse(game1.solveBoard(invalidBoard));
    }

    /**
     * testing if game is over
     */
    @Test
    public void isGameOverTest() {
        assertFalse(game1.isGameOver());
        // moving to complete the board
        game1.move(1, 2, 2);
        game1.move(4, 0, 1);
        game1.move(5, 0, 2);
        game1.move(2, 1, 1);
        game1.move(6, 0, 8);
        game1.move(8, 0, 4);
        game1.move(4, 1, 3);
        game1.move(3, 1, 4);
        game1.move(5, 2, 3);
        game1.move(7, 2, 7);
        game1.move(5, 1, 7);
        game1.move(1, 1, 8);
        game1.move(9, 8, 7);
        game1.move(8, 4, 7);
        game1.move(2, 7, 8);
        game1.move(8, 6, 8);
        game1.move(5, 8, 1);
        game1.move(5, 6, 6);
        game1.move(7, 8, 8);
        game1.move(6, 8, 4);
        game1.move(3, 5, 1);
        game1.move(6, 3, 1);
        game1.move(3, 6, 2);
        game1.move(7, 6, 5);
        game1.move(7, 7, 2);
        game1.move(6, 7, 0);
        game1.move(4, 4, 0);
        game1.move(1, 3, 0);
        game1.move(9, 4, 2);
        game1.move(8, 5, 2);
        game1.move(1, 7, 3);
        game1.move(9, 7, 4);
        game1.move(5, 7, 5);
        game1.move(4, 5, 5);
        game1.move(2, 4, 5);
        game1.move(7, 3, 6);
        game1.move(6, 4, 6);
        game1.move(5, 4, 8);
        game1.move(9, 5, 8);
        game1.move(9, 3, 3);
        game1.move(6, 5, 3);
        game1.move(7, 5, 4);
        game1.move(5, 3, 4);
        assertTrue(game1.isGameOver());
    }

    @Test
    public void getMarkAtTest(){
        assertEquals(0, game1.getMarkAt(0, 8), 0.01);
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void getMarkAtFailed(){
        assertEquals(-1, game1.getMarkAt(-100, 200));
    }

    /**
     * testing if the player wins the game, aka the player was able to solve the board
     *
     */
    @Test
    public void resultTest() {
        // moving to complete the board
        game1.move(1, 2, 2);
        game1.move(4, 0, 1);
        game1.move(5, 0, 2);
        game1.move(2, 1, 1);
        game1.move(6, 0, 8);
        game1.move(8, 0, 4);
        game1.move(4, 1, 3);
        game1.move(3, 1, 4);
        game1.move(5, 2, 3);
        game1.move(7, 2, 7);
        game1.move(5, 1, 7);
        game1.move(1, 1, 8);
        game1.move(9, 8, 7);
        game1.move(8, 4, 7);
        game1.move(2, 7, 8);
        game1.move(8, 6, 8);
        game1.move(5, 8, 1);
        game1.move(5, 6, 6);
        game1.move(7, 8, 8);
        game1.move(6, 8, 4);
        game1.move(3, 5, 1);
        game1.move(6, 3, 1);
        game1.move(3, 6, 2);
        game1.move(7, 6, 5);
        game1.move(7, 7, 2);
        game1.move(6, 7, 0);
        game1.move(4, 4, 0);
        game1.move(1, 3, 0);
        game1.move(9, 4, 2);
        game1.move(8, 5, 2);
        game1.move(1, 7, 3);
        game1.move(9, 7, 4);
        game1.move(5, 7, 5);
        game1.move(4, 5, 5);
        game1.move(2, 4, 5);
        game1.move(7, 3, 6);
        game1.move(6, 4, 6);
        game1.move(5, 4, 8);
        game1.move(9, 5, 8);
        game1.move(9, 3, 3);
        game1.move(6, 5, 3);
        game1.move(7, 5, 4);
        game1.move(5, 3, 4);
        assertTrue(game1.result(board));
    }

    /**
     * failed test because the game is not over and user wants to obtain the result
     */
    @Test (expected = IllegalStateException.class)
    public void restTestFailed(){
        assertTrue(game1.result(board));
    }
}