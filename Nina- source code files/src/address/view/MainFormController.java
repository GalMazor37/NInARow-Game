package address.view;

import address.model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import address.*;
import address.utils.constants.ConstantMassages;
import address.utils.xsdClass.GameDescriptor;
import javafx.application.Platform;
import javafx.beans.property.*;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.scene.shape.*;
import javafx.stage.StageStyle;

import javax.xml.bind.JAXBException;

import static java.util.Arrays.asList;


public class MainFormController {
    @FXML
    private GridPane headerGridPane;

    @FXML
    private RowConstraints firstLineGridPane;

    @FXML
    private AnchorPane firstLine0AnchorPane;

    @FXML
    private GridPane gridPaneForLine0;

    @FXML
    private AnchorPane gameLabelAnchorPane;

    @FXML
    private Label headLineLabelNinaRow;

    @FXML
    private AnchorPane anchorPaneMenuButtons;

    @FXML
    private HBox hBoxMenuButtons;

    @FXML
    private Button openFileButton;

    @FXML
    private Button startGameButton;

    @FXML
    private Button animationButton;

    @FXML
    private Button skinButton;

    @FXML
    private Button quitGameButton;

    @FXML
    private ScrollPane playersScrollPaneArea;

    @FXML
    private GridPane playersDisplayGridPane;

    @FXML
    private AnchorPane blueCircleAnchorPane;

    @FXML
    private BorderPane blueCircleBorderPane;

    @FXML
    private Circle blueCircle;

    @FXML
    private VBox blueCircleVBox;

    @FXML
    private Label blueLabelPlayerID;

    @FXML
    private Label blueLabelPlayerType;

    @FXML
    private Label blueLabelPlayerTurns;

    @FXML
    private AnchorPane yellowCircleAnchorPane;

    @FXML
    private BorderPane yellowCircleBorderPane;

    @FXML
    private Circle yellowCircle;

    @FXML
    private VBox yellowCircleVBox;

    @FXML
    private Label yellowLabelPlayerID;

    @FXML
    private Label yellowLabelPlayerType;

    @FXML
    private Label yellowLabelPlayerTurns;

    @FXML
    private AnchorPane greenCircleAnchorPane;

    @FXML
    private BorderPane greenCircleBorderPane;

    @FXML
    private Circle greenCircle;

    @FXML
    private VBox greenCircleVBox;

    @FXML
    private Label greenLabelPlayerID;

    @FXML
    private Label greenLabelPlayerType;

    @FXML
    private Label greenLabelPlayerTurns;

    @FXML
    private AnchorPane purpleCircleAnchorPane;

    @FXML
    private BorderPane purpleCircleBorderPane;

    @FXML
    private Circle purpleCircle;

    @FXML
    private VBox purpleCircleVBox;

    @FXML
    private Label purpleLabelPlayerID;

    @FXML
    private Label purpleLabelPlayerType;

    @FXML
    private Label purpleLabelPlayerTurns;

    @FXML
    private AnchorPane orangeCircleAnchorPane;

    @FXML
    private BorderPane orangeCircleBorderPane;

    @FXML
    private Circle orangeCircle;

    @FXML
    private VBox orangeCircleVBox;

    @FXML
    private Label orangeLabelPlayerID;

    @FXML
    private Label orangeLabelPlayerType;

    @FXML
    private Label orangeLabelPlayerTurns;

    @FXML
    private AnchorPane redCircleAnchorPane;

    @FXML
    private BorderPane redCircleBorderPane;

    @FXML
    private Circle redCircle;

    @FXML
    private VBox redCircleVBox;

    @FXML
    private Label redLabelPlayerID;

    @FXML
    private Label redLabelPlayerType;

    @FXML
    private Label redLabelPlayerTurns;

    @FXML
    private BorderPane blueCirclePlayerNameBoarderPane;

    @FXML
    private Label blueLabelPlayerName;

    @FXML
    private BorderPane yellowCirclePlayerNameBoarderPane;

    @FXML
    private Label yellowLabelPlayerName;

    @FXML
    private BorderPane greenCirclePlayerNameBoarderPane;

    @FXML
    private Label greenLabelPlayerName;

    @FXML
    private BorderPane purpleCirclePlayerNameBoarderPane;

    @FXML
    private Label purpleLabelPlayerName;

