package address.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.paint.Color;

public class PlayerProperties {
    private SimpleStringProperty playerName = new SimpleStringProperty();
    private SimpleStringProperty  playerID = new SimpleStringProperty();
    private SimpleStringProperty  playerType = new SimpleStringProperty();
    private SimpleStringProperty  turnsPlayed = new SimpleStringProperty();
    private Color playerColor;

    public PlayerProperties(String playerName, String playerID, String playerType, String turnsPlayed, Color color) {
        this.playerName.set(playerName);
        this.playerID.set(playerID);
        this.playerType.set(playerType);
        this.turnsPlayed.set(turnsPlayed);
        this.playerColor = color;
    }

    public String getPlayerName() {
        return playerName.get();
    }

    public SimpleStringProperty playerNameProperty() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName.set(playerName);
    }

    public String getPlayerID() {
        return playerID.get();
    }

    public SimpleStringProperty playerIDProperty() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID.set(playerID);
    }

    public String getPlayerType() {
        return playerType.get();
    }

    public SimpleStringProperty playerTypeProperty() {
        return playerType;
    }

    public void setPlayerType(String playerType) {
        this.playerType.set(playerType);
    }

    public String getTurnsPlayed() {
        return turnsPlayed.get();
    }

    public SimpleStringProperty turnsPlayedProperty() {
        return turnsPlayed;
    }

    public void setTurnsPlayed(String turnsPlayed) {
        this.turnsPlayed.set(turnsPlayed);
    }

    public Color getPlayerColor() {
        return playerColor;
    }

    public void setPlayerColor(Color playerColor) {
        this.playerColor = playerColor;
    }
}
