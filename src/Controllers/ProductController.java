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
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ProductController implements Initializable{

    @FXML
    private Label ProductName;

    @FXML
    private Text myAccountText;

    @FXML
    private GridPane productGridPane;
    
    public void loadData() {
    	String query = "Select * from Products";
    	Connection connection = DBConnect.getConnection();
    	int column = 0;
		int row = 1;
    	try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/Views/ProductItem.fxml"));
				try {
					VBox vbox = loader.load();
					CategoryItemController controller = loader.getController();
					controller.setData(resultSet.getString("productName"), resultSet.getString("productImage"));
					if (column == 3) {
	                    column = 0;
	                    row++;
	                }
					productGridPane.add(vbox, column++, row); //(child,column,row)
	                //set grid width
					productGridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
					productGridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
					productGridPane.setMaxWidth(Region.USE_PREF_SIZE);
	                //set grid height
					productGridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
					productGridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
					productGridPane.setMaxHeight(Region.USE_PREF_SIZE);
	                GridPane.setMargin(vbox, new Insets(10));
					
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
