package sk.tuke.fei.kpi.puchy.dots.game;

import org.springframework.beans.factory.annotation.Autowired;
import sk.tuke.fei.kpi.puchy.dots.entity.Comment;
import sk.tuke.fei.kpi.puchy.dots.entity.Rating;
import sk.tuke.fei.kpi.puchy.dots.entity.Score;
import sk.tuke.fei.kpi.puchy.dots.enums.Colors;
import sk.tuke.fei.kpi.puchy.dots.enums.GameState;
import sk.tuke.fei.kpi.puchy.dots.service.Comment.CommentService;
import sk.tuke.fei.kpi.puchy.dots.service.Comment.CommentServiceJDBC;
import sk.tuke.fei.kpi.puchy.dots.service.Rating.RatingService;
import sk.tuke.fei.kpi.puchy.dots.service.Rating.RatingServiceJDBC;
import sk.tuke.fei.kpi.puchy.dots.service.Score.ScoreService;
import sk.tuke.fei.kpi.puchy.dots.service.Score.ScoreServiceJDBC;

import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

public class ConsoleUI {
    //suradnice z inputu medzi ktorymi sa budu porovnavat ci su rovnake farby ak ano pocita sa skore a menia sa
    char firstX;
    int firstY;
    char secondX;
    int secondY;
    private GameState state = GameState.NOTPLAYING;
    private final Scanner scanner = new Scanner(System.in);
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private RatingService ratingService;
    private static final Pattern COMMAND_PATTERN = Pattern.compile("([a-fA-F])([0-5])([a-fA-F])([0-5])");
    private final Field field;
    boolean playedAtLeastOnce = false;
    Gameplay gameplay = new Gameplay();
    private String gameName = "Dots";
    public ConsoleUI(Field field) {
        this.field = field;
    }
    public void play() {
        System.out.println("Zozmam všetkých výsledkov hier od hráčov [Hra, hráč, score, dátum odohratia]: ");
        scoreService.getTopScores("Dots").forEach(System.out::println);
        System.out.println("--------------------------------");
        System.out.println("Zozmam všetkých komentárov od hráčov [Hra, hráč, komentár, dátum komentovania]: ");
        commentService.getComments("Dots").forEach(System.out::println);



        state = GameState.NOTPLAYING;
        System.out.print(Colors.BLUE_BOLD_BRIGHT + "ENTER YOUR NAME: " + Colors.RESET);
        String meno = scanner.nextLine();
        System.out.print(Colors.YELLOW_BOLD_BRIGHT + "TYPE 'PLAY' TO START PLAYING THE GAME: " + Colors.RESET);
        String line = scanner.nextLine().toUpperCase();
        if ("PLAY".equals(line)){
            state = GameState.PLAYING;
            System.out.println("");
        }
        else{
            state = GameState.NOTPLAYING;
            System.out.print(Colors.RED_BOLD_BRIGHT + "WRONG INPUT, START THE GAME AGAIN!" + Colors.RESET);
        }

        if(state == GameState.PLAYING) {
            playedAtLeastOnce = true;
            do {
                printField();
                processInput();
                System.out.println("");
                System.out.println("");
                if(!gameplay.checkIfEnoughMovesAreLeft()){
                    state = GameState.NOTPLAYING;
                    System.out.print(Colors.RED_BOLD_BRIGHT + "GAME OVER,   Your final score is: " + gameplay.getScore() + Colors.CYAN_BOLD_BRIGHT + "     Your stats were successfully loaded into database" + Colors.RESET);
                    System.out.println("");
                }
            } while (state == GameState.PLAYING);
        }
        if(playedAtLeastOnce) {
            System.out.print(Colors.WHITE_BOLD_BRIGHT + "LEAVE YOUR COMMENT: " + Colors.RESET);
            String comment = scanner.nextLine();
            if (comment != null) {
                System.out.print(Colors.BLUE_UNDERLINED + "THANKS FOR YOUR COMMENT :): " + Colors.RESET);
                System.out.println("");
                commentService.addComment(new Comment(gameName, meno, comment, new Date()));
            }
            scoreService.addScore(new Score(gameName, meno, gameplay.getScore(), new Date()));
            System.out.print(Colors.WHITE_BOLD_BRIGHT + "LEAVE YOUR RATING FROM 1 TO 5: " + Colors.RESET);
            int hodnotenie = parseInt(scanner.nextLine());
            while (hodnotenie < 1 || hodnotenie > 5) {
                System.out.print(Colors.RED_BOLD_BRIGHT + "WRONG RATING, INSERT RATING FROM 1 TO 5: " + Colors.RESET);
                hodnotenie = parseInt(scanner.nextLine());
            }
            ratingService.setRating(new Rating(gameName, meno, hodnotenie, new Date()));
        }
    }