    @FXML
    private BorderPane orangeCirclePlayerNameBoarderPane;

    @FXML
    private Label orangeLabelPlayerName;

    @FXML
    private BorderPane redCirclePlayerNameBoarderPane;

    @FXML
    private Label redLabelPlayerName;

    @FXML
    private GridPane boardGameGridPaneArea;

    @FXML
    private ScrollPane gameBoardScrollPane;

    @FXML
    private BorderPane gameBoardBorderPane;

    @FXML
    private AnchorPane gameBoardAnchorPane;

    @FXML
    private GridPane gridPanePopInOrOut;

    @FXML
    private AnchorPane popOutAnchorPane;

    @FXML
    private Button popOutButton;

    @FXML
    private AnchorPane popInAnchorPane;

    @FXML
    private Button popInButton;


    @FXML
    void initialize() {
        isFileSelected = new SimpleBooleanProperty(false);
        isGameStarted = new SimpleBooleanProperty(false);
        isAnimation = new SimpleBooleanProperty(true);
        isLightSkin = new SimpleBooleanProperty(true);
        bindPlayers();


        //todo: insert into one function
        startGameButton.disableProperty().bind(isFileSelected.not());
        animationButton.disableProperty().bind(isGameStarted.not());
        skinButton.disableProperty().bind(isGameStarted.not());
        quitGameButton.disableProperty().bind(isGameStarted.not());
        //startGameButton.onActionProperty()

        playersScrollPaneArea.setDisable(true);
        boardGameGridPaneArea.setDisable(true);

    }


    private Program mainApp;
    private Engine engine;
    private SimpleBooleanProperty isFileSelected;
    private SimpleBooleanProperty isGameStarted;
    private SimpleBooleanProperty isAnimation;
    private SimpleBooleanProperty isLightSkin;
    private Color currentDiscColor;

    public boolean isAnimation() {
        return isAnimation.get();
    }

    public SimpleBooleanProperty isAnimationProperty() {
        return isAnimation;
    }

    public void setIsAnimation(boolean isAnimation) {
        this.isAnimation.set(isAnimation);
    }

    public Color getCurrentDiscColor() {
        return engine.getCurrentColor();
    }

    public Player getCurrentPlayer() {
        return engine.getCurrentPlayer();
    }

    private BoardGameController board;
    ArrayList<PlayerProperties> playerProperties = new ArrayList<PlayerProperties>(
            asList(
                    new PlayerProperties("", "", "", "", Color.GREY),
                    new PlayerProperties("", "", "", "", Color.GREY),
                    new PlayerProperties("", "", "", "", Color.GREY),
                    new PlayerProperties("", "", "", "", Color.GREY),
                    new PlayerProperties("", "", "", "", Color.GREY),
                    new PlayerProperties("", "", "", "", Color.GREY)
            )
    );

    public boolean isIsFileSelected() {
        return isFileSelected.get();
    }

    public SimpleBooleanProperty isFileSelectedProperty() {
        return isFileSelected;
    }

    public void setIsFileSelected(boolean isFileSelected) {
        this.isFileSelected.set(isFileSelected);
    }


    @FXML
    private void PopInActionListener(ActionEvent event) {
        popOutButton.setDefaultButton(false);
        popInButton.setDefaultButton(true);
    }

    @FXML
    private void PopOutActionListener(ActionEvent event) {
        popOutButton.setDefaultButton(true);
        popInButton.setDefaultButton(false);
    }

    @FXML
    private void quitGameButtonActionListener(ActionEvent event) {
        SetFormToStartingPointAsNewForm();
    }


    @FXML
    private void animationButtonListener(ActionEvent event) {
        if (isAnimation()) {
            setIsAnimation(false);
            animationButton.setText("Animation: OFF");
        } else {
            setIsAnimation(true);
            animationButton.setText("Animation: ON");
        }
    }

    @FXML
    private void skinButtonListener(ActionEvent event) {
        if (isLightSkin.get()) {
            isLightSkin.set(false);
            skinButton.setText("Skin: DARK");
            clearStylesheets();
            setStylesheets("/address/view/DarkTheme.css");
        } else {
            isLightSkin.set(true);
            skinButton.setText("Skin: LIGHT");
            clearStylesheets();
            setStylesheets("/address/view/LightTheme.css");
        }
    }

