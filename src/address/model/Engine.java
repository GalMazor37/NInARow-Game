package address.model;

import address.utils.constants.ConstantMassages;
import address.utils.xsdClass.GameDescriptor;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.paint.Color;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;

public class Engine {

    public SimpleStringProperty gameTargetEngineProperty() {
        return gameTargetEngine;
    }

    public String getGameTargetEngine() {
        return gameTargetEngine.get();
    }

    public void setGameTargetEngine(String gameTargetEngine) {
        this.gameTargetEngine.set(gameTargetEngine);
    }

    private SimpleStringProperty gameTargetEngine = new SimpleStringProperty("N");

    public String getGameVariantEngine() {
        return gameVariantEngine.get();
    }

    public SimpleStringProperty gameVariantEngineProperty() {
        return gameVariantEngine;
    }

    public void setGameVariantEngine(String gameVariantEngine) {
        this.gameVariantEngine.set(gameVariantEngine);
    }


    public String getHeaderGame() {
        return headerGame.get();
    }

    public SimpleStringProperty headerGameProperty() {
        return headerGame;
    }

    private SimpleStringProperty gameVariantEngine = new SimpleStringProperty("");
    private SimpleStringProperty headerGame = new SimpleStringProperty("N in a Row");


    public String getPlayerTypeEngine() {
        return playerTypeEngine.get();
    }

    public SimpleStringProperty playerTypeEngineProperty() {
        return playerTypeEngine;
    }

    public void setPlayerTypeEngine(String playerTypeEngine) {
        this.playerTypeEngine.set(playerTypeEngine);
    }

    private SimpleStringProperty playerTypeEngine = new SimpleStringProperty("");



    /////////////////////////
    private Game game;

    public Engine () {
        //gameTargetEngine.bind(game.gameTargetGameProperty());
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
        Platform.runLater(new Runnable () {
            @Override
            public void run() {
                gameTargetEngine.bind(game.gameTargetGameProperty());
                gameVariantEngine.bind(game.gameVariantGameProperty());
            }
        });
    }


    public boolean gameLoadedSuccessfully() {return game != null;}


    public String isLegalDescriptor(GameDescriptor g) {
        String errorMsg = "";
        boolean legalData = true;
        if (g.getGame().getBoard().getRows() > 50 || g.getGame().getBoard().getRows() < 5) {
            errorMsg += ConstantMassages.k_IllegalRows + "\n";
        }
        if (g.getGame().getBoard().getColumns().intValue() > 30 || g.getGame().getBoard().getColumns().intValue() < 6) {
            errorMsg += ConstantMassages.k_IllegalCols + "\n";
        }
        if ((g.getGame().getTarget().intValue() >= g.getGame().getBoard().getRows() ||
                g.getGame().getTarget().intValue() >= g.getGame().getBoard().getColumns().intValue()) ||
                g.getGame().getTarget().intValue() < 2) {
            errorMsg += ConstantMassages.k_IllegalTarget + "\n";
        }

        if ((g.getGame().getTarget().intValue() >= g.getGame().getBoard().getRows() ||
                g.getGame().getTarget().intValue() >= g.getGame().getBoard().getColumns().intValue()) ||
                g.getGame().getTarget().intValue() < 2) {
            errorMsg += ConstantMassages.k_IllegalTarget + "\n";
        }

        if (g.getPlayers().getPlayer().size() < 2 ||
                g.getPlayers().getPlayer().size() > 6){
            errorMsg += ConstantMassages.k_IllegalNumOfPlayers + "\n";
        }

        for (int i = 0; i < g.getPlayers().getPlayer().size() - 1 && legalData == true ; i++){
            for (int j = i+1; j < g.getPlayers().getPlayer().size() && legalData == true; j++){
                if (g.getPlayers().getPlayer().get(i).getId() ==
                        g.getPlayers().getPlayer().get(j).getId()) {
                    legalData = false;
                    errorMsg += ConstantMassages.k_IllegalIDOfPlayers + "\n";
                }
            }
        }

        if (errorMsg != ""){
            errorMsg = ConstantMassages.k_IllegalXMLContent + "\n" + errorMsg;
        }
        return errorMsg;
    }

    public GameDescriptor createGameDescriptor(String xmlPath) throws JAXBException, FileNotFoundException {
        GameDescriptor g;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(GameDescriptor.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            File file = new File(xmlPath);
            if (file.exists() == false) {

                throw new FileNotFoundException();
            }
            g = (GameDescriptor) jaxbUnmarshaller.unmarshal(file);

        } catch (JAXBException e) {
            throw e;
        }

        return g;
    }

    public void setNewGameAfterXMLApproved(GameDescriptor g) {
        if(g.getGame().getVariant().equals("Regular")){
            this.setGame(new RegularGame(g));
        }
        else if (g.getGame().getVariant().equals("Circular")) {
            this.setGame(new CircularGame(g));
        }
        else {
            this.setGame(new PopoutGame(g));
        }
        headerGame.set(g.getGame().getTarget() + " in a Row - " + g.getGame().getVariant());
    }

    public void startNewGame() {
        game.Start();
    }

    public boolean ExecuteTurn(int column, boolean isPopout) {
        return getGame().ExecuteTurn(column, isPopout);
    }

    public Color getCurrentColor() {
        return game.getCurrentPlayer().getPlayerColor();
    }

    public Player getCurrentPlayer() {
        return game.getCurrentPlayer();
    }
}
