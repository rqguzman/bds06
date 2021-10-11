package com.devsuperior.movieflix.services;

import com.devsuperior.movieflix.dto.MovieDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repositories.GenreRepository;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {

    @Autowired
    private MovieRepository repository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private AuthService authService;

    @Transactional(readOnly = true)
    public MovieDTO findById(Long id) {

        User user = authService.authenticated();

        Optional<Movie> obj = repository.findById(id);

        Movie entity = obj.orElseThrow(() -> new ResourceNotFoundException("Movie not found!"));

        return new MovieDTO(entity);
    }

    @Transactional(readOnly = true)
    public Page<MovieDTO> findByGenre(Long genreId, Pageable pageable) {

        User user = authService.authenticated();

        Page<Movie> moviesPage = (genreId == 0)
                ? repository.findPagedMovies(pageable)
                : repository.findByGenre(genreId, pageable);

        return moviesPage.map(x -> new MovieDTO(x));
    }
}
