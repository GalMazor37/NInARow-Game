package address.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class endOfGameFormController {

    @FXML
    private AnchorPane formAnchorPane;

    @FXML
    private BorderPane formBoardPane;

    @FXML
    private AnchorPane headLineAnchorPane;

    @FXML
    private Label winnersLabelHeadLine;

    @FXML
    private Label winnersLabelHeadLine1;


    @FXML
    private Label drawLabelHeadLine;

    @FXML
    private GridPane winnersNamesGridPane;

    @FXML
    private AnchorPane winnerAnchorPane1;

    @FXML
    private Label winnerLabel1;

    @FXML
    private AnchorPane winnerAnchorPane3;

    @FXML
    private Label winnerLabel3;

    @FXML
    private AnchorPane winnerAnchorPane5;

    @FXML
    private Label winnerLabel5;

    @FXML
    private AnchorPane winnerAnchorPane2;

    @FXML
    private Label winnerLabel2;

    @FXML
    private AnchorPane winnerAnchorPane4;

    @FXML
    private Label winnerLabel4;

    @FXML
    private AnchorPane winnerAnchorPane6;

    @FXML
    private Label winnerLabel6;

    @FXML
    private Button OKButton;
    @FXML
    private void OkButtonActionListener(ActionEvent event) {
        closeButtonAction();
    }

    @FXML
    private void closeButtonAction(){
        Stage stage = (Stage) OKButton.getScene().getWindow();
        stage.close();
    }

    MainFormController mainAppController;
    @FXML

    public void UpdateWinners() {
        if (mainAppController.getEngine().getGame().getWinners() ==  null) {
            winnersLabelHeadLine.setText("Game over!");
            winnersLabelHeadLine1.setText("The game ended with a DRAW!");
        }
        else if (mainAppController.getEngine().getGame().getWinners().size() == 1) {
            winnersLabelHeadLine1.setText("The winner is:");
            winnerLabel1.setText(mainAppController.getEngine().getGame().getWinners().get(0));
        }
        else {
            winnersLabelHeadLine1.setText("The winners are:");
            winnerLabel1.setText(mainAppController.getEngine().getGame().getWinners().get(0));
            if (mainAppController.getEngine().getGame().getWinners().size() >= 2) {
                winnerLabel2.setText(mainAppController.getEngine().getGame().getWinners().get(1));
            } if (mainAppController.getEngine().getGame().getWinners().size() >= 3) {
                winnerLabel3.setText(mainAppController.getEngine().getGame().getWinners().get(2));
            } if (mainAppController.getEngine().getGame().getWinners().size() >= 4) {
                winnerLabel4.setText(mainAppController.getEngine().getGame().getWinners().get(3));
            }if (mainAppController.getEngine().getGame().getWinners().size() >= 5) {
                winnerLabel5.setText(mainAppController.getEngine().getGame().getWinners().get(4));
            }if (mainAppController.getEngine().getGame().getWinners().size() >= 6) {
                winnerLabel6.setText(mainAppController.getEngine().getGame().getWinners().get(5));
            }
        }
    }

    public void setMainApp(MainFormController mainFormController) {
        mainAppController = mainFormController;
    }
}
