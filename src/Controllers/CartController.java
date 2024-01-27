package Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.mysql.cj.xdevapi.Statement;

import DataBase.DBConnect;
import Models.Model;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CartController implements Initializable{

    @FXML
    private VBox layout;

    @FXML
    private Button payButton;

    @FXML
    private Text sumButton;
    
    @FXML
    private Button yourOrderButton;
    
    int customerId;
    
    public void setData(int id) {
    	this.customerId = id;
    }
    public void loadData() {
        double total = 0;
        String query = "Select distinct * from Cart, Products Where CustomerId = ? AND "
                + "Cart.ProductId = Products.productId";
        Connection connection = DBConnect.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, customerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            int num = 1;

            layout.getChildren().clear(); // Clear existing items before loading new data

            while (resultSet.next()) {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/Views/CartItem.fxml"));
                    HBox hbox = loader.load();
                    CartItemController controller = loader.getController();

                    controller.setData(num, resultSet.getString("productName"), resultSet.getDouble("productPrice"),
                            resultSet.getDouble("quantities"), resultSet.getInt("customerId"), resultSet.getInt("productId"));
                    total = total + resultSet.getDouble("productPrice") * resultSet.getDouble("quantities");
                    layout.getChildren().add(hbox);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                num++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Double sum = total;
        sumButton.setText(sum.toString());
    }
    
	public void placeOrder(int customerId, String address) {
	    String orderInsertQuery = "INSERT INTO Orders (customerId, orderTotal, orderStatus, address) VALUES (?, ?, ?, ?)";
	    String orderDetailQuery = "INSERT INTO OrderDetails (orderId, productId, productQuantities) VALUES (?, ?, ?)";
	    String cartQuery = "SELECT productId, quantities FROM Cart WHERE customerId = ?";
	    
	    try (Connection connection = DBConnect.getConnection();
	         PreparedStatement orderInsertStatement = connection.prepareStatement(orderInsertQuery, new String[]{"orderId"});
	         PreparedStatement orderDetailStatement = connection.prepareStatement(orderDetailQuery);
	         PreparedStatement cartStatement = connection.prepareStatement(cartQuery);
	    ) {
	        // Inserting into Orders table
	        orderInsertStatement.setInt(1, customerId);
	        orderInsertStatement.setDouble(2, Double.parseDouble(sumButton.getText())); // Assuming a method to calculate total
	        orderInsertStatement.setString(3, "In Progress");
	        orderInsertStatement.setString(4, address);
	        orderInsertStatement.executeUpdate();

	        // Retrieving orderId for the newly inserted order
	        ResultSet generatedKeys = orderInsertStatement.getGeneratedKeys();
	        int orderId = 0;
	        if (generatedKeys.next()) {
	            orderId = generatedKeys.getInt(1);
	        } else {
	            // Handle error, couldn't retrieve orderId
	            System.err.println("Failed to retrieve orderId.");
	            return;
	        }

	        // Inserting order details from the cart
	        cartStatement.setInt(1, customerId);
	        ResultSet cartResult = cartStatement.executeQuery();
	        while (cartResult.next()) {
	            orderDetailStatement.setInt(1, orderId);
	            orderDetailStatement.setInt(2, cartResult.getInt("productId"));
	            orderDetailStatement.setDouble(3, cartResult.getDouble("quantities"));
	            orderDetailStatement.executeUpdate();
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}


    // Example method for calculating order total (replace with your logic)
    private double calculateOrderTotal() {
        return 0.0;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    
    public void showYourOrder() {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("/Views/YourOrder.fxml"));
    	try {
			Parent root = loader.load();
			YourOrderController controller = loader.getController();
			controller.setData(customerId);
			controller.loadData();
			Stage stage = new Stage();
		    // Set the FXML content as the scene for the stage
		    Scene scene = new Scene(root);
		    stage.setScene(scene);
		    // Set the title of the stage
		    stage.setTitle("Your Order");
		    // Show the stage
		    stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void payAction() {
    	// Tạo cửa sổ Popup
	    Stage popupStage = new Stage();

	    // Tạo layout cho cửa sổ Popup
	    VBox popupLayout = new VBox();

	    // Tạo các trường nhập thông tin
	    TextField nameField = new TextField();
	    TextField addressField = new TextField();
	    TextField phoneNumber = new TextField();
	    Button confirmButton = new Button("Confirm");
	    confirmButton.setOnAction(e1 -> {
	        // Kiểm tra xem các trường nhập liệu có rỗng không
	        if (nameField.getText().isEmpty() || addressField.getText().isEmpty() || phoneNumber.getText().isEmpty()) {
	            // Nếu có trường nào đó rỗng, hiển thị thông báo lỗi
	            showAlert("Error", "Please fill in all the fields.");
	        } else {
	            // Nếu tất cả các trường đều đã điền, thực hiện đặt hàng và đóng cửa sổ Popup
	            placeOrder(customerId, addressField.getText());
	            popupStage.close();

	            // Hiển thị thông báo đặt hàng thành công
	            showAlert("Success", "Order placed successfully!");
	        }
	    });

	    // Thêm các trường nhập và nút xác nhận vào layout
	    popupLayout.getChildren().addAll(
	            new Label("Enter your name:"),
	            nameField,
	            new Label("Enter your address:"),
	            addressField,
	            new Label("Enter delivery phone number:"),
	            phoneNumber,
	            confirmButton
	    );
	    popupLayout.setAlignment(Pos.CENTER);
	    popupLayout.setSpacing(5);

	    // Tạo scene cho cửa sổ Popup và hiển thị
	    Scene popupScene = new Scene(popupLayout, 300, 200);
	    popupStage.setScene(popupScene);
	    popupStage.setTitle("Enter Your Information");
	    popupStage.show();
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loadData();		
		payButton.setOnAction(e -> {
		    payAction();
		});
		yourOrderButton.setOnAction(e->{
			showYourOrder();
		});		
	}

}