    private void setStylesheets(String s) {
        this.headLineLabelNinaRow.getStylesheets().add(getClass().getResource(s).toExternalForm());
        this.headerGridPane.getStylesheets().add(getClass().getResource(s).toExternalForm());
        this.openFileButton.getStylesheets().add(getClass().getResource(s).toExternalForm());
        this.startGameButton.getStylesheets().add(getClass().getResource(s).toExternalForm());
        this.animationButton.getStylesheets().add(getClass().getResource(s).toExternalForm());
        this.skinButton.getStylesheets().add(getClass().getResource(s).toExternalForm());
        this.quitGameButton.getStylesheets().add(getClass().getResource(s).toExternalForm());
        this.playersScrollPaneArea.getStylesheets().add(getClass().getResource(s).toExternalForm());
        this.boardGameGridPaneArea.getStylesheets().add(getClass().getResource(s).toExternalForm());
        this.gameBoardBorderPane.getStylesheets().add(getClass().getResource(s).toExternalForm());
        this.gameBoardAnchorPane.getStylesheets().add(getClass().getResource(s).toExternalForm());
        this.blueCircleAnchorPane.getStylesheets().add(getClass().getResource(s).toExternalForm());
        this.blueCircleBorderPane.getStylesheets().add(getClass().getResource(s).toExternalForm());
        this.yellowCircleAnchorPane.getStylesheets().add(getClass().getResource(s).toExternalForm());
        this.yellowCircleBorderPane.getStylesheets().add(getClass().getResource(s).toExternalForm());
        this.greenCircleAnchorPane.getStylesheets().add(getClass().getResource(s).toExternalForm());
        this.greenCircleBorderPane.getStylesheets().add(getClass().getResource(s).toExternalForm());
        this.orangeCircleAnchorPane.getStylesheets().add(getClass().getResource(s).toExternalForm());
        this.orangeCircleBorderPane.getStylesheets().add(getClass().getResource(s).toExternalForm());
        this.purpleCircleAnchorPane.getStylesheets().add(getClass().getResource(s).toExternalForm());
        this.purpleCircleBorderPane.getStylesheets().add(getClass().getResource(s).toExternalForm());
        this.redCircleAnchorPane.getStylesheets().add(getClass().getResource(s).toExternalForm());
        this.redCircleBorderPane.getStylesheets().add(getClass().getResource(s).toExternalForm());
        this.popOutButton.getStylesheets().add(getClass().getResource(s).toExternalForm());
        this.popInButton.getStylesheets().add(getClass().getResource(s).toExternalForm());
        this.gridPanePopInOrOut.getStylesheets().add(getClass().getResource(s).toExternalForm());
        this.blueCirclePlayerNameBoarderPane.getStylesheets().add(getClass().getResource(s).toExternalForm());
        this.yellowCirclePlayerNameBoarderPane.getStylesheets().add(getClass().getResource(s).toExternalForm());
        this.purpleCirclePlayerNameBoarderPane.getStylesheets().add(getClass().getResource(s).toExternalForm());
        this.orangeCirclePlayerNameBoarderPane.getStylesheets().add(getClass().getResource(s).toExternalForm());
        this.greenCirclePlayerNameBoarderPane.getStylesheets().add(getClass().getResource(s).toExternalForm());
        this.redCirclePlayerNameBoarderPane.getStylesheets().add(getClass().getResource(s).toExternalForm());
        this.blueLabelPlayerID.getStylesheets().add(getClass().getResource(s).toExternalForm());
        this.yellowLabelPlayerID.getStylesheets().add(getClass().getResource(s).toExternalForm());
        this.purpleLabelPlayerID.getStylesheets().add(getClass().getResource(s).toExternalForm());
        this.orangeLabelPlayerID.getStylesheets().add(getClass().getResource(s).toExternalForm());
        this.greenLabelPlayerID.getStylesheets().add(getClass().getResource(s).toExternalForm());
        this.redLabelPlayerID.getStylesheets().add(getClass().getResource(s).toExternalForm());
        this.blueLabelPlayerName.getStylesheets().add(getClass().getResource(s).toExternalForm());
        this.yellowLabelPlayerName.getStylesheets().add(getClass().getResource(s).toExternalForm());
        this.purpleLabelPlayerName.getStylesheets().add(getClass().getResource(s).toExternalForm());
        this.orangeLabelPlayerName.getStylesheets().add(getClass().getResource(s).toExternalForm());
        this.greenLabelPlayerName.getStylesheets().add(getClass().getResource(s).toExternalForm());
        this.redLabelPlayerName.getStylesheets().add(getClass().getResource(s).toExternalForm());
        this.blueLabelPlayerTurns.getStylesheets().add(getClass().getResource(s).toExternalForm());
        this.yellowLabelPlayerTurns.getStylesheets().add(getClass().getResource(s).toExternalForm());
        this.purpleLabelPlayerTurns.getStylesheets().add(getClass().getResource(s).toExternalForm());
        this.orangeLabelPlayerTurns.getStylesheets().add(getClass().getResource(s).toExternalForm());
        this.greenLabelPlayerTurns.getStylesheets().add(getClass().getResource(s).toExternalForm());
        this.redLabelPlayerTurns.getStylesheets().add(getClass().getResource(s).toExternalForm());
        this.blueLabelPlayerType.getStylesheets().add(getClass().getResource(s).toExternalForm());
        this.yellowLabelPlayerType.getStylesheets().add(getClass().getResource(s).toExternalForm());
        this.purpleLabelPlayerType.getStylesheets().add(getClass().getResource(s).toExternalForm());
        this.orangeLabelPlayerType.getStylesheets().add(getClass().getResource(s).toExternalForm());
        this.greenLabelPlayerType.getStylesheets().add(getClass().getResource(s).toExternalForm());
        this.redLabelPlayerType.getStylesheets().add(getClass().getResource(s).toExternalForm());
    }

