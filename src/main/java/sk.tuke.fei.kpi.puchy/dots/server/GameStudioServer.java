package sk.tuke.fei.kpi.puchy.dots.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sk.tuke.fei.kpi.puchy.dots.service.Comment.CommentService;
import sk.tuke.fei.kpi.puchy.dots.service.Comment.CommentServiceJPA;
import sk.tuke.fei.kpi.puchy.dots.service.Rating.RatingService;
import sk.tuke.fei.kpi.puchy.dots.service.Rating.RatingServiceJPA;
import sk.tuke.fei.kpi.puchy.dots.service.Score.ScoreService;
import sk.tuke.fei.kpi.puchy.dots.service.Score.ScoreServiceJPA;

@SpringBootApplication
@Configuration
@EntityScan("sk.tuke.fei.kpi.puchy.dots.entity")
public class GameStudioServer {
    public static void main(String[] args) {
        SpringApplication.run(GameStudioServer.class, args);
    }
    @Bean
    public ScoreService scoreService() {
        return new ScoreServiceJPA();
    }
    @Bean
    public CommentService commentService() {
        return new CommentServiceJPA();
    }
    @Bean
    public RatingService ratingService() {
        return new RatingServiceJPA();
    }
}
