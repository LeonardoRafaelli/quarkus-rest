import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.quarkus_study.app.model.Film;
import repository.FilmRepository;

import java.util.Optional;

import static io.smallrye.common.constraint.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class FilmRepositoryTest {

    @Inject
    FilmRepository filmRepository;

    @Test
    public void test(){
        Optional<Film> filmOptional = filmRepository.getFilmById((short) 5);
        assertTrue(filmOptional.isPresent());
        assertEquals(filmOptional.get().getTitle(), "AFRICAN EGG");
    }
}
