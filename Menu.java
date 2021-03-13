package view;

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
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;

public class myView extends BorderPane {

    private final Model model;
    private final BorderPane pane;
    public ImageView myImageView;
    //private final Controller controller;

    
    private final Alert alert = new Alert(Alert.AlertType.INFORMATION);

    public myView(Model model) {
        this.model = model;
        //controller = new Controller(model, this);
        
        pane = new BorderPane();
        myImageView = new ImageView();
      
        MenuBar menuBar = createMenu();//arg controller??
	
        this.setTop(menuBar);
        this.setRight(myImageView);

        //updateFromModel();
    }    
    
    private MenuBar createMenu() {//arg controller??
		MenuItem exitItem = theExit();
                MenuItem savePic = theSave();
                MenuItem openPic = toOpenImage();
		
		Menu fileMenu = new Menu("File");
		fileMenu.getItems().addAll(exitItem, savePic, openPic);
                
                MenuItem blurPic = blurImage();
                MenuItem invertColor = toInvertColor();
                MenuItem histogram = toOpenHistogram();
                Menu processMenu = new Menu("Process");
                processMenu.getItems().addAll(histogram, blurPic, invertColor);
                
                if(myImageView == null){
                    processMenu.hide();
                }
                else 
                    processMenu.show();
		
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(fileMenu, processMenu);
		
		return menuBar;
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
    
    public MenuItem theSave(){
        MenuItem savePic = new MenuItem("Save Image");
        savePic.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Save the image");
                Controller.handleSavePic(myImageView.getImage());
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
    
    public MenuItem toInvertColor(){
        MenuItem invertColor = new MenuItem("Invert Color");
        invertColor.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Invert Color");
                Image newImage = Controller.handleInvertColor(myImageView.getImage()); 
                myImageView.setImage(newImage);
            }
        });
        return invertColor;
    }
    
    public MenuItem blurImage(){
        MenuItem blurPic = new MenuItem("Blur");
        blurPic.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Da blurr");
                Image newImage = Controller.handleBlurPic(myImageView.getImage()); 
                myImageView.setImage(newImage);
            }
        });
        return blurPic;
    }
    
    public MenuItem toOpenHistogram(){
        MenuItem histogram = new MenuItem("Show Histogram");
        histogram.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Show Histogram");
            }
        });
        return histogram;
    }

}