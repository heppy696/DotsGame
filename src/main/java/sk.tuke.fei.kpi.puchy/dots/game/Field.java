package sk.tuke.fei.kpi.puchy.dots.game;

import sk.tuke.fei.kpi.puchy.dots.enums.DotColor;
import sk.tuke.fei.kpi.puchy.dots.enums.GameState;

public class Field {
    private GameState state = GameState.PLAYING;
    private final int rowCount;
    private final int columnCount;
    public Dot[][] dots;
    public Field(int rows, int columns) {
        this.rowCount = rows;
        this.columnCount = columns;
        dots = new Dot[rowCount][columnCount];
        generateDots();
    }

    private void generateDots() {
        for (int row = 0; row < rowCount; row++) {
            for (int column = 0; column < columnCount; column++) {
                if (dots[row][column] == null) {
                    dots[row][column] = new Dot(row, column);
                }
            }
        }
    }

    public void ChangeDot(char firstX, int firstY, char secondX, int secondY){
        //v pripade pohybu po riadku, pismenko zostava nezmenene
        if(firstX == secondX){
            for(int index = firstY-48; index <= secondY-48; index++){
                dots[firstX-65][index] = new Dot(firstX-65, index);
            }
        }
        else if(firstY == secondY){
            for(int index = firstX-65; index <= secondX-65; index++){
                dots[index][firstY-48] = new Dot(index, firstY-48);
            }
        }
    }


    public GameState getState() {
        return state;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public Dot getDot(int row, int column){
        return dots[row][column];
    }





}
