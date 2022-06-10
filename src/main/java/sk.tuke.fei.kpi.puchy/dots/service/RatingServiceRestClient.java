package sk.tuke.fei.kpi.puchy.dots.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import sk.tuke.fei.kpi.puchy.dots.entity.Comment;
import sk.tuke.fei.kpi.puchy.dots.entity.Rating;
import sk.tuke.fei.kpi.puchy.dots.service.Rating.RatingException;
import sk.tuke.fei.kpi.puchy.dots.service.Rating.RatingService;

public class RatingServiceRestClient implements RatingService {
    private final String url = "http://localhost:8080/api/rating";
    @Autowired
    private RestTemplate restTemplate;
    @Override
    public void setRating(Rating rating) throws RatingException {
        restTemplate.postForEntity(url, rating, Rating.class);
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        return (restTemplate.getForEntity(url + "/", (int.class)).getBody());
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        return (restTemplate.getForEntity(url + "/" + game, (int.class)).getBody());
    }

    @Override
    public void reset() throws RatingException {
        throw new UnsupportedOperationException("Not supported via web service");
    }
}
