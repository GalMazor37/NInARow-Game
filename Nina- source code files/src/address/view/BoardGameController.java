package address.view;

import address.model.Move;
import address.model.eGameStatus;
import address.model.eGameVariant;
import address.model.ePlayerMode;
import address.utils.constants.ConstantMassages;
import address.utils.xsdClass.GameDescriptor;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;



public class BoardGameController {
    private static final int TILE_SIZE = 60;
    private Disc[][] discGrid;
    private Pane discPane = new Pane();
    private int rows;
    private int cols;
    private int numberOfPlayers;
    private MainFormController mainAppController;
    ////////////////////////////////////////////////////
    //TODO
    private SimpleIntegerProperty RowsBoard = new SimpleIntegerProperty();
    private SimpleIntegerProperty ColumnsBoard = new SimpleIntegerProperty();


    public BoardGameController(int rows, int cols, int size) {
        this.rows = rows;
        this.cols = cols;
        this.numberOfPlayers = size;
        this.discGrid = new Disc[rows][cols];
        mainPane = new Pane(createContent());
    }


    public void setMainAppController(MainFormController mainAppController) {
        this.mainAppController = mainAppController;
    }

    private Parent createContent(){
        Pane root = new Pane();
        root.getChildren().add(makeGrid());
        root.getChildren().add(discPane);
        return root;
    }

    public void startBoardOperation() {
        mainPane.getChildren().addAll(makeColumns());
    }

    private Shape makeGrid(){
        Shape shape = new Rectangle((cols +1) * TILE_SIZE, (rows + 1)*TILE_SIZE);
        for (int y=0; y<rows; y++)
        {
            for (int x=0; x<cols; x++){
                Circle circle = new Circle(TILE_SIZE/2);
                circle.setCenterX((TILE_SIZE/2));
                circle.setCenterY(TILE_SIZE/2);
                circle.setTranslateX(x*(TILE_SIZE+5)+TILE_SIZE/4);
                circle.setTranslateY(y*(TILE_SIZE+5)+TILE_SIZE/4);

                shape = Shape.subtract(shape, circle);
            }
        }
        //  Light.Distant light = new Light.Distant();
        //  light.setAzimuth(45.0);
        //  light.setElevation(30.0);
        //  Lighting lighting = new Lighting();
        //  lighting.setLight(light);
        // lighting.setSurfaceScale(5.0);

        //  shape.setEffect(lighting);
        shape.setFill(Color.GREEN);
        return shape;
    }

    private List<Rectangle> makeColumns(){
        List<Rectangle> list = new ArrayList<>();
        for (int i = 0; i < cols; i++){
            Rectangle rect = new Rectangle(TILE_SIZE, (rows + 1)*TILE_SIZE);
            rect.setTranslateX(i*(TILE_SIZE +5) + TILE_SIZE /4);
            rect.setFill(Color.TRANSPARENT);

            rect.setOnMouseEntered(e->rect.setFill(Color.rgb(200,200,50,0.3)));
            rect.setOnMouseExited(e->rect.setFill(Color.TRANSPARENT));
            rect.setOnMouseClicked(e->playTurn(rect));
            list.add(rect);
        }

        return list;
    }

    private void playTurn(Rectangle rect) {
        if (playHumanTurn(rect)){
            while (mainAppController.getEngine().getCurrentPlayer().getPlayerMode() == ePlayerMode.Computer &&
            mainAppController.getEngine().getGame().getStatus() == eGameStatus.InProgress) {
                mainAppController.ChangeActivePlayer();
                Task computerTurn = playComputerTurn();
                new Thread(computerTurn).start();
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Task playComputerTurn() {
        return new Task() {
            @Override
            protected Object call() {
                Disc computerDisc = new Disc(mainAppController.getCurrentDiscColor());
                Move move = mainAppController.handleComputerTurn();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (move != null) {
                            InsertDisc(move.getRow(), move.getColumn(), computerDisc);
                            mainAppController.ChangeActivePlayer();
                        }
                    }
                });
                return false;
            }
        };
    }

