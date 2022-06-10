package sk.tuke.fei.kpi.puchy.dots.game;

import sk.tuke.fei.kpi.puchy.dots.enums.DotColor;
import sk.tuke.fei.kpi.puchy.dots.enums.DotState;

import java.util.Random;

public class Dot {
    private DotState state = DotState.UNUSED;
    private DotColor color;
    private int coordinateX = 0;
    private int coordinateY = 0;

    public Dot(int coordinateX, int coordinateY) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        //nahodna farba Dotu
        DotColor[] values = DotColor.values();
        int length = values.length;
        int randIndex = new Random().nextInt(length);
        this.color = values[randIndex];
    }

    public int getCoordinateX() {
        return coordinateX;
    }

    public int getCoordinateY() {
        return coordinateY;
    }

    public DotColor getColor() {
        return color;
    }

    public DotState getState() {
        return state;
    }

    void setState(DotState state) {
        this.state = state;
    }

}