    private void clearStylesheets() {
        this.headLineLabelNinaRow.getStylesheets().clear();
        this.headerGridPane.getStylesheets().clear();
        this.openFileButton.getStylesheets().clear();
        this.startGameButton.getStylesheets().clear();
        this.animationButton.getStylesheets().clear();
        this.skinButton.getStylesheets().clear();
        this.quitGameButton.getStylesheets().clear();
        this.playersScrollPaneArea.getStylesheets().clear();
        this.boardGameGridPaneArea.getStylesheets().clear();
        this.gameBoardBorderPane.getStylesheets().clear();
        this.gameBoardAnchorPane.getStylesheets().clear();
        this.blueCircleAnchorPane.getStylesheets().clear();
        this.blueCircleBorderPane.getStylesheets().clear();
        this.yellowCircleAnchorPane.getStylesheets().clear();
        this.yellowCircleBorderPane.getStylesheets().clear();
        this.greenCircleAnchorPane.getStylesheets().clear();
        this.greenCircleBorderPane.getStylesheets().clear();
        this.orangeCircleAnchorPane.getStylesheets().clear();
        this.orangeCircleBorderPane.getStylesheets().clear();
        this.purpleCircleAnchorPane.getStylesheets().clear();
        this.purpleCircleBorderPane.getStylesheets().clear();
        this.redCircleAnchorPane.getStylesheets().clear();
        this.redCircleBorderPane.getStylesheets().clear();
        this.gridPanePopInOrOut.getStylesheets().clear();
        this.popOutButton.getStylesheets().clear();
        this.popInButton.getStylesheets().clear();
        this.blueCirclePlayerNameBoarderPane.getStylesheets().clear();
        this.yellowCirclePlayerNameBoarderPane.getStylesheets().clear();
        this.purpleCirclePlayerNameBoarderPane.getStylesheets().clear();
        this.orangeCirclePlayerNameBoarderPane.getStylesheets().clear();
        this.greenCirclePlayerNameBoarderPane.getStylesheets().clear();
        this.redCirclePlayerNameBoarderPane.getStylesheets().clear();
        this.blueLabelPlayerID.getStylesheets().clear();
        this.yellowLabelPlayerID.getStylesheets().clear();
        this.purpleLabelPlayerID.getStylesheets().clear();
        this.orangeLabelPlayerID.getStylesheets().clear();
        this.greenLabelPlayerID.getStylesheets().clear();
        this.redLabelPlayerID.getStylesheets().clear();
        this.blueLabelPlayerName.getStylesheets().clear();
        this.yellowLabelPlayerName.getStylesheets().clear();
        this.purpleLabelPlayerName.getStylesheets().clear();
        this.orangeLabelPlayerName.getStylesheets().clear();
        this.greenLabelPlayerName.getStylesheets().clear();
        this.redLabelPlayerName.getStylesheets().clear();
        this.blueLabelPlayerTurns.getStylesheets().clear();
        this.yellowLabelPlayerTurns.getStylesheets().clear();
        this.purpleLabelPlayerTurns.getStylesheets().clear();
        this.orangeLabelPlayerTurns.getStylesheets().clear();
        this.greenLabelPlayerTurns.getStylesheets().clear();
        this.redLabelPlayerTurns.getStylesheets().clear();
        this.blueLabelPlayerType.getStylesheets().clear();
        this.yellowLabelPlayerType.getStylesheets().clear();
        this.purpleLabelPlayerType.getStylesheets().clear();
        this.orangeLabelPlayerType.getStylesheets().clear();
        this.greenLabelPlayerType.getStylesheets().clear();
        this.redLabelPlayerType.getStylesheets().clear();
    }

