package Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import DataBase.DBConnect;
import Models.Category;
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

public class CategoryManageController implements Initializable{

    @FXML
    private Button addButton;

    @FXML
    private TableColumn<Category, String> categoryDesCol;

    @FXML
    private TableColumn<Category, String> categoryNameCol;

    @FXML
    private Label categoryNameText;

    @FXML
    private TableColumn<Category, Integer> categoryStatusCol;

    @FXML
    private TableView<Category> categoryTable;

    @FXML
    private VBox containerVbox;

    @FXML
    private Button delButton;

    @FXML
    private Button editButton;

    @FXML
    private Hyperlink homeHyperlink;

    @FXML
    private TableColumn<Category, Integer> idCol;

    @FXML
    private Text myAccountText;
    
    public void loadCategoryData() {
        ObservableList<Category> cateList = FXCollections.observableArrayList();
        String query = "SELECT * FROM categories";
        Connection connection = DBConnect.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Category cate = new Category();
                cate.setCategoryId(resultSet.getInt("categoryId"));
                cate.setCategoryName(resultSet.getString("categoryName"));
                cate.setStatus(resultSet.getInt("categoryStatus"));
                cate.setCategoryDescribe(resultSet.getString("categoryDescribe"));
                cateList.add(cate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        categoryTable.setItems(cateList);
        idCol.setCellValueFactory(new PropertyValueFactory<>("categoryId"));
        categoryNameCol.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        categoryDesCol.setCellValueFactory(new PropertyValueFactory<>("categoryDescribe"));
        categoryStatusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
    }
    
    public void delete() {
    	Category cate = categoryTable.getSelectionModel().getSelectedItem();
    	String query = "Delete from categories where categoryId = " + cate.getCategoryId();
    	Connection connection = DBConnect.getConnection();
    	try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	loadCategoryData();
    }
    
    public void addCategory() {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("/Views/AddCategory.fxml"));
    	Parent root;
		try {
			root = loader.load();
			Stage primaryStage = new Stage();
	    	Scene scene = new Scene(root);
	    	primaryStage.setScene(scene);
	    	primaryStage.setTitle("Cart View"); 
	    	primaryStage.showAndWait();
	    	loadCategoryData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void edit() {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("/Views/EditCategory.fxml"));
    	try {
			Parent root;
			root = loader.load();
			EditCategoryController controller = loader.getController();
			Category selectCategory = categoryTable.getSelectionModel().getSelectedItem();
			controller.setData(selectCategory.getCategoryName(), selectCategory.getCategoryDescribe(), 
							   selectCategory.getStatus(), selectCategory.getImagePath(), selectCategory.getCategoryId());
			Stage primaryStage = new Stage();
	    	Scene scene = new Scene(root);
	    	primaryStage.setScene(scene);
	    	primaryStage.setTitle("Cart View"); 
	    	primaryStage.showAndWait();
	    	loadCategoryData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		loadCategoryData();
		delButton.setOnAction(e->{
			delete();
		});
		addButton.setOnAction(e->{
			addCategory();
		});
		editButton.setOnAction(e->{
			edit();
		});
		
	}

}
