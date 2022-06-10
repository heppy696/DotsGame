package sk.tuke.fei.kpi.puchy.dots.game;

import sk.tuke.fei.kpi.puchy.dots.enums.Colors;
import sk.tuke.fei.kpi.puchy.dots.enums.GameState;

public class Gameplay {
    //Class gameplay handles the whole gameplay and game proceesing
    char firstX;
    int firstY;
    char secondX;
    int secondY;

    public void setField(Field field) {
        this.field = field;
    }

    Field field;
    private int movesLeft = 1;

    private final int startingNumberOfMoves = movesLeft;
    private GameState state = GameState.NOTPLAYING;
    private int score = 0;



    public Gameplay() {}

    public void coordinatesCheck() {
        if(firstY - secondY != 0 && firstX - secondX != 0){
            System.out.print(Colors.RED_BOLD_BRIGHT + "OUT OF RANGE, REPEAT YOUR MOVE!");
            return;
        }
        else if(firstY - secondY == 0 && firstX - secondX == 0){
            System.out.print(Colors.RED_BOLD_BRIGHT + "OUT OF RANGE, REPEAT YOUR MOVE!");
            return;
        }
        //overenie ak by hrač vymenil suradnice
        if(firstX == secondX && firstY > secondY){
            int temp;
            temp = firstY;
            firstY = secondY;
            secondY = temp;
        }
        else if(firstX-65 > secondX-65 && firstY == secondY){
            char temp;
            temp = firstX;
            firstX = secondX;
            secondX = temp;
        }
        bindColors();
    }

    private void bindColors() {
        boolean areTheyTheSameColor = false;
        //v pripade pohybu po riadku, pismenko zostava nezmenene
        if(firstX == secondX){
            for(int move = firstY; move < secondY; move++){
                //podmienky !! kontroluje ci sa nachadzaju vedla seba podla inputu dots rovnakej farby
                if(field.getDot((int)firstX-65, (int)move-48).getColor() == field.getDot((int)firstX-65, (int)((move+1)-48)).getColor()){
                    areTheyTheSameColor = true;
                }
                else{
                    areTheyTheSameColor = false;
                }
            }
            if(areTheyTheSameColor){
                addToScoreDependingOnHowManyDotsConnected(((secondY-48)-(firstY-48))+1);
                field.ChangeDot(firstX, firstY, secondX, secondY);
            }
            else{
                movesLeft--;
            }
        }
        //pohyb po stlpcoch
        else if(firstY == secondY){
            for(int move = firstX; move < secondX; move++){
                //podmienky !! kontroluje ci sa nachadzaju vedla seba podla inputu dots rovnakej farby
                if(field.getDot((int)move-65, (int)firstY-48).getColor() == field.getDot((int)(move+1)-65, secondY-48).getColor()){
                    areTheyTheSameColor = true;
                }
                else{
                    areTheyTheSameColor = false;
                }
            }
            if(areTheyTheSameColor){
                //System.out.print(Colors.GREEN_BOLD_BRIGHT + " PERFECT !");
                addToScoreDependingOnHowManyDotsConnected(((secondX-65)-(firstX-65))+1);
                field.ChangeDot(firstX, firstY, secondX, secondY);
            }
            else{
                movesLeft--;
            }
        }
    }

    public void addToScoreDependingOnHowManyDotsConnected(int numberOfDotsConnected) {
        switch (numberOfDotsConnected){
            case 2:
                score = score + 2;
                break;
            case 3:
                score = score + 4;
                break;
            case 4:
                score = score + 8;
                break;
            case 5:
                score = score + 16;
                break;
            case 6:
                score = score + 32;
                break;
        }
        movesLeft--;
    }


    public int getScore() {
        return score;
    }
    public int getMovesLeft() {
        return movesLeft;
    }

    public void printMovesLeft() {
        System.out.print(Colors.CYAN_BOLD_BRIGHT + "Your moves left: " + movesLeft + Colors.RESET);
    }

    public void printScore() {
        System.out.print(Colors.CYAN_BOLD_BRIGHT + "Your score: " + score + Colors.RESET);
        System.out.print("");
    }

    public void setFirstX(char firstX) {
        this.firstX = firstX;
    }

    public void setFirstY(int firstY) {
        this.firstY = firstY;
    }

    public void setSecondX(char secondX) {
        this.secondX = secondX;
    }

    public void setSecondY(int secondY) {
        this.secondY = secondY;
    }

    public void setCoordinates(char firstX, int firstY, char secondX, int secondY){
        this.firstX = firstX;
        this.firstY = firstY;
        this.secondX = secondX;
        this.secondY = secondY;
    }

    public boolean checkIfEnoughMovesAreLeft() {
        if(movesLeft < 1){
            return false;
        }
        return true;
    }

    public int getStartingNumberOfMoves() {
        return startingNumberOfMoves;
    }
}
