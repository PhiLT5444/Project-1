package Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import DataBase.DBConnect;
import Models.Model;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CartItemController implements Initializable{

    @FXML
    private Button delButton;

    @FXML
    private Text intoMoney;

    @FXML
    private Text number;

    @FXML
    private Text price;

    @FXML
    private Text productName;

    @FXML
    private Text quantities;
    
    Integer idUser;
    Integer idProduct;
    Double quantitiess;
    
    
    public void setData(Integer num, String name, Double price, Double quantities, Integer id, Integer id2) {
    	this.number.setText(num.toString());
    	this.productName.setText(name);
    	this.price.setText(price.toString());
    	this.quantities.setText(quantities.toString());
    	this.quantitiess = quantities;
    	Double toMoney = price * quantities;
        
        // Sử dụng DecimalFormat để làm tròn số và định dạng như tiền tệ
        DecimalFormat df = new DecimalFormat("#,##0.00"); // Định dạng để làm tròn đến hai chữ số thập phân và định dạng tiền tệ
        String formattedMoney = df.format(toMoney); // Chuỗi sau khi làm tròn
        
        // Hiển thị số đã được làm tròn và định dạng như tiền tệ
        this.intoMoney.setText(formattedMoney);
        this.idUser = id;
        this.idProduct = id2;
    }
    
    public void delete() { 	
    	try {
    		String query = "delete from Cart where customerId = ? and productId = ?";
        	Connection connection = DBConnect.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, idUser);
			preparedStatement.setInt(2, idProduct);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("/Views/Cart.fxml"));
    	try {
			Parent root = loader.load();
			CartController controller = loader.getController();
			controller.setData(idUser);
			controller.loadData();
			Stage primStage = new Stage();
			Scene scene = new Scene(root);
			primStage.setScene(scene);
			Stage stage1 = (Stage)delButton.getScene().getWindow();
	        Model.getInstance().getViewFactory().closeStage(stage1);
			primStage.showAndWait();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    } 
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		delButton.setOnAction(e->{
			delete();
		});
	}

}
