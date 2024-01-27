package Controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.print.CancelablePrintJob;

import DataBase.DBConnect;
import Models.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class EditCategoryController implements Initializable{

    @FXML
    private Button cancelButton;

    @FXML
    private TextField describeTextField;

    @FXML
    private Button editButton;

    @FXML
    private TextField imageTextField;

    @FXML
    private TextField nameTextFiled;

    @FXML
    private TextField statusTextField;
    
    private Integer categoryId;
    
    public void setData(String name, String des, Integer status, String img, Integer id) {
    	nameTextFiled.setText(name);
    	describeTextField.setText(des);
    	statusTextField.setText(status.toString());
    	imageTextField.setText(img);
    	categoryId = id;
    }
    
    public void edit() {
    	String query = "UPDATE categories SET categoryName = ?, categoryDescribe = ?, categoryStatus= ? , imagePath = ? WHERE categoryId = ?";
    	Connection connection = DBConnect.getConnection();
    	try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, nameTextFiled.getText());
			preparedStatement.setString(2, describeTextField.getText());
			preparedStatement.setInt(3, Integer.parseInt(statusTextField.getText()));
			preparedStatement.setString(4, imageTextField.getText());
			preparedStatement.setInt(5, categoryId);
			preparedStatement.executeUpdate();
			
			// Show success alert
            showAlert(AlertType.INFORMATION, "Success", "Category edited successfully!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// Show error alert
            showAlert(AlertType.ERROR, "Error", "Failed to edit category. Please try again.");
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
		editButton.setOnAction(e->{
			edit();
		});
		cancelButton.setOnAction(e->{
			Stage stage1 = (Stage)cancelButton.getScene().getWindow();
	        Model.getInstance().getViewFactory().closeStage(stage1);
		});
	}

}
