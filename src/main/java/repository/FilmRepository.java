package repository;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import com.speedment.jpastreamer.application.JPAStreamer;
import com.speedment.jpastreamer.projection.Projection;
import com.speedment.jpastreamer.streamconfiguration.StreamConfiguration;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.hibernate.sql.ast.tree.predicate.Junction;
import org.quarkus_study.app.model.Film;
import org.quarkus_study.app.model.Film$;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@ApplicationScoped
public class FilmRepository {

        @Inject
        JPAStreamer jpaStreamer;

        private static final int PAGE_SIZE = 20;

        // Optional - Because it's going to be populated, only if it finds a movie
        public Optional<Film> getFilmById(short filmId) {
            return jpaStreamer.stream(Film.class)
                    .filter(Film$.filmId.equal(filmId))
                    .findFirst();
        };

        public Stream<Film> getFilms(short minLength){
                return jpaStreamer.stream(Projection.select(Film$.filmId, Film$.title, Film$.length, Film$.rentalRate))
                        .filter(Film$.length.greaterThan(minLength))
                        .sorted(Film$.length);
        }

        public Stream<Film> getPageFilm(long page, short minLength){
                return jpaStreamer.stream(Projection.select(Film$.filmId, Film$.title, Film$.length))
                        .filter(Film$.length.greaterThan(minLength))
                        .sorted(Film$.length)
                        .skip(page * PAGE_SIZE)
                        .limit(PAGE_SIZE);
        }


        public Stream<Film> actors (String startsWith, short minLength) {
                final StreamConfiguration<Film> sc =
                        StreamConfiguration.of(Film.class)
                                .joining(Film$.actors);

                return jpaStreamer.stream(sc)
                        .filter(Film$.title.startsWith(startsWith).and(Film$.length.greaterThan(minLength)))
                        .sorted(Film$.length.reversed());
        }


        @Transactional
        public void updateRentalRate (short minLength, BigDecimal rentalRate){
                jpaStreamer.stream(Film.class)
                  .filter(Film$.length.greaterThan(minLength))
                  .forEach(f -> {
                          f.setRentalRate(rentalRate);
                  });
        };
}
