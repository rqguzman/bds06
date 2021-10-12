package com.devsuperior.movieflix.repositories;

import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("SELECT obj "
            + "FROM Movie obj "
            + "WHERE obj.genre.id  = :id "
            + "ORDER BY obj.title")
    Page<Movie> findByGenre(Long id, Pageable pageable);

    @Query("SELECT obj "
            + "FROM Movie obj "
            + "ORDER BY obj.title")
    Page<Movie> findPagedMovies(Pageable pageable);

    @Query("SELECT obj "
            + "FROM Movie obj "
            + "JOIN FETCH obj.genre "
            + "WHERE obj IN :movies")
    List<Movie> findListWithGenre(List<Movie> movies);


    @Query("SELECT obj.reviews "
            + "FROM Movie obj "
            + "WHERE obj.id = :id ")
    List<Review> getReviewsByMovieId(Long id);
}
