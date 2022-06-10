package sk.tuke.fei.kpi.puchy.dots;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.client.RestTemplate;
import sk.tuke.fei.kpi.puchy.dots.game.ConsoleUI;
import sk.tuke.fei.kpi.puchy.dots.game.Field;
import sk.tuke.fei.kpi.puchy.dots.service.Comment.CommentService;
import sk.tuke.fei.kpi.puchy.dots.service.Comment.CommentServiceJPA;
import sk.tuke.fei.kpi.puchy.dots.service.CommentServiceRestClient;
import sk.tuke.fei.kpi.puchy.dots.service.Rating.RatingService;
import sk.tuke.fei.kpi.puchy.dots.service.Rating.RatingServiceJPA;
import sk.tuke.fei.kpi.puchy.dots.service.RatingServiceRestClient;
import sk.tuke.fei.kpi.puchy.dots.service.Score.ScoreService;
import sk.tuke.fei.kpi.puchy.dots.service.Score.ScoreServiceJDBC;
import sk.tuke.fei.kpi.puchy.dots.service.Score.ScoreServiceJPA;
import sk.tuke.fei.kpi.puchy.dots.service.ScoreServiceRestClient;

@SpringBootApplication
@Configuration
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX,
        pattern = "sk.tuke.fei.kpi.puchy.dots.server.*"))
public class SpringClient {
    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringClient.class).web(WebApplicationType.NONE).run(args);
    }
    @Bean
    public CommandLineRunner runner(ConsoleUI consoleUI){
        return s -> {

            consoleUI.play();


        };
    }
    @Bean
    public ConsoleUI consoleUI(){
        return new ConsoleUI(field());
    }
    @Bean
    public Field field(){
        return new Field(6,6);
    }
    @Bean
    public ScoreService scoreService(){
        return new ScoreServiceRestClient();
    }
    @Bean
    public CommentService commentService(){
        return new CommentServiceRestClient();
    }
    @Bean
    public RatingService ratingService(){
        return new RatingServiceRestClient();
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}

