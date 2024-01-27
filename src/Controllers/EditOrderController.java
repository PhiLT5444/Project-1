package Controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import DataBase.DBConnect;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class EditOrderController implements Initializable{

    @FXML
    private TextField statusTextFiled;

    @FXML
    private Button updateButton;
    
    private Integer orderId;
    
    public void setData(Integer orderId) {
    	this.orderId = orderId;
    }
    
    public void update() {
    	String query = "UPDATE Orders SET orderStatus = ? WHERE orderId = ?";
    	Connection connection = DBConnect.getConnection();
    	try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, statusTextFiled.getText());
			preparedStatement.setInt(2, orderId);
			preparedStatement.executeUpdate();
			showAlert("Success", "Order updated successfully!", AlertType.INFORMATION);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			showAlert("Warning", "Please fill in all fields!", AlertType.WARNING);
		}
    }
    
    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		updateButton.setOnAction(e->{
			update();
		});
	}

}
