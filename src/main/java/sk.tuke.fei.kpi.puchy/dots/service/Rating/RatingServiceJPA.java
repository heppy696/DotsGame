package sk.tuke.fei.kpi.puchy.dots.service.Rating;


import sk.tuke.fei.kpi.puchy.dots.entity.Rating;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;


@Transactional
public class RatingServiceJPA implements RatingService {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void setRating(Rating rating) throws RatingException {
        entityManager.persist(rating);
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        return (int) entityManager.createQuery("select avg(s.points) from Score s")
                .getSingleResult();
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        return (int) entityManager.createQuery("select r.rating from Rating r").getSingleResult();
    }

    @Override
    public void reset() throws RatingException {
        entityManager.createNativeQuery("DELETE FROM Rating ").executeUpdate();
    }
}
