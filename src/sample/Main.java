package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("sample.fxml"));
        StackPane stackpane = loader.load();

        Scene scene = new Scene(stackpane);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Pierwszy program javafx");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