    private void printField() {
        for (int row = 0; row < field.getRowCount(); row++) {
            System.out.print((char)(row+65) +":  ");
            for (int column = 0; column < field.getColumnCount(); column++) {
                Dot bodka = field.getDot(row, column);
                switch (bodka.getColor()){
                    case RED:
                        System.out.print(Colors.RED + "O " + Colors.WHITE_BRIGHT + "| " + Colors.RESET);
                        continue;
                    case BLUE:
                        System.out.print(Colors.BLUE + "O " + Colors.WHITE_BRIGHT + "| " + Colors.RESET);
                        continue;
                    case WHITE:
                        System.out.print(Colors.WHITE + "O "+ Colors.WHITE_BRIGHT + "| " + Colors.RESET);
                        continue;
                    case ORANGE:
                        System.out.print(Colors.YELLOW + "O "+ Colors.WHITE_BRIGHT + "| " + Colors.RESET);
                        continue;
                    case GREEN:
                        System.out.print(Colors.GREEN + "O "+ Colors.WHITE_BRIGHT + "| " + Colors.RESET);
                }
            }
            System.out.println();
        }
        System.out.print("-   ");
        for (int column = 0; column < field.getColumnCount(); column++) {
            System.out.print(column +"   ");
        }
    }

    private void processInput() {
        System.out.println("");
        gameplay.printScore();
        System.out.println("");
        gameplay.printMovesLeft();
        System.out.println("");
        System.out.print(Colors.WHITE_BOLD_BRIGHT + "Enter range (Pattern: f.e. ´A2B2´ to connect dots from A2 to B2, X - to exit): " + Colors.RESET);
        String line = scanner.nextLine().toUpperCase();
        if ("X".equals(line)){
            System.out.print(Colors.MAGENTA_BOLD_BRIGHT + "GAME ENDED");
            System.exit(0);
        }
        Matcher matcher = COMMAND_PATTERN.matcher(line);
        if (matcher.matches()) {
            //nasnimame prvu a druhu suradnicu
            firstX = line.charAt(0);
            firstY = line.charAt(1);
            secondX = line.charAt(2);
            secondY = line.charAt(3);
            gameplay.setField(field);
            gameplay.setCoordinates(firstX, firstY, secondX, secondY);
            gameplay.coordinatesCheck();
            //GameLogic.gameplay(firstX, firstY, secondX, secondY);
        }
        else{
            incorrectInputCalling();
        }

    }

    private void incorrectInputCalling() {
        System.out.println("");
        System.out.print(Colors.RED_BOLD_BRIGHT + "INCORRECT INPUT");
        System.exit(0);
    }

    private void printTopScores() {
        var scores = scoreService.getTopScores("Dots");
        System.out.println("---------------------- TOP SCORE TABLE  -----------------------");
        for (int i = 0; i < scores.size(); i++) {
            var score = scores.get(i);
            System.out.printf("%d. %s %d\n", i + 1, score.getPlayer(), score.getPoints());
        }
        System.out.println("---------------------------------------------------------------");
    }

    private void printRating() {
        var rating = ratingService.getRating("Dots","Testing");
        System.out.println("---------------------- Average Rating for player 'Testing'  -----------------------");
        System.out.printf("Testing rating: %s\n", rating);
        System.out.println("---------------------------------------------------------------");
    }




}
