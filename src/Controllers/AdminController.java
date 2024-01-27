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
import Models.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AdminController implements Initializable {


    @FXML
    private Button accountManageButton;

    @FXML
    private Button categoryManageButton;

    @FXML
    private Label categoryNameText;

    @FXML
    private VBox containerVbox;

    @FXML
    private Hyperlink homeHyperlink;

    @FXML
    private Button logOutButton;

    @FXML
    private Text myAccountText;

    @FXML
    private Button orderManageButton;

    @FXML
    private Button searchButton;
    
    @FXML
    private Button productManageButton;

    @FXML
    private TextField searchText;
    
    public void showProductManage() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Views/ProductManage.fxml"));
        try {
            VBox vbox = loader.load();
            ProductManageController controller = loader.getController();
            controller.loadProductData();
            containerVbox.getChildren().setAll(cloneVBox(vbox).getChildren());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private VBox cloneVBox(VBox original) {
        VBox copy = new VBox(original.getSpacing());
        copy.getChildren().addAll(original.getChildren());
        return copy;
    }

    public void showCatagoryManage() {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("/Views/CategoryManage.fxml"));
    	try {
			VBox vbox = loader.load();
			containerVbox.getChildren().setAll(cloneVBox(vbox).getChildren());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   	
    }
    
    public void showOrderManage() {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("/Views/OrderManage.fxml"));
    	try {
			VBox vbox = loader.load();
			containerVbox.getChildren().setAll(cloneVBox(vbox).getChildren());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
    }
    
    public void showAccountManage() {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("/Views/AccountManage.fxml"));
    	try {
			VBox vbox = loader.load();
			containerVbox.getChildren().setAll(cloneVBox(vbox).getChildren());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        productManageButton.setOnAction(e->{
        	showProductManage();
        });
        categoryManageButton.setOnAction(e->{
    	   showCatagoryManage();
        });
        orderManageButton.setOnAction(e->{
        	showOrderManage();
        });
        accountManageButton.setOnAction(e->{
        	showAccountManage();
        });
        logOutButton.setOnAction(e->{
        	Stage stage1 = (Stage)logOutButton.getScene().getWindow();
	        Model.getInstance().getViewFactory().closeStage(stage1);
	        Model.getInstance().getViewFactory().showLogin();
        });
    }
}
