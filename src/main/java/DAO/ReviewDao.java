package DAO;

import entities.Review;
import jakarta.persistence.EntityManager;

public class ReviewDao extends BaseDao<Review> {
    public ReviewDao(EntityManager em) { super(em, Review.class); }
}