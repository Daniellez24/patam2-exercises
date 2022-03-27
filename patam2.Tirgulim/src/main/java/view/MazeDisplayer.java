package view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

// this class defines the presentation
public class MazeDisplayer extends Canvas {

    // 1 = wall, 0= empty square in the maze
    int[][] mazeData;

    // we can put values to a StringProperty object in the fxml file
    private StringProperty wallFileName;

    // add a character to the maze, with its col and row position
    int cCol;
    int cRow;

    public MazeDisplayer() {
        wallFileName = new SimpleStringProperty();
        cCol = 0;
        cRow = 1;
    }

    public void setCharacterPosition(int row, int col) {
        this.cRow = row;
        this.cCol = col;
        redraw();
    }

    public int getcCol() {
        return cCol;
    }

    public int getcRow() {
        return cRow;
    }

    public String getWallFileName() {
        return wallFileName.get();
    }

    public void setWallFileName(String wallFileName) {
        this.wallFileName.set(wallFileName);
    }

    public void setMazeData(int[][] mazeData) {
        this.mazeData = mazeData;
        redraw();
    }

    // re draw the maze everytime it is changed
    public void redraw(){
        if(mazeData != null){
            // get size of the canvas
            double W = getWidth();
            double H = getHeight();
            // w,h are relative width and height of each cell in the maze (relative to W,H)
            double w,h;
            w =  W/ mazeData[0].length;
            h =  H/mazeData.length;

            GraphicsContext gc = getGraphicsContext2D();
            Image wall = null;
            try {
                wall = new Image(new FileInputStream(wallFileName.get()));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            // clear all the maze, and redraw it so the Character Oval will be deleted from its previous position
            // and redrawn in the new position
            gc.clearRect(0,0,W,H);

            // i = lines, j = columns
            for (int i = 0; i < mazeData.length; i++) {
                for (int j = 0; j < mazeData[i].length; j++) {
                    if(mazeData[i][j] != 0){
                        if(wall == null){
                            // fillRect fills rectangles with the given measurements, the default fill color is black
                            gc.fillRect(j*w,i*h,w,h);
                        }
                        else{
                            // paints the walls with the wall Image, if it's not null
                            gc.drawImage(wall, j*w,i*h,w,h);
                        }

                    }
                }
            }
            // paint Character
            gc.setFill(Color.RED);
            gc.fillOval(cCol*w, cRow*h, w, h);

        }
    }
}

