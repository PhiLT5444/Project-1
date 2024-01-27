package Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class YourOrderItemController implements Initializable{

    @FXML
    private Label addressLabel;

    @FXML
    private Label idLabel;

    @FXML
    private Label numberLabel;

    @FXML
    private Label statusLabel;

    @FXML
    private Label totalLable;
    
    @FXML 
    private Button detailButton;
    private Integer orderID;
    private Integer idUser;
    public void setData(Integer id, Integer oid) {
    	this.idUser = id;
    	this.orderID = oid;
    }
    
    public void setData(Integer num, Integer Id, Double total, String status, String address) {
    	this.addressLabel.setText(address);
    	this.idLabel.setText(Id.toString());
    	this.numberLabel.setText(num.toString());
    	this.totalLable.setText(total.toString());
    	this.statusLabel.setText(status);
    }
    
    public void getDetail() {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("/Views/CartDetail.fxml"));
    	try {
			Parent root = loader.load();
			CartDetailController controller = loader.getController();
			controller.loadData(idUser, orderID);
			Stage primaryStage= new Stage();
			Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Cart Detail");
            primaryStage.show();	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		detailButton.setOnAction(e->{
			getDetail();
		});
	}

}