    private boolean playHumanTurn(Rectangle rect) {
        boolean turnPlayed = false;
        if (mainAppController.getEngine().getGame().getVariant() == eGameVariant.Popout) {
            if (mainAppController.isPopUpTurn()) {
                turnPlayed = playHumanPopOutTurn(rect);
            } else {
                turnPlayed = playHumanPopInTurn(rect);
            }
        }
        else {
            turnPlayed = playHumanPopInTurn(rect);
        }

        mainAppController.ChangeActivePlayer();
        return turnPlayed;
    }

    private boolean playHumanPopOutTurn(Rectangle rect) {
        boolean turnPlayed = false;
        int column = convertRectToColumn(rect.getTranslateX());
        int row = rows - 1;
        if (mainAppController.handleTurnPlay(column, true)) {
            PopDisc(row, column);
            turnPlayed = true;
        }
        return turnPlayed;

    }

    private boolean playHumanPopInTurn(Rectangle rect) {
        boolean turnPlayed = false;
        int column = convertRectToColumn(rect.getTranslateX());
        Disc disc = new Disc(mainAppController.getCurrentDiscColor());
        int row = mainAppController.getEngine().getGame().nextAvailableRow(column);
        if (mainAppController.handleTurnPlay(column, false)) {
            InsertDisc(row, column, disc);
            turnPlayed = true;
        }
        return turnPlayed;
    }

    public void InsertDisc(int row, int column, Disc disc) {
        discGrid[row][column] = disc;
        discPane.getChildren().add(disc);
        disc.setTranslateX(column * (TILE_SIZE + 5) + TILE_SIZE / 4);
        if (mainAppController.isAnimation()){
            TranslateTransition animation = new TranslateTransition(Duration.seconds(0.5), disc);
            animation.setToY(row * (TILE_SIZE + 5) + TILE_SIZE / 4);
            animation.play();
        }
        else {
        disc.setTranslateY(row * (TILE_SIZE + 5) + TILE_SIZE / 4);
        }
    }

    public void PopDisc(int row, int column){
        discGrid[row][column].setFill(Color.TRANSPARENT);
        discGrid[row][column] = null;
        row--;
        while (discGrid[row][column] != null) {
            discGrid[row + 1][column] = discGrid[row][column];
            discGrid[row][column] = null;
            discGrid[row + 1][column].setTranslateY((row + 1) * (TILE_SIZE + 5) + TILE_SIZE / 4);
            row--;
        }
    }

    private int convertRectToColumn(double translateX) {return (int)((translateX - (TILE_SIZE/4))/(TILE_SIZE+5));}

    private static class Disc extends Circle{
        private Color color;

        public Disc(Color color){
            super(TILE_SIZE/2, color);
            this.color = color;
            setCenterX(TILE_SIZE/2);
            setCenterY(TILE_SIZE/2);
        }
    }

    public Node getMainScene() {
        return mainScene.getRoot();
    }

    public void setMainScene(Scene mainScene) {
        this.mainScene = mainScene;
    }

    private Scene mainScene;
    private Pane mainPane;

    public Pane getMainPane() {
        return mainPane;
    }

//    @Override
//    public void start (Stage stage) throws Exception{
    // stage.setScene(new Scene(createContent()));
    // setMainScene(stage.getScene());
    //stage.show();
    //   }

    //  public static void main (String[] args){
    //      launch(args);
    //  }


    private Stage primaryStage;
    private BorderPane rootLayout;

/*
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    private void handleEditPerson() {
        Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            boolean okClicked = mainApp.showPersonEditDialog(selectedPerson);
            if (okClicked) {
                showPersonDetails(selectedPerson);
            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());


            alert.showAndWait();
        }
    }
    */

}
