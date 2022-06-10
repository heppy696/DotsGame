package sk.tuke.fei.kpi.puchy.dots.service.Rating;

import sk.tuke.fei.kpi.puchy.dots.entity.Rating;

public interface RatingService {
    void setRating(Rating rating) throws RatingException;
    int getAverageRating(String game) throws RatingException;
    int getRating(String game, String player) throws RatingException;
    void reset() throws RatingException;
}
