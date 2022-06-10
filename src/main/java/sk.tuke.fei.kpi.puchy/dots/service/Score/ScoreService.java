package sk.tuke.fei.kpi.puchy.dots.service.Score;

import sk.tuke.fei.kpi.puchy.dots.entity.Score;
import sk.tuke.fei.kpi.puchy.dots.service.Score.ScoreException;

import java.util.List;

public interface ScoreService {
    void addScore(Score score) throws ScoreException;
    List<Score> getTopScores(String game) throws ScoreException;
    void reset() throws ScoreException;
}
