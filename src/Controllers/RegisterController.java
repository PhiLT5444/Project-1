package Controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import DataBase.DBConnect;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class RegisterController implements Initializable{

    @FXML
    private TextField emailText;

    @FXML
    private TextField fullNameText;

    @FXML
    private TextField passWordText;

    @FXML
    private TextField phoneNumberText;

    @FXML
    private Button registerButton;
    
    public void register() {
        String email = emailText.getText();
        String phone = phoneNumberText.getText();

        // Kiểm tra trùng lặp số điện thoại và email trong cơ sở dữ liệu
        if (checkDuplicate(email, phone)) {
            showAlert("Thông tin đã tồn tại trong cơ sở dữ liệu!");
        } else {
            // Nếu không trùng lặp, thêm vào cơ sở dữ liệu
            String query = "INSERT INTO customers (customerName, customerEmail, customerPassword, customerPhone) VALUES (?,?,?,?)";
            Connection connection = DBConnect.getConnection();
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, fullNameText.getText());
                preparedStatement.setString(2, email);
                preparedStatement.setString(3, passWordText.getText());
                preparedStatement.setString(4, phone);
                preparedStatement.executeUpdate();
                showAlert("Đăng ký thành công!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Kiểm tra trùng lặp email và số điện thoại trong cơ sở dữ liệu
    private boolean checkDuplicate(String email, String phone) {
        Connection connection = DBConnect.getConnection();
        String query = "SELECT * FROM customers WHERE customerEmail = ? OR customerPhone = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, phone);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next(); // Trả về true nếu đã tồn tại bản ghi
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Hiển thị thông báo
    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		registerButton.setOnAction(e->{
			register();
		});
	}

}