    // this function get the form back to the starting point where only the OPEN FILE BUTTON is clear for the user
    private void SetFormToStartingPointAsNewForm(){
        openFileButton.setDisable(false);
        openFileButton.setDefaultButton(true);
        boardGameGridPaneArea.setDisable(true);
        playersScrollPaneArea.setDisable(true);
        isGameStarted.set(false);
        blueCirclePlayerNameBoarderPane.getStyleClass().clear();
        yellowCirclePlayerNameBoarderPane.getStyleClass().clear();
        greenCirclePlayerNameBoarderPane.getStyleClass().clear();
        purpleCirclePlayerNameBoarderPane.getStyleClass().clear();
        orangeCirclePlayerNameBoarderPane.getStyleClass().clear();
        redCirclePlayerNameBoarderPane.getStyleClass().clear();
        blueCirclePlayerNameBoarderPane.getStyleClass().add("background");
        yellowCirclePlayerNameBoarderPane.getStyleClass().add("background");
        greenCirclePlayerNameBoarderPane.getStyleClass().add("background");
        purpleCirclePlayerNameBoarderPane.getStyleClass().add("background");
        orangeCirclePlayerNameBoarderPane.getStyleClass().add("background");
        redCirclePlayerNameBoarderPane.getStyleClass().add("background");
        //////////////////////////////////
        // TODO: DELETE ONLY BEFORE SERVING THE GAME, i might gonna use it
        //boardGameGridPaneArea.setVisible(false);
        //playersScrollPaneArea.setVisible(false);
        ////////////////////////////////////

        initialize();
    }


    @FXML
    private void StartGameActionListener(ActionEvent event) {
        engine.startNewGame();
        openFileButton.setDisable(true);
        startGameButton.disableProperty().unbind();
        startGameButton.setDisable(true);
        if (engine.getGame().getVariant() == eGameVariant.Popout) {
            gridPanePopInOrOut.setVisible(true);
        }
        else {
            gridPanePopInOrOut.setVisible(false);
        }
        startGameButton.defaultButtonProperty().setValue(false);
        isGameStarted.set(true);
        board.startBoardOperation();
        getCurrentPlayerBoarderPane(getCurrentPlayer()).getStyleClass().add("active-player-background");
    }

/*/////////////////////////////////////// /

    public SimpleStringProperty gameTargetProperty() {
        return gameTarget;
    }


    public String getGameTarget() {
        return gameTarget.get();
    }



    public void setGameTarget(String gameTarget) {
        this.gameTarget.set(gameTarget);
    }

    private SimpleStringProperty gameTarget = new SimpleStringProperty("");

   *//////////////////////////////


    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public void setMain(Program main) { this.mainApp = main; }
    private Task<File> currentRunningTask;


