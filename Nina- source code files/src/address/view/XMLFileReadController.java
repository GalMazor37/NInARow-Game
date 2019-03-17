package address.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class XMLFileReadController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane xmlProgressPopupAnchorPane;

    @FXML
    private VBox vBoxContainer;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label progressMassageLabel;

    @FXML
    private Button OKButton;

    @FXML
    void initialize() {
        OKButton.disableProperty().set(false);
    }

    Task task;

    public void setTask(Task task) {
        this.task = task;
        progressBar.progressProperty().bind(task.progressProperty());
        OKButton.disableProperty().bind(task.progressProperty().isEqualTo(10));
        task.messageProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                progressMassageLabel.setText(newValue);
            }
        });
    }

    @FXML
    private void OkButtonActionListener(ActionEvent event) {
        closeButtonAction();
    }

    @FXML
    private void closeButtonAction(){
        Stage stage = (Stage) OKButton.getScene().getWindow();
        stage.close();
    }
}