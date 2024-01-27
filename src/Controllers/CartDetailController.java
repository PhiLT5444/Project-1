package Controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import DataBase.DBConnect;
import Models.OrderDetail;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class CartDetailController implements Initializable{

    @FXML
    private TableColumn<OrderDetail, Integer> orderIdCol;

    @FXML
    private TableColumn<OrderDetail, String> productNameCol;

    @FXML
    private TableColumn<OrderDetail, Double> quantitiesCol;

    @FXML
    private TableView<OrderDetail> tableView;
    
    public void loadData(Integer customerID, Integer orderId) {
    	ObservableList<OrderDetail> orderDetailList = FXCollections.observableArrayList();
    	String query = "SELECT DISTINCT orderDetails.*\r\n"
				+ "FROM Cart, orders, orderDetails, customers\r\n"
				+ "WHERE orders.orderId = orderDetails.orderId and  customers.customerId = ? and orders.orderId = ?;";
    	Connection connection = DBConnect.getConnection();
    	try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, customerID);
			preparedStatement.setInt(2, orderId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				OrderDetail orderDt = new OrderDetail();
				orderDt.setOrderId(resultSet.getInt("orderId"));
				orderDt.setProductId(resultSet.getInt("productId"));
				orderDt.setProductQuantities(resultSet.getDouble("productQuantities"));
				orderDetailList.add(orderDt);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	tableView.setItems(orderDetailList);
    	orderIdCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));
    	productNameCol.setCellValueFactory(new PropertyValueFactory<>("productId"));
    	quantitiesCol.setCellValueFactory(new PropertyValueFactory<>("productQuantities"));
    	productNameCol.setCellValueFactory(cellData -> {
    	    Integer productId = cellData.getValue().getProductId();
    	    String productName = "Unknown"; // Default value

    	    if (productId != null) {
    	        String query1 = "Select * from products where productId = " + productId;
    	        try {
					PreparedStatement preparedStatement1 = connection.prepareStatement(query1);
					ResultSet resultSet1 = preparedStatement1.executeQuery();
					while(resultSet1.next()) {
						productName = resultSet1.getString("productName");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    	    }

    	    return new SimpleStringProperty(productName);
    	});
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
