package Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.mysql.cj.xdevapi.DbDoc;

import DataBase.DBConnect;
import Models.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class OrderManageController implements Initializable{

    @FXML
    private Button detailButton;

    @FXML
    private TableColumn<Order, String> addressCol;

    @FXML
    private Label categoryNameText;

    @FXML
    private TableView<Order> categoryTable;

    @FXML
    private VBox containerVbox;

    @FXML
    private TableColumn<Order, Integer> customerIdCol;

    @FXML
    private Button delButton;

    @FXML
    private Button editButton;

    @FXML
    private Hyperlink homeHyperlink;

    @FXML
    private TableColumn<Order, Integer> idCol;

    @FXML
    private Text myAccountText;

    @FXML
    private TableColumn<Order, String> orderStatusCol;

    @FXML
    private TableColumn<Order, Double> orderTotalCol;
    
    public void loadData() {
    	ObservableList<Order> orderList = FXCollections.observableArrayList();
    	String query = "Select * from orders";
    	Connection connection = DBConnect.getConnection();
    	try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Order order = new Order();
				order.setCustomerId(resultSet.getInt("customerId"));
				order.setOrderId(resultSet.getInt("orderId"));
				order.setOrderStatus(resultSet.getString("orderStatus"));
				order.setOrderTotal(resultSet.getDouble("orderTotal"));
				order.setAddress(resultSet.getString("address"));
				orderList.add(order);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	categoryTable.setItems(orderList);
    	idCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));
    	customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
    	orderStatusCol.setCellValueFactory(new PropertyValueFactory<>("orderStatus"));
    	orderTotalCol.setCellValueFactory(new PropertyValueFactory<>("orderTotal"));
    	addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
    }
    
    public void showDetail() {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("/Views/OrderDetail.fxml"));
    	try {
			Parent root = loader.load();
			OrderDetailController controller = loader.getController();
			Order detail = categoryTable.getSelectionModel().getSelectedItem();
			controller.setData(detail.getOrderId());
			controller.loadData();
			Stage primStage = new Stage();
			Scene scene = new Scene(root);
			primStage.setScene(scene);
			primStage.setTitle("Detail");
			primStage.showAndWait();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void delete() {
    	Order order = categoryTable.getSelectionModel().getSelectedItem();
    	String query1 = "Delete from orderDetails Where orderId = ?";
    	String query2 = "Delete from orders where orderId = ?";
    	Connection connection = DBConnect.getConnection();
    	try {
			PreparedStatement preparedStatement = connection.prepareStatement(query1);
			preparedStatement.setInt(1, order.getOrderId());
			preparedStatement.executeUpdate();
			preparedStatement = connection.prepareStatement(query2);
			preparedStatement.setInt(1,order.getOrderId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	loadData();
    }
    
    public void update() {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("/Views/EditOrder.fxml"));
    	try {
			Parent root = loader.load();
			EditOrderController controller = loader.getController();
			Order order = categoryTable.getSelectionModel().getSelectedItem();
			controller.setData(order.getOrderId());
			Stage primStage = new Stage();
			Scene scene = new Scene(root);
			primStage.setScene(scene);
			primStage.setTitle("UPDATE");
			primStage.showAndWait();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		loadData();
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		loadData();
		detailButton.setOnAction(e->{
			showDetail();
		});
		delButton.setOnAction(e->{
			delete();
		});
		editButton.setOnAction(e->{
			update();
		});
	}

}
