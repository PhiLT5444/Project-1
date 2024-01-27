package Controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import DataBase.DBConnect;
import Models.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class AccountManageController implements Initializable {

    @FXML
    private Label categoryNameText;

    @FXML
    private TableView<Customer> categoryTable;

    @FXML
    private VBox containerVbox;

    @FXML
    private Button delButton;

    @FXML
    private TableColumn<Customer, String> emailCol;

    @FXML
    private Hyperlink homeHyperlink;

    @FXML
    private TableColumn<Customer, Integer> idCol;

    @FXML
    private Text myAccountText;

    @FXML
    private TableColumn<Customer, String> nameCol;

    @FXML
    private TableColumn<Customer, String> passwordCol;

    @FXML
    private TableColumn<Customer, String> phoneNumberCol;
    
    public void loadData() {
    	ObservableList<Customer> customerList = FXCollections.observableArrayList();
    	String query = "Select * from customers";
    	Connection connection = DBConnect.getConnection();
    	try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Customer cus = new Customer();
				cus.setCustomerId(resultSet.getInt("customerId"));
				cus.setCustomerName(resultSet.getString("customerName"));
				cus.setCustomerEmail(resultSet.getString("customerEmail"));
				cus.setCustomerPassword(resultSet.getString("customerPassword"));
				cus.setCustomerPhone(resultSet.getString("customerPhone"));
				customerList.add(cus);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	categoryTable.setItems(customerList);
    	idCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
    	nameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
    	emailCol.setCellValueFactory(new PropertyValueFactory<>("customerEmail"));
    	passwordCol.setCellValueFactory(new PropertyValueFactory<>("customerPassword"));
    	phoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
    }
    
    public void delete() {
    	String query = "Delete from customers where customerId = ?";
    	Customer cus = categoryTable.getSelectionModel().getSelectedItem();
    	Connection connection = DBConnect.getConnection();
    	try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, cus.getCustomerId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	loadData();
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		loadData();
		delButton.setOnAction(e->{
			delete();
		});
	}

}
