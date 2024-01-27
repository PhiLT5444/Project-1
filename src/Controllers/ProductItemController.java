package Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ProductItemController implements Initializable {

    @FXML
    private VBox productButton;

    @FXML
    private ImageView productImage;

    @FXML
    private Label productName;
    
    @FXML
    private Text priceText;
    
    public void setData(String name, String imagePath, Double price) {
    	productName.setText(name);
    	Image image = new Image(imagePath);
    	productImage.setImage(image);
    	priceText.setText(price.toString());
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
	}

}
