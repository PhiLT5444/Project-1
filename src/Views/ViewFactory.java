package Views;


import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewFactory {
    public void showHome() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
        createStage(loader);
    }
    
    public void showLogin() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        createStage(loader);
    }
    
    public void showRegister() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Register.fxml"));
        createStage(loader);
    }
    
    public void showCart() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Cart.fxml"));
        createStage(loader);
    }
    
    public void showAdmin() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Admin.fxml"));
        createStage(loader);
    }
    
    public void createStage(FXMLLoader loader) {
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (Exception e){
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
    public void createFullScreenStage(FXMLLoader loader){
        Scene scene = null;
        try {
            scene = new Scene(loader.load());	
        } catch (Exception e){
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
    public void closeStage(Stage stage) {
        stage.close();
    }
}