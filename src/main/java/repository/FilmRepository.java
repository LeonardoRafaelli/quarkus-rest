package repository;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import com.speedment.jpastreamer.application.JPAStreamer;
import com.speedment.jpastreamer.projection.Projection;
import com.speedment.jpastreamer.streamconfiguration.StreamConfiguration;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.hibernate.sql.ast.tree.predicate.Junction;
import org.quarkus_study.app.model.Film;
import org.quarkus_study.app.model.Film$;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@ApplicationScoped
public class FilmRepository {

        @Inject
        JPAStreamer jpaStreamer;
        // Optional - Because it's going to be populated, only if it finds a movie

        private static final int PAGE_SIZE = 20;

        public Optional<Film> getFilmById(short filmId) {
            return jpaStreamer.stream(Film.class)
                    .filter(Film$.filmId.equal(filmId))
                    .findFirst();
        };

        public Stream<Film> getPageFilm(long page, short minLength){
                return jpaStreamer.stream(Projection.select(Film$.filmId, Film$.title, Film$.length))
                        .filter(Film$.length.greaterThan(minLength))
                        .sorted(Film$.length)
                        .skip(page * PAGE_SIZE)
                        .limit(PAGE_SIZE);
        }


        public Stream<Film> actors (String startsWith) {
                final StreamConfiguration<Film> sc =
                        StreamConfiguration.of(Film.class)
                                .joining(Film$.actors);

                return jpaStreamer.stream(sc)
                        .filter(Film$.title.startsWith(startsWith))
                        .sorted(Film$.length.reversed());
        }
}
