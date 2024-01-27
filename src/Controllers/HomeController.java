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
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HomeController implements Initializable{

	@FXML
    private Button addToCartButton;

    @FXML
    private VBox cardButton;

    @FXML
    private GridPane categoryGridPane;

    @FXML
    private VBox containerVbox;

    @FXML
    private ImageView fruitImage;

    @FXML
    private Label fruitNameLable;
    
    @FXML
    private Label categoryNameText;

    @FXML
    private Label fruitPriceLabel;

    @FXML
    private TextField massText;

    @FXML
    private Text myAccountText;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchText;
    
    @FXML
    private Hyperlink homeHyperlink;

    @FXML
    private ImageView cartImage;
    
    @FXML 
    private Label soLuongText;
    
    @FXML
    private Label xuatSuText;
    
    private Integer customerID;
    
    public void setCustomer(String name, Integer id) {
		myAccountText.setText(name);
		customerID = id;
	}
    
    
    int categoryID;
    int productId;
    
    
    
    //Hiễn thị các danh mục (category)
    public void loadData() {
    	categoryGridPane.getChildren().clear();
    	String query = "Select * from Categories";
    	Connection connection = DBConnect.getConnection();
    	try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			int column = 0;
			int row = 1;
			while(resultSet.next()) {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/Views/categoryItem.fxml"));
				int x = resultSet.getInt("categoryId");
				String name = resultSet.getString("categoryName");
				try {
					VBox vbox = loader.load();
					vbox.setOnMouseClicked(e->{
						categoryID = x;
						System.out.println(categoryID);
						categoryNameText.setText(name);
						loadProductData();
					});
					CategoryItemController controller = loader.getController();
					controller.setData(resultSet.getString("categoryName"), resultSet.getString("imagePath"));
					if (column == 3) {
	                    column = 0;
	                    row++;
	                }
					categoryGridPane.add(vbox, column++, row); //(child,column,row)
	                //set grid width
	                categoryGridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
	                categoryGridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
	                categoryGridPane.setMaxWidth(Region.USE_PREF_SIZE);
	                //set grid height
	                categoryGridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
	                categoryGridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
	                categoryGridPane.setMaxHeight(Region.USE_PREF_SIZE);
	                GridPane.setMargin(vbox, new Insets(10));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
    }
    
    
    //Hiễn thị các sản phẩm hoa quả(product)
    public void loadProductData() {
    	categoryGridPane.getChildren().clear();
    	String query = "select * from Products where categoryId = ?";
    	Connection connection = DBConnect.getConnection();
    	int column = 0;
		int row = 1;
    	try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, categoryID);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {	
				String imagePath = resultSet.getString("productImage");
				String name = resultSet.getString("productName");
				Double price = resultSet.getDouble("productPrice");
				Integer soluong = resultSet.getInt("productStatus");
				String des = resultSet.getString("productDescribe");
				int id = resultSet.getInt("productId");
				try {
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("/Views/ProductItem.fxml"));
					VBox vbox = loader.load();
					vbox.setOnMouseClicked(e->{
						System.out.println("OKOK");
						Image image = new Image(imagePath);
						fruitImage.setImage(image);
						fruitNameLable.setText(name);
						fruitPriceLabel.setText(price.toString());
						soLuongText.setText(soluong.toString());
						xuatSuText.setText(des);
						productId = id;
					});
					ProductItemController controller = loader.getController();
					controller.setData(resultSet.getString("productName"), resultSet.getString("productImage"), resultSet.getDouble("productPrice"));
					if (column == 3) {
	                    column = 0;
	                    row++;
	                }
					categoryGridPane.add(vbox, column++, row); //(child,column,row)
	                //set grid width
	                categoryGridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
	                categoryGridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
	                categoryGridPane.setMaxWidth(Region.USE_PREF_SIZE);
	                //set grid height
	                categoryGridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
	                categoryGridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
	                categoryGridPane.setMaxHeight(Region.USE_PREF_SIZE);
	                GridPane.setMargin(vbox, new Insets(10));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    // Phương thức để hiển thị cửa sổ alert lỗi
    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    // Phương thức để hiển thị cửa sổ alert thành công
    private void showSuccessAlert(String title, String header, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    public void addToCart() {
        String massTextValue = massText.getText().trim(); // Loại bỏ khoảng trắng ở đầu và cuối chuỗi

        if (!massTextValue.isEmpty()) {
            try {
                Double mass = Double.parseDouble(massTextValue);

                // Tiếp tục với việc thêm vào cơ sở dữ liệu
                String query = "insert into Cart(customerID, productId, quantities) values(?,?,?)";
                Connection connection = DBConnect.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, customerID);
                preparedStatement.setInt(2, productId);
                preparedStatement.setDouble(3, mass);
                preparedStatement.executeUpdate();
                
                // Hiển thị thông báo thành công nếu không có lỗi khi thêm vào cơ sở dữ liệu
                showSuccessAlert("Thành công", "Thông báo", "Đã thêm vào giỏ hàng!");
            } catch (NumberFormatException | SQLException e) {
                // Xử lý lỗi nếu có lỗi khi chuyển đổi hoặc thêm vào cơ sở dữ liệu
                e.printStackTrace();
                showAlert("Lỗi", "Lỗi thêm vào giỏ hàng", "Vui lòng kiểm tra lại dữ liệu!");
            }
        } else {
            // Hiển thị cảnh báo nếu không có dữ liệu nhập vào
            showAlert("Cảnh báo", "Cảnh báo", "Vui lòng nhập khối lượng!");
        }
    }


    
    
    public void showCart() {
    	 
		try {
			FXMLLoader loader = new FXMLLoader();
	    	loader.setLocation(getClass().getResource("/Views/Cart.fxml"));
	    	Parent root;
			root = loader.load();
			CartController controller = loader.getController();
	    	controller.setData(customerID);
	    	controller.loadData();
	    	Stage primaryStage = new Stage();
	    	Scene scene = new Scene(root);
	    	primaryStage.setScene(scene);
	    	primaryStage.setTitle("Cart View"); // Đặt tiêu đề của cửa sổ
	    	primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }
    
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		loadData();
		
		homeHyperlink.setOnAction(e->{
			loadData();
		});
		
		cartImage.setOnMouseClicked(e->{
			showCart();
			System.out.println(customerID);
		});
		
		addToCartButton.setOnAction(e->{
			addToCart();
			System.out.println(massText.getText());
		});
		
	}
}
