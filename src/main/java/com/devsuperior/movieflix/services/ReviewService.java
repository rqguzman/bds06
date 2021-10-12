package com.devsuperior.movieflix.services;

import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository repository;

    @Autowired
    private AuthService authService;

    @PreAuthorize("hasAnyRole('MEMBER')")
    @Transactional
    public ReviewDTO insert(ReviewDTO dto) {

        User user = authService.authenticated();
        System.out.println(">>>> TEST: " + user);
        Review entity = new Review();
        entity.setText(dto.getText());
        entity.setMovie(new Movie(dto.getMovieId(), null, null, null, null, null, null));
        entity.setUser(user);

        entity = repository.save(entity);

        return new ReviewDTO(entity);
    }

}
