package controller;


import com.sun.source.tree.WhileLoopTree;
import listener.GameListener;
import model.*;
import Exception.*;
import view.CellComponent;
import view.ChessComponent;
import view.ChessboardComponent;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

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

    private int turnIndex;

    private int winID;
    public ChessboardComponent getView() {
        return view;
    }

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
        turnIndex = 0;
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

    private void AfterPlayerAction() {
        win();
        selectedPoint = null;
        swapColor();
        view.repaint();

        // 若悔棋之后再动，则遗忘未来的步数
        if (turnIndex == history.size() - 1)
            while (history.size() - 1 > turnIndex) history.remove(history.size() - 1);

        history.add(model.getClonedGrid());
        turnIndex += 1;

        try{
            Save("./resource/auto_save.txt");
        } catch (IOException e) {
            System.out.println("自动存档失败 ");
        }
    }

    /// <summary>
    /// check if someone win the game
    /// </summary>
    /// <returns>0:no one win, 1:Player1 win, 2:Player2 win</returns>
    public int checkWin(){
        win();
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
        turnIndex = 0;
    }

    public void LoadLastStep(){
        if (turnIndex <= 0) return;
        turnIndex -= 1;
        LoadHistory(turnIndex);
        currentPlayer = PlayerColor.values()[(turnIndex) % 2];
        view.initiateChessComponent(model);
        view.repaint();
    }

    public void LoadNextStep(){
        if (turnIndex >= history.size()-1) return;
        turnIndex += 1;
        LoadHistory(turnIndex);
        currentPlayer = PlayerColor.values()[(turnIndex) % 2];
        view.initiateChessComponent(model);
        view.repaint();
    }

    public void Load(String path) throws IOException, WrongChessException, NoFileThereException, WrongFormatException, WrongChessBoardSizeException {
        if (!path.endsWith(".txt")) throw new WrongFormatException();
        File f = new File(path);
        if (!f.exists()) throw new NoFileThereException();

        Scanner input1 = new Scanner(f);
        CheckSaveSize(input1);

        Scanner input = new Scanner(f);
        StringToSave(input);

        LoadHistory(turnIndex);
        System.out.println("成功加载：" + path);
        view.initiateChessComponent(model);
        view.repaint();

    }

    private void CheckSaveSize(Scanner input) throws WrongChessBoardSizeException {
        int m = 0;
        while (input.hasNext()){
            int n = input.nextLine().length();
            System.out.println(n + " " + m);
            if (n != 0 && n != 3*7) throw new WrongChessBoardSizeException();
            if (n==0){
                if (m != 9) throw new WrongChessBoardSizeException();
                else m = 0;
            }
            else m += 1;
        }
        System.out.println( m);
        if (m != 9 && m != 0) throw new WrongChessBoardSizeException();
    }

    public void Save(String path) throws IOException {
        String saveText = SavetoString();

        BufferedWriter bw = new BufferedWriter(new FileWriter(path));
        bw.write(saveText);
        bw.close();
        System.out.println("成功保存在" + path);
    }

    public void StringToSave(Scanner input) throws WrongChessException {
        ArrayList<Cell[][]> result = new ArrayList<>();

        while (input.hasNext()){
            Cell[][] g = new Cell[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];
            for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
                for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                    int id = input.nextInt();
                    g[i][j] = new Cell();
                    if (id % 20 > 8 || id < 0 || id > 28) throw new WrongChessException();
                    if (id != 0){
                        g[i][j].setPiece(new ChessPiece(PlayerColor.values()[id / 20], ChessPiece.PieceType.values()[id % 20]));
                    }
                }
            }
            result.add(g);
        }

        history = result;

        turnIndex = history.size() - 1;
        currentPlayer = PlayerColor.values()[turnIndex % 2];

    }

    public String StringToSave(String Savetext){
        // TODO: 读取存档
        return "";
    }

    public String SavetoString(){
        StringBuilder result = new StringBuilder();
        //result.append(currentPlayer.toString()).append("\n");
        for (Cell[][] time_slice: history){
            for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
                for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                    if (time_slice[i][j].getPiece() == null) result.append("00");
                    else {
                        int id = time_slice[i][j].getPiece().getType().ordinal() + 20*time_slice[i][j].getPiece().getOwner().ordinal();
                        result.append("%02d".formatted(id));
                    }

                    result.append(' ');
                }
                result.append("\n");
            }
            result.append("\n");
        }
        return result.toString();
    }

    public int getTurnIndex(){return turnIndex;}

    public void LoadHistory(int index){
        model.LoadGrid(history.get(index));
        turnIndex = index;
        selectedPoint = null;
    }

    public ChessboardPoint getLastStepSrc(){
        if (turnIndex <= 0) return null;
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                if (history.get(turnIndex - 1)[i][j].getPiece() != null && history.get(turnIndex)[i][j].getPiece() == null)
                    return new ChessboardPoint(i,j);
            }
        }
        return null;
    }

    public ChessboardPoint getLastStepDest(){
        if (turnIndex <= 0) return null;
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                if (
                        history.get(turnIndex)[i][j].getPiece() != null &&(
                        history.get(turnIndex - 1)[i][j].getPiece() == null ||
                        history.get(turnIndex - 1)[i][j].getPiece().getOwner() != history.get(turnIndex)[i][j].getPiece().getOwner() ||
                        history.get(turnIndex - 1)[i][j].getPiece().getType() != history.get(turnIndex)[i][j].getPiece().getType())
                )
                    return new ChessboardPoint(i,j);
            }
        }
        return null;
    }
}
