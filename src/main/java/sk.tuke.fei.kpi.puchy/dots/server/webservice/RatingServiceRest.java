package sk.tuke.fei.kpi.puchy.dots.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.fei.kpi.puchy.dots.entity.Rating;
import sk.tuke.fei.kpi.puchy.dots.entity.Score;
import sk.tuke.fei.kpi.puchy.dots.service.Rating.RatingException;
import sk.tuke.fei.kpi.puchy.dots.service.Rating.RatingService;
import sk.tuke.fei.kpi.puchy.dots.service.Score.ScoreService;

import java.util.List;

@RestController
@RequestMapping("/api/rating")
public class RatingServiceRest {
    @Autowired
    private RatingService ratingService;

    @PostMapping
    public void setRating(@RequestBody Rating rating) {
        ratingService.setRating(rating);
    }


    @GetMapping("/{game}")
    public int getAverageRating(@PathVariable String game) {
        return ratingService.getAverageRating(game);
    }

    @GetMapping("/{game}/{player}")
    public int getRating(@PathVariable String game,@PathVariable String player){
        return ratingService.getRating(game,player);
    }
}
