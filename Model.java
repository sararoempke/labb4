import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.myView;
import model.Model;

/**
 *
 * @author sarar
 */
public class MainAgain extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        // create model, view and controller, and tie them together
        Model model = new Model();
        myView view = new myView(model); // also creates the controller
        
        
        Scene scene = new Scene(view, 700, 600);
        primaryStage.setTitle("Image Processing");
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }
    
    public static void main(String[] args){
        launch(args);
    }
    
}

}

//den här satte jag i default packade, men det spelar säkert ingen roll
