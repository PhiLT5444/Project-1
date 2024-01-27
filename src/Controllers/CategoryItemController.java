package Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class CategoryItemController implements Initializable {

	@FXML 
	private VBox categoryButton;
	
    @FXML
    private ImageView categoryImage;

    @FXML
    private Label categoryName;

    public void setData(String name, String path) {
    	categoryName.setText(name);
    	Image image = new Image(path);
    	categoryImage.setImage(image);
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
}
