package Controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import DataBase.DBConnect;
import Models.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AddProductController implements Initializable {

    @FXML
    private Button addButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField categoryTextField;

    @FXML
    private TextField describeTextField;

    @FXML
    private TextField imageTextField;

    @FXML
    private TextField nameTextFiled;

    @FXML
    private TextField priceTextField;

    @FXML
    private TextField statusTextField;
    
    @FXML
    private MenuButton choiceMenu;
    
    public void setAction() {
    	String query = "select * from Categories";
    	Connection connection = DBConnect.getConnection();
    	try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Integer id = resultSet.getInt("CategoryId");
				String name = resultSet.getString("categoryName");
				MenuItem menu = new MenuItem();
				menu.setText(id.toString() + " | " + name);
				menu.setOnAction(e->{
					categoryTextField.setText("ID: " + id.toString() + ", Name: " + name);
					choiceMenu.setText(id.toString());
				});
				choiceMenu.getItems().add(menu);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void add() {
    	String query = "Insert into products(productName, categoryId, productDescribe, productPrice, productImage, productStatus) values(?,?,?,?,?,?)";
    	Connection connection = DBConnect.getConnection();
    	try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, nameTextFiled.getText());
			preparedStatement.setInt(2, Integer.parseInt(choiceMenu.getText()));
			preparedStatement.setString(3, describeTextField.getText());
			preparedStatement.setDouble(4, Double.parseDouble(priceTextField.getText()));
			preparedStatement.setString(5, imageTextField.getText());
			preparedStatement.setInt(6, Integer.parseInt(statusTextField.getText()));
			preparedStatement.executeUpdate();
			showAlert(AlertType.INFORMATION, "Success", "Product added successfully!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			showAlert(AlertType.ERROR, "Error", "Failed to add product. Please try again.");
			e.printStackTrace();
		}
    }
    
    private void showAlert(AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		setAction();
		
		addButton.setOnAction(e->{
			add();
		});
		cancelButton.setOnAction(e->{
			Stage stage1 = (Stage)cancelButton.getScene().getWindow();
	        Model.getInstance().getViewFactory().closeStage(stage1);
		});
		
	}
}
