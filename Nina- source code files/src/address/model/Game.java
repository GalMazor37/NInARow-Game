package address.model;

import address.utils.xsdClass.*;
import javafx.beans.property.*;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public abstract class Game implements Serializable{
    private String REGULAR = "REGULAR";
    private String CIRCULAR = "CIRCULAR";
    private String POPOUT = "POPOUT";
    private Board board;
    private LinkedList<Player> playerList;
    private Player currentPlayer;
    private int target;
    private eGameStatus status;
    private LinkedList<Move> movesHistory;
    private eGameVariant variant;
    private Timer timer;
    protected LinkedList<String> winners = null;

    public LinkedList<String> getWinners() {
        return winners;
    }

    public String getREGULAR() {
        return REGULAR;
    }

    public void setREGULAR(String REGULAR) {
        this.REGULAR = REGULAR;
    }

    public String getCIRCULAR() {
        return CIRCULAR;
    }

    public void setCIRCULAR(String CIRCULAR) {
        this.CIRCULAR = CIRCULAR;
    }

    public String getPOPOUT() {
        return POPOUT;
    }

    public void setPOPOUT(String POPOUT) {
        this.POPOUT = POPOUT;
    }

    public void setPlayerList(LinkedList<Player> playerList) {
        this.playerList = playerList;
    }

    public void setMovesHistory(LinkedList<Move> movesHistory) {
        this.movesHistory = movesHistory;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

///////////////////////////////////////////////////////////////////////

    public String getGameTargetGame() {
        return gameTargetGame.get();
    }

    public SimpleStringProperty gameTargetGameProperty() {
        return gameTargetGame;
    }

    public void setGameTargetGame(String gameTargetGame) {
        this.gameTargetGame.set(gameTargetGame);
    }

    private SimpleStringProperty gameTargetGame = new SimpleStringProperty("");
    private SimpleStringProperty gameVariantGame = new SimpleStringProperty("");

    public String getGameVariantGame() {
        return gameVariantGame.get();
    }

    public SimpleStringProperty gameVariantGameProperty() {
        return gameVariantGame;
    }

    public void setGameVariantGame(String gameVariantGame) {
        this.gameVariantGame.set(gameVariantGame);
    }


    //////////////////////////////////////////////////////////////



    public Game(GameDescriptor g) {
        int rows = g.getGame().getBoard().getRows();
        int columns = g.getGame().getBoard().getColumns().intValue();
        board = new Board (rows, columns);
        status = eGameStatus.NotStarted;
        target = g.getGame().getTarget().intValue();
        createPlayersList(g);
        variant = convertFromStringToVariant(g.getGame().getVariant());
        movesHistory = new LinkedList<>();
        ////////////////////////////////////////////////////////////////
        gameTargetGame.set(Integer.toString(target));
        gameVariantGame.set(variant.name());
        //////////////////////////////////////////////////////////////
    }



    private eGameVariant convertFromStringToVariant(String variant) {
        eGameVariant returnedVariant;
        variant = variant.toUpperCase();
        if(variant.equals(REGULAR)){
            returnedVariant = eGameVariant.Regular;
        }
        else if(variant.equals(CIRCULAR))
        {
            returnedVariant = eGameVariant.Circular;
        }
        else {
            returnedVariant = eGameVariant.Popout;
        }

        return returnedVariant;
    }

    private void createPlayersList(GameDescriptor g) {
        playerList = new LinkedList<>();
        Random rand = new Random();
        for (address.utils.xsdClass.Player player : g.getPlayers().getPlayer()){
            playerList.add(new Player(player));
        }
        currentPlayer = null;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }


    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public eGameStatus getStatus() {
        return status;
    }

    public void setStatus(eGameStatus status) {
        this.status = status;
    }

    public LinkedList<Move> getMovesHistory() {
        return movesHistory;
    }

    public Timer getTimer() {
        return timer;
    }

    public eGameVariant getVariant() {
        return variant;
    }

    public void setVariant(eGameVariant variant) {
        this.variant = variant;
    }

    public void PlayAMove(int chosenColumn, boolean isPopout) {
        InsertDiscToColumn(chosenColumn);
        handleEndOfTurn();
    }

    public void InsertDiscToColumn(int chosenColumn) {
        int row = endOfColumn(chosenColumn);
        Disc insertedDisc = new Disc (currentPlayer, new Position(row, chosenColumn));
        board.setDisc(row, chosenColumn, insertedDisc);
        currentPlayer.getDiscList().add(insertedDisc);
        currentPlayer.incNumOfPlays();
        Move executedMove = new Move (currentPlayer, row, chosenColumn, false);
        movesHistory.add(executedMove);
    }

    private int endOfColumn(int chosenColumn) {
        int i = 0;
        boolean reached = false;
        while (i < board.getRows() && !reached)
        {
           if (board.getDisc(i, chosenColumn) == null) { i++; }
           else { reached = true; }
        } return i - 1;
    }

    public void SwapPlayers() {
        Player firstPlayer = playerList.removeFirst();
        playerList.addLast(firstPlayer);
        currentPlayer = playerList.getFirst();
    }

    public LinkedList<String> CheckIfWinAchieved() {
        LinkedList<String> winner = new LinkedList<String>();
        Disc insertedDisc = currentPlayer.getDiscList().get(currentPlayer.getDiscList().size() - 1);
        if (checkNinaRow(insertedDisc, new Direction().new Horizontal()) ||
                checkNinaRow(insertedDisc, new Direction().new Vertical()) ||
                checkNinaRow(insertedDisc, new Direction().new FirstObliqueLineStartDownLeft()) ||
                checkNinaRow(insertedDisc, new Direction().new SecondObliqueLineStartDownRight())) {
            winner.add(insertedDisc.getPlayer().getPlayerName());
            this.status = eGameStatus.EndWithWin;
        }

        return winner;
    }

    public boolean checkNinaRow(Disc insertedDiscToCompare, Direction direction) {
        boolean weGotNinaInARow;
        int targetInARow = 1;
        targetInARow += countOccupiedSquares(insertedDiscToCompare, direction.getFirstX(), direction.getFirstY());
        weGotNinaInARow = checkIfWeGotTargetInARow(targetInARow);
        if(!weGotNinaInARow){
            targetInARow += countOccupiedSquares(insertedDiscToCompare, direction.getSecondX(), direction.getSecondY());
            weGotNinaInARow = checkIfWeGotTargetInARow(targetInARow);
        }

        return weGotNinaInARow;
    }

    public int countOccupiedSquares(Disc insertedDiscToCompare, int addToRow, int addToColumn )
    {
        int tempXPosition = insertedDiscToCompare.getPosition().getRow();
        int tempYPosition = insertedDiscToCompare.getPosition().getCol();
        boolean stillCurrentPlayerDiscSign = true;
        int countedMatchSigns = 0;
        tempXPosition += addToRow;
        tempYPosition += addToColumn;
        while (isInRange(tempXPosition, tempYPosition) && stillCurrentPlayerDiscSign) {
            if (board.getDisc(tempXPosition, tempYPosition) != null)
            {
                if (board.getDisc(tempXPosition,tempYPosition).getPlayer() == insertedDiscToCompare.getPlayer()){
                    countedMatchSigns++;
                    tempXPosition += addToRow;
                    tempYPosition += addToColumn;
                }
                else
                    stillCurrentPlayerDiscSign = false;
            }
            else{
                stillCurrentPlayerDiscSign = false;
            }
        }

        return countedMatchSigns;
    }

    private boolean checkIfWeGotTargetInARow(int targetInARow) {
        if (targetInARow >= target){
            return true;
        }
        else
            return false;
    }

    public boolean isInRange(int xPositionCur, int yPositionCur)
    {
        if(xPositionCur < board.getRows() && xPositionCur >= 0 && yPositionCur >= 0 && yPositionCur <board.getCols())
        {
            return true;
        }
        return false;
    }

    public boolean CheckIfBoardFulled() {
        for (int i = 0; i < board.getCols() ; i ++)
        {
            if (board.IsColumnVacant(i))
            {
                return false;
            }
        }

        return true;
    }

    public Move PlayComputerTurn() {
        boolean turnPlayed = false;
        Random randomCol = new Random();
        int selectedColumn;

        do {
            selectedColumn = randomCol.nextInt(board.getCols());
            if (board.IsColumnVacant(selectedColumn)){
                turnPlayed = true;
                PlayAMove(selectedColumn, false);
                return movesHistory.getLast();
            }
        }while(!turnPlayed);

        return null;
    }

    public void UndoMove(Move moveToRemove) {
        Disc discToRemove = board.getDisc(moveToRemove.getRow(), moveToRemove.getColumn());
        moveToRemove.getExecutedPlayer().RemoveDisc(discToRemove);
        board.RemoveDiscFromColumn(moveToRemove.getRow(), moveToRemove.getColumn());
        movesHistory.remove(moveToRemove);
        SwapPlayers();
    }

    public void StartTimer() {
        timer = new Timer();
    }

    protected void handleEndOfTurn() {
        if (!CheckIfWinAchieved().isEmpty()){
            setStatus(eGameStatus.EndWithWin);
            winners = CheckIfWinAchieved();
        } else if (CheckIfBoardFulled()){
            setStatus(eGameStatus.EndWithDraw);
        } else{
            SwapPlayers();
        }
    }

    public int nextAvailableRow(int column){
        return endOfColumn(column);
    }

    public boolean ExecuteTurn(int column, boolean isPopout)
    {
        boolean isTurnExecuted = false;
        if (isPopout) {
            if (isColumnNotEmpty(column))
            {
                if (isPopoutDiscMatchToPlayer(column)){
                    PlayAMove(column, isPopout);
                    isTurnExecuted = true;
                }
            }
        }
        else  {
            if (isColumnVacant(column)) {
                PlayAMove(column, isPopout);
                isTurnExecuted = true;
            }
        }

        return isTurnExecuted;
    }

    private boolean isPopoutDiscMatchToPlayer(int column) {
        return board.getLastDiscInColumn(column).getPlayer() == this.getCurrentPlayer();
    }

    private boolean isColumnNotEmpty(int column) { return endOfColumn(column) != board.getRows() - 1;}

    private boolean isColumnVacant(int column) {
        return endOfColumn(column) >= 0;
    }

    public void Start() {
        setCurrentPlayer(getPlayerList().get(0));
        setStatus(eGameStatus.InProgress);
    }
}

