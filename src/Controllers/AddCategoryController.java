package Controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import DataBase.DBConnect;
import Models.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AddCategoryController implements Initializable {

    @FXML
    private Button addButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField describeTextField;

    @FXML
    private TextField imageTextField;

    @FXML
    private TextField nameTextFiled;

    @FXML
    private TextField statusTextField;
    
    public void addCategory() {
        String query = "Insert into Categories (categoryName, categoryDescribe, categoryStatus, imagePath) values (?,?,?,?)";
        Connection connection = DBConnect.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, nameTextFiled.getText());
            preparedStatement.setString(2, describeTextField.getText());
            preparedStatement.setInt(3, Integer.parseInt(statusTextField.getText()));
            preparedStatement.setString(4, imageTextField.getText());
            preparedStatement.executeUpdate();

            // Show success alert
            showAlert(AlertType.INFORMATION, "Success", "Category added successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
            // Show error alert
            showAlert(AlertType.ERROR, "Error", "Failed to add category. Please try again.");
        }
    }

    // Helper method to display an alert
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
		addButton.setOnAction(e->{
			addCategory();
		});
		cancelButton.setOnAction(e->{
			Stage stage1 = (Stage)cancelButton.getScene().getWindow();
	        Model.getInstance().getViewFactory().closeStage(stage1);
		});
	}

}
