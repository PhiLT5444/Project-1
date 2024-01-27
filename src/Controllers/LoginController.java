package Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import DataBase.DBConnect;
import Models.Model;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginController implements Initializable{

    @FXML
    private Button registerButton;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordText;

    @FXML
    private TextField userNameText;

    @FXML
    private Text warningText;
    
    @FXML
    private Button manageButton;
    
    public void login() {
    	String query = "Select * from customers";
    	Connection connection = DBConnect.getConnection();
    	String name = new String();
    	int id = 0;
    	try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			boolean check = false;
			while(resultSet.next()) {
				if((userNameText.getText().equals(resultSet.getString("customerEmail")) || userNameText.getText().equals(resultSet.getString("customerPhone")))&& 
				   passwordText.getText().equals(resultSet.getString("customerPassword"))) {
					check = true;
					name = resultSet.getString("customerName");
					id = resultSet.getInt("customerId");
				}
			}
			if(check) {
				System.out.println("OKOK");
				try {
					Stage stage1 = (Stage)registerButton.getScene().getWindow();
			        Model.getInstance().getViewFactory().closeStage(stage1);
				    FXMLLoader loader = new FXMLLoader();
				    loader.setLocation(getClass().getResource("/Views/Home.fxml"));
				    Parent root = loader.load(); // Tải FXML và lấy root của Scene
				    HomeController controller = loader.getController(); // Lấy controller từ FXMLLoader
					System.out.println(name);
				    controller.setCustomer(name, id);
				    // Tạo Scene và hiển thị giao diện
				    Scene scene = new Scene(root);
				    Stage stage = new Stage();
				    stage.setScene(scene);
				    stage.show();			    
				} catch (IOException e) {
				    e.printStackTrace();
				}
			}else {
				warningText.setText("Invalid Login. Please try again ");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void guest() {
    	
    }
    
    public void register() {
 	    Model.getInstance().getViewFactory().showRegister();
    }
    
    public void showAdmin() {
    	if(passwordText.getText().equals("admin") && userNameText.getText().equals("PhiLT")) {
    		Stage stage1 = (Stage)registerButton.getScene().getWindow();
	        Model.getInstance().getViewFactory().closeStage(stage1);
	        Model.getInstance().getViewFactory().showAdmin();
    	}else {
    		warningText.setText("Invalid Login. Please try again ");
    	}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		warningText.setText("");
		loginButton.setOnAction(e->{
			login();
		});
		registerButton.setOnAction(e->{
			register();
		});
		manageButton.setOnAction(e->{
			showAdmin();
		});
	}

}
