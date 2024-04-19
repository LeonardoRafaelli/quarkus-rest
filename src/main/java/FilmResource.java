import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.quarkus_study.app.model.Film;
import repository.FilmRepository;

import java.awt.*;
import java.util.Optional;

@Path("/")
public class FilmResource {

    @Inject
    FilmRepository filmRepository;

    @GET
    @Path("/helloWorld")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello(){
        return "Hello World!";
    }

    @GET
    @Path("/film/{filmId}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getFilmById(short filmId){
        Optional<Film> optionalFilm = filmRepository.getFilmById(filmId);
        return optionalFilm.isPresent() ? optionalFilm.get().getTitle() : "No film was found!";
    }

}
