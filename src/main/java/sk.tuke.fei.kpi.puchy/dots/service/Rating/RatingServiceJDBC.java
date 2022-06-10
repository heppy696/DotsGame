package sk.tuke.fei.kpi.puchy.dots.service.Rating;

import sk.tuke.fei.kpi.puchy.dots.entity.Rating;
import sk.tuke.fei.kpi.puchy.dots.service.Score.ScoreException;

import java.sql.*;

public class RatingServiceJDBC implements RatingService {
    public static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    public static final String USER = "postgres";
    public static final String PASSWORD = "admin";
    public static final String SELECT = "SELECT rating FROM rating WHERE (game = ? AND player = ?)";
    public static final String DELETE = "DELETE FROM rating";
    public static final String INSERT = "INSERT INTO rating (game, player, rating, ratedon) VALUES (?, ?, ?, ?)";
    public static final String AVG_RATING = "SELECT  game, player, rating, ratedon FROM rating WHERE (game = ?)";

    @Override
    public void setRating(Rating rating) throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(INSERT);
        ) {
            statement.setString(1, rating.getGame());
            statement.setString(2, rating.getPlayer());
            statement.setInt(3, rating.getRating());
            statement.setTimestamp(4, new Timestamp(rating.getRatedOn().getTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ScoreException("Problem inserting rating", e);
        }
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        int sum = 0;
        int numberOfRatings = 0;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(AVG_RATING);
        ) {
            statement.setString(1, game);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    sum = sum + rs.getInt(3);
                    numberOfRatings++;
                }
            }
        } catch (SQLException e) {
            throw new ScoreException("Problem selecting score", e);
        }
        if (numberOfRatings != 0)
            return sum / numberOfRatings;
        else
            return 0;
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        int rating = 0;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(SELECT);
        ) {
            statement.setString(1, game);
            statement.setString(2, player);
            statement.executeUpdate();
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    rating = result.getInt(3);
                }
            }

        } catch (SQLException e) {
            throw new ScoreException("Problem selecting score", e);
        }
        return rating;
    }

    @Override
    public void reset() throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
        ) {
            statement.executeUpdate(DELETE);
        } catch (SQLException e) {
            throw new ScoreException("Problem deleting score", e);
        }
    }
}
