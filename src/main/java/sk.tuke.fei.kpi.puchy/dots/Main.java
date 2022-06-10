package sk.tuke.fei.kpi.puchy.dots;

import sk.tuke.fei.kpi.puchy.dots.game.ConsoleUI;
import sk.tuke.fei.kpi.puchy.dots.game.Field;

public class Main {
    public static void main(String[] args) {
        Field field = new Field(6,6);
        ConsoleUI konzolicka = new ConsoleUI(field);
        konzolicka.play();
    }
}
