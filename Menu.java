package view;

import controller.Controller;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Model;
import controller.Controller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;

public class myView extends BorderPane {

    private final Model model;
    public static BorderPane pane;
    public ImageView myImageView = null;
    public boolean showSlider = false;
    public static int windowValue;
    public static int levelValue;

    
    private final Alert alert = new Alert(Alert.AlertType.INFORMATION);

    public myView(Model model) {
        this.model = model;
        
        
        pane = new BorderPane();
        myImageView = new ImageView();
        Controller controller = new Controller(model, this);
        MenuBar menuBar = createMenu(controller);
	
        this.setTop(menuBar);
        this.setRight(myImageView);
        
        if(showSlider == true){
            VBox sliders = contrastView();
            this.setLeft(sliders);
        }
    }    
    
    private MenuBar createMenu(Controller controller) {//arg controller??
		MenuItem exitItem = theExit();
                MenuItem savePic = theSave(controller);
                MenuItem openPic = toOpenImage();
		
		Menu fileMenu = new Menu("File");
		fileMenu.getItems().addAll(exitItem, savePic, openPic);
                
                MenuItem blurPic = blurImage(controller);
                MenuItem invertColor = toInvertColor(controller);
                MenuItem histogram = toOpenHistogram(controller);
                MenuItem editContrast = toEditContrast(controller);
                Menu processMenu = new Menu("Process");
                processMenu.getItems().addAll(histogram, blurPic, invertColor, editContrast);
                
                if(myImageView == null){
                    processMenu.hide();
                }
                else 
                    processMenu.show();
		
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(fileMenu, processMenu);
		
		return menuBar;
    }
    
    public VBox contrastView(){
        VBox sliders = new VBox(10);
            Label windowlb = new Label("Window:");
            Label levellb = new Label("Level:");
            Slider Window = new Slider(0, 255, 0);
            Window.setShowTickLabels(true);
            Window.setShowTickMarks(true);
            Slider Level = new Slider(0, 255, 0);
            Level.setShowTickLabels(true);
            Level.setShowTickMarks(true);
            //Button btEdit = new Button("Edit");
            Button btDone = new Button("Apply");
            
            Window.valueProperty().addListener(new ChangeListener<Number>(){
                @Override
                public void changed(ObservableValue <? extends Number >
                        observable, Number oldValue, Number newValue){
                    windowValue = (int) newValue;
                }
            });
            
            Level.valueProperty().addListener(new ChangeListener<Number>(){
                @Override
                public void changed(ObservableValue <? extends Number >
                        observable, Number oldValue, Number newValue){
                    levelValue = (int) newValue;
                }
            });
            
            btDone.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    showSlider = false;
                }
            });
            
            sliders.getChildren().addAll(windowlb, Window, levellb, Level, btDone);
            
            return sliders;
        
    }
    
    // ska fråga om du inte vill spara bild först
    public MenuItem theExit(){
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Platform.exit();
			}
		});
        
        return exitItem;
    }
    
    public MenuItem theSave(Controller controller){
        MenuItem savePic = new MenuItem("Save Image");
        savePic.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Save the image");
                controller.handleSavePic(myImageView.getImage());
            }
        });
        
        return savePic;
    }
    
    public MenuItem toOpenImage(){
        MenuItem openPic = new MenuItem("Open Image");
        openPic.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                
                System.out.println("Open Image");
                FileChooser fileChoice = new FileChooser();
        
                File file = fileChoice.showOpenDialog(null);
        
                try{
                 BufferedImage bufferedImage = ImageIO.read(file);
                 Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                 myImageView.setImage(image);
                }catch (IOException ex) {
                    Logger.getLogger(myView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        return openPic;
    }
    
    public MenuItem toInvertColor(Controller controller){
        MenuItem invertColor = new MenuItem("Invert Color");
        invertColor.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Invert Color");
                Image newImage = controller.handleInvertColor(myImageView.getImage()); 
                myImageView.setImage(newImage);
            }
        });
        return invertColor;
    }
    
    public MenuItem blurImage(Controller controller){
        MenuItem blurPic = new MenuItem("Blur");
        blurPic.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Da blurr");
                Image newImage = controller.handleBlurPic(myImageView.getImage()); 
                myImageView.setImage(newImage);
            }
        });
        return blurPic;
    }
    
    public MenuItem toOpenHistogram(Controller controller){
        MenuItem histogram = new MenuItem("Show Histogram");
        histogram.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //showSlider = true;
                System.out.println("Show Histogram");
                //showSlider = false;
            }
        });
        return histogram;
    }
    
    public MenuItem toEditContrast(Controller controller){
        MenuItem editContrast = new MenuItem("Edit Contrast");
        editContrast.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                showSlider = true;
                System.out.println("Editing contrast");
                Image newImage = controller.handleContrast(myImageView.getImage()); 
                myImageView.setImage(newImage);
            }
        });
        return editContrast;
    }

}