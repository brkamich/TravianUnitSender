package org.pupkaci.travianunitsender;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainApp extends Application {

    public Scene currentScene;
    public Stage stage;
    public static MainApp instance;
    @Override
    
    public void start(Stage stage) throws Exception {
        instance = this;
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/LoginFX.fxml"));
        
        currentScene = new Scene(root);
        this.stage = stage;
        stage.setTitle("Travian Unit Sender");
        stage.setScene(currentScene);
        stage.show();
    }
    public static void onLogin(String username,String server) 
    {
        try
        {
            Parent root = FXMLLoader.load(MainApp.class.getResource("/fxml/MainScene.fxml"));
            instance.currentScene = new Scene(root);
            instance.stage.setScene(instance.currentScene);
            instance.stage.show();
            instance.stage.setMinWidth(916);
            instance.stage.setMinHeight(739);
            System.out.println("W:"+instance.stage.widthProperty().get()+" H:"+instance.stage.heightProperty().get());
            instance.stage.setTitle(username+" @ "+server);
        }
        catch(Exception ex)
        {
            
        }
    }
    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}