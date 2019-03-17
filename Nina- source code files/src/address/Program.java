package address;


import address.model.Engine;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;


public class Program extends Application {
    private Stage primaryStage;
    private GridPane rootLayout;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("N in a Row");
        initRootForm();
    }

    public Stage getPrimaryStage() {return primaryStage; }


    public void initRootForm() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Program.class.getResource("view/MainFormSecondOption.fxml"));

            //loader.setLocation(Program.class.getResource("view/MainForm.fxml"));
            rootLayout = loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

            //give controller access to main
            address.view.MainFormController controller = loader.getController();
            controller.setMain(this);
           // controller.setEngine(new Engine());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}