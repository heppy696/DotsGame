package sk.tuke.fei.kpi.puchy.dots.server.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sk.tuke.fei.kpi.puchy.dots.game.Dot;
import sk.tuke.fei.kpi.puchy.dots.game.Field;
import sk.tuke.fei.kpi.puchy.dots.service.Score.ScoreService;

import java.util.Date;

@Controller
@RequestMapping("/dots")
public class DotsController {
    @Autowired
    private ScoreService scoreService;
    private Field field = new Field(6,6);
    private boolean firstClick = true;
    private int firstRow;
    private int firstColumn;
    private int secondRow;
    private int secondColumn;
    @RequestMapping
    public String dots(@RequestParam(required = false) Integer row, @RequestParam(required = false) Integer column, Model model){

        if (row != null && column != null){
               if(firstClick == true){
                   firstRow = row;
                   firstColumn = column;
                   firstClick = false;
               }
               else{
                   secondRow = row;
                   secondColumn = column;
               }
        }
        prepareModel(model);
        return "dots";
    }

    @RequestMapping("/new")
    public String newGame(Model model) {
        field = new Field(6,6);
        prepareModel(model);
        return "dots";
    }


    public String getHtmlField() {
        StringBuilder sb = new StringBuilder();
        sb.append("<table class='dotsfield'>\n");
        for (int row = 0; row < field.getRowCount(); row++) {
            sb.append("<tr>\n");
            for (int column = 0; column < field.getColumnCount(); column++) {
                var dot = field.getDot(row, column);
                sb.append("<td>\n");
                sb.append("<a href='/dots?row=" + row + "&column=" + column + "'>\n");
                sb.append("<img src='/images/dots/" + getDotClass(dot) + ".jpg'>");
                sb.append("</a>\n");
                sb.append("</td>\n");
            }
            sb.append("</tr>\n");
        }

        sb.append("</table>\n");
        return sb.toString();
    }

    private String getDotClass(Dot dot) {
        switch (dot.getColor()) {
            case RED:
                return "red";
            case BLUE:
                return "blue";
            case WHITE:
                return "white";
            case ORANGE:
                return "orange";
            case GREEN:
                return "green";
            default:
                throw new RuntimeException("Unexpected dot state");
        }
    }

    private void prepareModel(Model model) {
        model.addAttribute("scores", scoreService.getTopScores("dots"));
    }





}
