import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

/**
 * This is the controller class for the sudoku game
 */
public class sudokuController implements IController{
    /**
     * components that is private for the controller
     */
    private Scanner in;
    private IView view;
    private IModel model;

    /**
     * The constructor of the controller for sudoku that passes in the in, view, and model for the class
     * @param model passes in the model to interact with model
     * @param in an input for players to enter
     * @param view interacts with the view portion
     */
    public sudokuController(IModel model, InputStream in, IView view) {
        this.model = model;
        this.view = view;
        this.in = new Scanner(in);
    }

    /**
     * The go function runs the game
     */
    @Override
    public void go(){
        view.showInstruction();
        while (!model.isGameOver()){
            view.displayBoard(this.model.copyBoard());
            view.showEntryMessage();

            String position = in.nextLine();

            try{
                // making sure the input is a number and within 3 digits, so it doesn't come up as e.g. 9999
                if (position.length() == 3) {
                    // parsing the first three inputs
                  int number = Integer.parseInt(String.valueOf(position.charAt(0)));
                  int row = Integer.parseInt(String.valueOf(position.charAt(1)));
                  int col = Integer.parseInt(String.valueOf(position.charAt(2)));
                  // testing if it's a valid placement before placing it on the board
                    if (this.model.copyBoard()[row][col] == 0){
                        model.move(number, row, col);
                        this.model.copyBoard()[row][col] = number;
                    }
                    else {
                        view.showErrorMessage();
                    }
                } else {
                    view.showErrorMessage();
                }
            } catch(NumberFormatException e)
            {
                view.showErrorMessage();
            }
        }
        view.displayBoard(this.model.copyBoard());
        view.showResultMessage(this.model.result(this.model.copyBoard()));
    }
}