    @FXML
    void openFileButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open XML file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML Document (.xml)", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
        if (selectedFile == null) {
            return;
        }
        handleXMLFileLoading(selectedFile);
    }

    private void loadPlayersData() {
        for (int i = 0; i < engine.getGame().getPlayerList().size(); i++) {
            playerProperties.get(i).setPlayerName(engine.getGame().getPlayerList().get(i).getPlayerName());
            playerProperties.get(i).setPlayerID(engine.getGame().getPlayerList().get(i).getPlayerID());
            playerProperties.get(i).setPlayerType(engine.getGame().getPlayerList().get(i).getPlayerMode().name());
            playerProperties.get(i).setTurnsPlayed(Integer.toString(engine.getGame().getPlayerList().get(i).getNumOfPlays()));
        }
        updateColors();
    }

    private void updateColors() {
        blueCircle.setVisible(true);
        blueCircle.setFill(Color.DODGERBLUE);
        playerProperties.get(0).setPlayerColor(Color.DODGERBLUE);
        engine.getGame().getPlayerList().get(0).setPlayerColor(Color.DODGERBLUE);
        blueCircleAnchorPane.setVisible(true);
        blueCirclePlayerNameBoarderPane.setVisible(true);

        yellowCircle.setVisible(true);
        yellowCircle.setFill(Color.rgb(236, 255, 31));
        playerProperties.get(1).setPlayerColor(Color.rgb(236, 255, 31));
        engine.getGame().getPlayerList().get(1).setPlayerColor(Color.rgb(236, 255, 31));
        yellowCircleAnchorPane.setVisible(true);
        yellowCirclePlayerNameBoarderPane.setVisible(true);

        if (engine.getGame().getPlayerList().size() >= 3) {
            greenCircle.setVisible(true);
            greenCircle.setFill(Color.rgb(31, 255, 48));
            playerProperties.get(2).setPlayerColor(Color.rgb(31, 255, 48));
            engine.getGame().getPlayerList().get(2).setPlayerColor(Color.rgb(31, 255, 48));
            greenCircleAnchorPane.setVisible(true);
            greenCirclePlayerNameBoarderPane.setVisible(true);
        }
        if (engine.getGame().getPlayerList().size() >= 4) {
            purpleCircle.setVisible(true);
            purpleCircle.setFill(Color.rgb(255, 31, 195));
            playerProperties.get(3).setPlayerColor(Color.rgb(255, 31, 195));
            engine.getGame().getPlayerList().get(3).setPlayerColor(Color.rgb(255, 31, 195));
            purpleCircleAnchorPane.setVisible(true);
            purpleCirclePlayerNameBoarderPane.setVisible(true);

        }
        if (engine.getGame().getPlayerList().size() >= 5) {
            orangeCircle.setVisible(true);
            orangeCircle.setFill(Color.rgb(255, 134, 31));
            playerProperties.get(4).setPlayerColor(Color.rgb(255, 134, 31));
            engine.getGame().getPlayerList().get(4).setPlayerColor(Color.rgb(255, 134, 31));
            orangeCircleAnchorPane.setVisible(true);
            orangeCirclePlayerNameBoarderPane.setVisible(true);
        }
        if (engine.getGame().getPlayerList().size() >= 6) {
            redCircle.setVisible(true);
            redCircle.setFill(Color.rgb(255, 31, 31));
            playerProperties.get(5).setPlayerColor(Color.rgb(255, 31, 31));
            engine.getGame().getPlayerList().get(5).setPlayerColor(Color.rgb(255, 31, 31));
            redCircleAnchorPane.setVisible(true);
            redCirclePlayerNameBoarderPane.setVisible(true);
        }
    }

    private void bindPlayers() {
        blueLabelPlayerName.textProperty().bind(playerProperties.get(0).playerNameProperty());
        yellowLabelPlayerName.textProperty().bind(playerProperties.get(1).playerNameProperty());
        greenLabelPlayerName.textProperty().bind(playerProperties.get(2).playerNameProperty());
        purpleLabelPlayerName.textProperty().bind(playerProperties.get(3).playerNameProperty());
        orangeLabelPlayerName.textProperty().bind(playerProperties.get(4).playerNameProperty());
        redLabelPlayerName.textProperty().bind(playerProperties.get(5).playerNameProperty());

        blueLabelPlayerID.textProperty().bind(playerProperties.get(0).playerIDProperty());
        yellowLabelPlayerID.textProperty().bind(playerProperties.get(1).playerIDProperty());
        greenLabelPlayerID.textProperty().bind(playerProperties.get(2).playerIDProperty());
        purpleLabelPlayerID.textProperty().bind(playerProperties.get(3).playerIDProperty());
        orangeLabelPlayerID.textProperty().bind(playerProperties.get(4).playerIDProperty());
        redLabelPlayerID.textProperty().bind(playerProperties.get(5).playerIDProperty());

        blueLabelPlayerType.textProperty().bind(playerProperties.get(0).playerTypeProperty());
        yellowLabelPlayerType.textProperty().bind(playerProperties.get(1).playerTypeProperty());
        greenLabelPlayerType.textProperty().bind(playerProperties.get(2).playerTypeProperty());
        purpleLabelPlayerType.textProperty().bind(playerProperties.get(3).playerTypeProperty());
        orangeLabelPlayerType.textProperty().bind(playerProperties.get(4).playerTypeProperty());
        redLabelPlayerType.textProperty().bind(playerProperties.get(5).playerTypeProperty());

        blueLabelPlayerTurns.textProperty().bind(playerProperties.get(0).turnsPlayedProperty());
        yellowLabelPlayerTurns.textProperty().bind(playerProperties.get(1).turnsPlayedProperty());
        greenLabelPlayerTurns.textProperty().bind(playerProperties.get(2).turnsPlayedProperty());
        purpleLabelPlayerTurns.textProperty().bind(playerProperties.get(3).turnsPlayedProperty());
        orangeLabelPlayerTurns.textProperty().bind(playerProperties.get(4).turnsPlayedProperty());
        redLabelPlayerTurns.textProperty().bind(playerProperties.get(5).turnsPlayedProperty());
    }

    private void loadBoardFromEngine() {
        board = new BoardGameController(
                engine.getGame().getBoard().getRows(),
                engine.getGame().getBoard().getCols(),
                engine.getGame().getPlayerList().size());
        board.setMainAppController(this);
    }

    private void handleXMLFileLoading(File selectedFile) {
        setEngine(new Engine());
        String absolutePath = selectedFile.getAbsolutePath();
        Task gameCreator = createNewGameFromXMLPath(absolutePath);
        Parent root;
        synchronized (this){
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Program.class.getResource("view/XMLFileRead.fxml"));
                root = loader.load();
                address.view.XMLFileReadController controller = loader.getController();
                controller.setTask(gameCreator);
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Loading XML File");
                stage.setScene(scene);
                stage.show();

                new Thread(gameCreator).start();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private Task createNewGameFromXMLPath(String absoluteXMLFilePath){
        return new Task() {
            @Override
            protected Object call() throws Exception {
                String errorMsg;
                try {
                    this.updateProgress(1, 10);
                    updateMessage("Reading XML...");
                    Thread.sleep(400);
                    this.updateProgress(3, 10);
                    Thread.sleep(300);
                    GameDescriptor g = (GameDescriptor) engine.createGameDescriptor(absoluteXMLFilePath);
                    this.updateProgress(5, 10);
                    updateMessage("Checking Legality...");
                    Thread.sleep(350);
                    this.updateProgress(6, 10);
                    Thread.sleep(100);
                    errorMsg = engine.isLegalDescriptor(g);
                    if (errorMsg == ""){
                        this.updateProgress(7, 10);
                        updateMessage("Creating Game...");
                        Thread.sleep(350);
                        this.updateProgress(8, 10);
                        Thread.sleep(100);
                        engine.setNewGameAfterXMLApproved(g);
                        errorMsg = ConstantMassages.k_XMLFileLoadedSuccessfully;
                        updateProgress(10, 10);
                        updateMessage(errorMsg);
                        Platform.runLater(new Runnable () {
                            @Override
                            public void run() {
                                isFileSelected.set(true);
                                playersScrollPaneArea.setDisable(false);
                                boardGameGridPaneArea.setDisable(false);
                                startGameButton.defaultButtonProperty().setValue(true);
                                openFileButton.defaultButtonProperty().setValue(false);
                                headLineLabelNinaRow.textProperty().bind(engine.headerGameProperty());
                                loadBoardFromEngine();
                                loadPlayersData();
                                loadBoard();

                            }
                        });
                    }
                    else {
                        updateProgress(10, 10);
                        updateMessage(errorMsg);
                    }
                }

                catch (JAXBException e) {
                    updateProgress(10, 10);
                    updateMessage(ConstantMassages.k_LoadFailed);
                }
                catch (FileNotFoundException e) {
                    updateProgress(10, 10);
                    updateMessage(ConstantMassages.k_LoadFailed);
                }
                return true;
            }
        };
    }

    private void loadBoard() {
        gameBoardAnchorPane.getChildren().clear();
        gameBoardAnchorPane.getChildren().add(board.getMainPane());
    }

    public boolean handleTurnPlay(int column, boolean isPopout) {
        boolean turnSucceeded = false;
        this.gameBoardAnchorPane.setDisable(true);
        Player player = getCurrentPlayer();
        if (engine.ExecuteTurn(column, isPopout)) {
            updateNumberOfTurns(player);
            if (engine.getGame().getStatus() == eGameStatus.EndWithWin) {
                handleEndOfGameSituation();
            } else if (engine.getGame().getStatus() == eGameStatus.EndWithDraw) {
                handleEndOfGameSituation();
            }
            turnSucceeded =  true;
        }
        this.gameBoardAnchorPane.setDisable(false);
        return turnSucceeded;
    }

    public void ChangeActivePlayer() {
        Player activePlayer = getCurrentPlayer();
        BorderPane currentPlayerBoarderPane = getCurrentPlayerBoarderPane(activePlayer);
        blueCirclePlayerNameBoarderPane.getStyleClass().clear();
        yellowCirclePlayerNameBoarderPane.getStyleClass().clear();
        greenCirclePlayerNameBoarderPane.getStyleClass().clear();
        purpleCirclePlayerNameBoarderPane.getStyleClass().clear();
        orangeCirclePlayerNameBoarderPane.getStyleClass().clear();
        redCirclePlayerNameBoarderPane.getStyleClass().clear();
        blueCirclePlayerNameBoarderPane.getStyleClass().add("background");
        yellowCirclePlayerNameBoarderPane.getStyleClass().add("background");
        greenCirclePlayerNameBoarderPane.getStyleClass().add("background");
        purpleCirclePlayerNameBoarderPane.getStyleClass().add("background");
        orangeCirclePlayerNameBoarderPane.getStyleClass().add("background");
        redCirclePlayerNameBoarderPane.getStyleClass().add("background");
        currentPlayerBoarderPane.getStyleClass().add("active-player-background");
    }

    private BorderPane getCurrentPlayerBoarderPane(Player activePlayer) {
        BorderPane borderPane = null;
        if (activePlayer.getPlayerID().equals(blueLabelPlayerID.getText())){
            borderPane = blueCirclePlayerNameBoarderPane;
        } else if (activePlayer.getPlayerID().equals(yellowLabelPlayerID.getText())) {
            borderPane = yellowCirclePlayerNameBoarderPane;
        } else if (activePlayer.getPlayerID().equals(greenLabelPlayerID.getText())) {
            borderPane = greenCirclePlayerNameBoarderPane;
        } else if (activePlayer.getPlayerID().equals(orangeLabelPlayerID.getText())) {
            borderPane = orangeCirclePlayerNameBoarderPane;
        } else if (activePlayer.getPlayerID().equals(purpleLabelPlayerID.getText())) {
            borderPane = purpleCirclePlayerNameBoarderPane;
        } else if (activePlayer.getPlayerID().equals(redLabelPlayerID.getText())) {
            borderPane = redCirclePlayerNameBoarderPane;
        }

        return borderPane;
    }

    private void updateNumberOfTurns(Player player) {
        for (PlayerProperties playerProperty : playerProperties) {
            if (playerProperty.getPlayerID() == player.getPlayerID()){
                playerProperty.setTurnsPlayed(Integer.toString(player.getNumOfPlays()));
                break;
            }
        }
    }


    private void handleEndOfGameSituation() {
        handleWinnerOrDrawSituation();
        SetFormToStartingPointAsNewForm();
    }


    public Move handleComputerTurn() {
        Move move = engine.getGame().PlayComputerTurn();
        if (move != null) {
            Platform.runLater(new Runnable () {
                @Override
                public void run() {
                    updateNumberOfTurns(move.getExecutedPlayer());
                    if (engine.getGame().getStatus() == eGameStatus.EndWithWin) {
                        handleEndOfGameSituation();
                    } else if (engine.getGame().getStatus() == eGameStatus.EndWithDraw) {
                        handleEndOfGameSituation();
                    }
                }
            });
            return move;
        }
        else
            return null;
    }

    public boolean isPopUpTurn() {
        return popOutButton.isDefaultButton();
    }


    private void handleWinnerOrDrawSituation() {
        Parent root;
        String winnerFullPathToOpen = "view/winnersForm.fxml";

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Program.class.getResource(winnerFullPathToOpen));
            root = loader.load();
            address.view.endOfGameFormController controller = loader.getController();
            controller.setMainApp(this);
            controller.UpdateWinners();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Game Over");
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}






