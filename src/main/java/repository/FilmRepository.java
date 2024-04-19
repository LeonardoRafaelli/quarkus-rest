package repository;

import com.speedment.jpastreamer.application.JPAStreamer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.quarkus_study.app.model.Film;
import org.quarkus_study.app.model.Film$;

import java.util.Optional;


@ApplicationScoped
public class FilmRepository {

        @Inject
        JPAStreamer jpaStreamer;
        // Optional - Cause it's gonna be populated, only if it finds a movie
        public Optional<Film> getFilmById(short filmId) {
            return jpaStreamer.stream(Film.class)
                    .filter(Film$.filmId.equal(filmId))
                    .findFirst();
        };
}
