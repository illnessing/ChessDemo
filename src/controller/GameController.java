package controller;


import listener.GameListener;
import model.*;
import netscape.javascript.JSObject;
import view.CellComponent;
import view.ChessComponent;
import view.ChessboardComponent;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Controller is the connection between model and view,
 * when a Controller receive a request from a view, the Controller
 * analyzes and then hands over to the model for processing
 * [in this demo the request methods are onPlayerClickCell() and onPlayerClickChessPiece()]
 *
*/
public class GameController implements GameListener {


    private Chessboard model;
    private ChessboardComponent view;
    private PlayerColor currentPlayer;

    // Record whether there is a selected piece before
    private ChessboardPoint selectedPoint;

    // To restore the history grid
    private ArrayList<Cell[][]> history;

    private int winID;

    public GameController(ChessboardComponent view, Chessboard model) {
        this.view = view;
        this.model = model;
        this.currentPlayer = PlayerColor.BLUE;
        this.winID = 0;

        view.registerController(this);
        initialize();
        view.initiateChessComponent(model);
        view.repaint();

        history = new ArrayList<>();
        history.add(model.getClonedGrid());
    }

    private void initialize() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {

            }
        }
    }

    // after a valid move swap the player
    private void swapColor() {
        currentPlayer = currentPlayer == PlayerColor.BLUE ? PlayerColor.RED : PlayerColor.BLUE;
    }

    public PlayerColor getCurrentPlayer(){return currentPlayer;}

    public ChessboardPoint getSelectedPoint(){return selectedPoint;}

    private boolean win() {
        // If someone cannot move, they lose
        if (currentPlayer == PlayerColor.BLUE){
            boolean flag = false;
            // try to move every Piece
            for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
                for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                    if (model.getGrid()[i][j].getPiece() != null && model.getGrid()[i][j].getPiece().getOwner() == PlayerColor.BLUE){
                        for (int k = 0; k < Constant.CHESSBOARD_ROW_SIZE.getNum(); k++) {
                            for (int l = 0; l < Constant.CHESSBOARD_COL_SIZE.getNum(); l++) {
                                if (isValidMove(new ChessboardPoint(i,j), new ChessboardPoint(k,l)) || isValidCapture(new ChessboardPoint(i,j), new ChessboardPoint(k,l))) flag = true;
                                if (flag) break;
                            }
                            if (flag) break;
                        }
                        if (flag) break;
                    }
                }
                if (flag) break;
            }
            if (!flag) {
                winID = 2;
                return true;
            }
        }
        if (currentPlayer == PlayerColor.RED){
            boolean flag = false;
            // try to move every Piece
            for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
                for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                    if (model.getGrid()[i][j].getPiece() != null && model.getGrid()[i][j].getPiece().getOwner() == PlayerColor.BLUE){
                        for (int k = 0; k < Constant.CHESSBOARD_ROW_SIZE.getNum(); k++) {
                            for (int l = 0; l < Constant.CHESSBOARD_COL_SIZE.getNum(); l++) {
                                if (isValidMove(new ChessboardPoint(i,j), new ChessboardPoint(k,l)) || isValidCapture(new ChessboardPoint(i,j), new ChessboardPoint(k,l))) flag = true;
                                if (flag) break;
                            }
                            if (flag) break;
                        }
                        if (flag) break;
                    }
                }
                if (flag) break;
            }
            if (!flag) {
                winID = 1;
                return true;
            }
        }


        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                if (model.getGrid()[i][j].getCellType() == CellType.BlueDen && model.getGrid()[i][j].getPiece() != null) {
                    winID = 2;
                    return true;
                }
                if (model.getGrid()[i][j].getCellType() == CellType.RedDen && model.getGrid()[i][j].getPiece() != null) {
                    winID = 1;
                    return true;
                }
            }
        }
        return false;
    }


    // click an empty cell
    @Override
    public void onPlayerClickCell(ChessboardPoint point, CellComponent component) {
        if (win()) return;
        if (selectedPoint != null && model.isValidMove(selectedPoint, point)) {
            model.moveChessPiece(selectedPoint, point);
            view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
            AfterPlayerAction();
            // TODO: if the chess enter Dens or Traps and so on
        }
    }

    // click a cell with a chess
    @Override
    public void onPlayerClickChessPiece(ChessboardPoint point, ChessComponent component) {
        if (win()) return;
        if (selectedPoint == null) {
            if (model.getChessPieceOwner(point).equals(currentPlayer)) {
                selectedPoint = point;
                component.setSelected(true);
                component.repaint();
            }
        } else if (selectedPoint.equals(point)) {
            selectedPoint = null;
            component.setSelected(false);
            component.repaint();
        } else if (model.isValidCapture(selectedPoint, point)) {
            // capture
            model.captureChessPiece(selectedPoint, point);
            view.removeChessComponentAtGrid(point);
            view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
            AfterPlayerAction();
        }
    }

    private void AfterPlayerAction(){
        selectedPoint = null;
        swapColor();
        view.repaint();

        history.add(model.getClonedGrid());
    }

    /// <summary>
    /// check if someone win the game
    /// </summary>
    /// <returns>0:no one win, 1:Player1 win, 2:Player2 win</returns>
    public int checkWin(){
        return winID;
        //throw new IllegalArgumentException("I don't know who is the winner. May the winner is audience!");
    }

    public boolean isValidMove(ChessboardPoint src, ChessboardPoint dest){
        return model.isValidMove(src,dest);
    }

    public boolean isValidCapture(ChessboardPoint src, ChessboardPoint dest){
        return model.isValidCapture(src,dest);
    }

    public void ReStart(){
        //Restart the game
        model.initialize();
        this.currentPlayer = PlayerColor.BLUE;
        this.winID = 0;
        selectedPoint = null;

        //view.registerController(this);
        initialize();
        view.initiateChessComponent(model);
        view.repaint();

        history = new ArrayList<>();
        history.add(model.getGrid().clone());
    }

    public void Save(String path) throws IOException {
        String saveText = SavetoString();

        BufferedWriter bw = new BufferedWriter(new FileWriter(path));
        bw.write(saveText);
        bw.close();
        System.out.println("保存成功");
    }

    public String StringToSave(String Savetext){
        // TODO: 读取存档
        return "";
    }

    public String SavetoString(){
        StringBuilder result = new StringBuilder();
        result.append(currentPlayer.toString()).append("\n");
        for (Cell[][] time_slice: history){
            for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
                for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                    if (time_slice[i][j].getPiece() == null) result.append("0");
                    else result.append(time_slice[i][j].getPiece().getType().ordinal() + 20*time_slice[i][j].getPiece().getOwner().ordinal());

                    result.append(' ');
                }
                result.append("\n");
            }
            result.append("\n");
        }
        return result.toString();
    }
}
