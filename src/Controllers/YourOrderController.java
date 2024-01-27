package Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import DataBase.DBConnect;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class YourOrderController implements Initializable {

    @FXML
    private VBox layout;

    @FXML
    private HBox head;
    
    private Integer customerId;
    
    public void setData(int customerId) {
    	this.customerId = customerId;
    }
    
    public void loadData() {
    	layout.getChildren().clear();
    	layout.getChildren().add(head);
    	String query = "select * from Orders Where customerId = ?";
    	Connection connection = DBConnect.getConnection();
    	try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, customerId);
			ResultSet resultSet = preparedStatement.executeQuery();
			int count = 1;
			while(resultSet.next()) {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/Views/YourOrderItem.fxml"));
				try {
					HBox hbox = loader.load();
					YourOrderItemController controller = loader.getController();
					controller.setData(customerId, resultSet.getInt("OrderId"));
					controller.setData(count, resultSet.getInt("OrderId"), resultSet.getDouble("orderTotal"), 
									   resultSet.getString("orderStatus"), resultSet.getString("address"));
					layout.getChildren().add(hbox);
					count ++;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
	}

}
