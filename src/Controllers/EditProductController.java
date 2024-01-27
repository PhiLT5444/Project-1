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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditProductController implements Initializable {

    @FXML
    private Button addButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField categoryTextField;

    @FXML
    private MenuButton choiceMenu;

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
    
    private Integer productId;
    
    public void setData(Integer id, String name, String des, Double price, Integer status, String image) {
    	productId = id;
    	this.nameTextFiled.setText(name);
    	this.describeTextField.setText(des);
    	this.priceTextField.setText(price.toString());
    	this.statusTextField.setText(status.toString());
    	this.imageTextField.setText(image);
    }
    
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
    
    public void editProduct() {
    	String query = "UPDATE Products SET productName = ?, productDescribe = ?, productPrice = ?, productImage = ?, productStatus = ? , categoryId = ? "
    			+ "		WHERE productId = ?";
    	Connection connection = DBConnect.getConnection();
    	try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, nameTextFiled.getText());
			preparedStatement.setString(2, describeTextField.getText());
			preparedStatement.setDouble(3, Double.parseDouble(priceTextField.getText()));
			preparedStatement.setString(4, imageTextField.getText());
			preparedStatement.setString(5, statusTextField.getText());
			preparedStatement.setInt(6, Integer.parseInt(choiceMenu.getText()));
			preparedStatement.setInt(7, productId);
			preparedStatement.executeUpdate();
			showAlert("Success", "Product updated successfully!", AlertType.INFORMATION);
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
		setAction();
		addButton.setOnAction(e->{
			editProduct();
		});
		cancelButton.setOnAction(e->{
			Stage stage1 = (Stage)cancelButton.getScene().getWindow();
	        Model.getInstance().getViewFactory().closeStage(stage1);
		});
	}
}
