package Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import DataBase.DBConnect;
import Models.Product;
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

public class ProductManageController implements Initializable {

    @FXML
    private Button addButton;

    @FXML
    private Label categoryNameText;

    @FXML
    private TableView<Product> categoryTable;

    @FXML
    private VBox containerVbox;

    @FXML
    private Button delButton;

    @FXML
    private Button editButton;

    @FXML
    private Hyperlink homeHyperlink;

    @FXML
    private TableColumn<Product, Integer> idCol;

    @FXML
    private Text myAccountText;

    @FXML
    private TableColumn<Product, String> productDesCol;

    @FXML
    private TableColumn<Product, String> productNameCol;

    @FXML
    private TableColumn<Product, Double> productPriceCol;

    @FXML
    private TableColumn<Product, String> productStatusCol;
    
    public void loadProductData() {
    	ObservableList<Product> productList = FXCollections.observableArrayList();
    	String query = "Select * from products";
    	Connection connection = DBConnect.getConnection();
    	try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Product product = new Product();
				product.setProductID(resultSet.getInt("productId"));
				product.setProductName(resultSet.getString("productName"));
				product.setProductDescribe(resultSet.getString("productDescribe"));
				product.setProductPrice(resultSet.getDouble("productPrice"));
				product.setProductStatus(resultSet.getInt("productStatus"));
				product.setCategoryId(resultSet.getInt("categoryId"));
				productList.add(product);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  	
    	categoryTable.setItems(productList);
    	idCol.setCellValueFactory(new PropertyValueFactory<>("productID"));
    	productNameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
    	productDesCol.setCellValueFactory(new PropertyValueFactory<>("productDescribe"));
    	productPriceCol.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
    	productStatusCol.setCellValueFactory(new PropertyValueFactory<>("productStatus"));
    }
    
    public void add() {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("/Views/AddProduct.fxml"));
    	try {
			Parent root = loader.load();
			Stage primaryStage = new Stage();
	    	Scene scene = new Scene(root);
	    	primaryStage.setScene(scene);
	    	primaryStage.setTitle("Cart View"); 
	    	primaryStage.showAndWait();
	    	loadProductData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public void edit() {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("/Views/EditProduct.fxml"));
    	try {
			Parent root = loader.load();
			EditProductController controller = loader.getController();
			Product product = categoryTable.getSelectionModel().getSelectedItem();
			controller.setData(product.getProductID(), product.getProductName(), product.getProductDescribe(), product.getProductPrice(),
								product.getProductStatus(), product.getProductImage());
			Stage primStage = new Stage();
			Scene scene = new Scene(root);
			primStage.setScene(scene);
			primStage.setTitle("Edit Product");
			primStage.showAndWait();
			loadProductData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
    }
    
    public void delete() {
    	Product product = categoryTable.getSelectionModel().getSelectedItem();
    	String query = "Delete from Products where productId = " + product.getProductID();
    	Connection connection = DBConnect.getConnection();
    	try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	loadProductData();
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		addButton.setOnAction(e->{
			add();
		});
		editButton.setOnAction(e->{
			edit();
		});
		delButton.setOnAction(e->{
			delete();
		});
	}

}
