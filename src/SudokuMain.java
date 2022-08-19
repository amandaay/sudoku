/**
 * The main function that runs the sudoku game
 */
public class SudokuMain {
    public static void main(String[] args){
        IModel model = new sudokuModel();
        IView view = new SudokuView(System.out);
        IController controller = new sudokuController(model, System.in, view);
        controller.go();
    }
}
