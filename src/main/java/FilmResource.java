import io.vertx.codegen.annotations.ProxyClose;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.quarkus_study.app.model.Film;
import repository.FilmRepository;

import java.awt.*;
import java.util.Optional;
import java.util.stream.Collectors;

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


    @GET
    @Path("/film-page/{page}/{minLength}")
    @Produces(MediaType.TEXT_PLAIN)
    public String filmPage(long page, short minLength){
        return filmRepository.getPageFilm(page, minLength)
                .map(f -> String.format("%s (%d min)", f.getTitle(), f.getLength()))
                .collect(Collectors.joining("\n"));
    }

    @GET
    @Path("/actors/{startsWith}")
    @Produces(MediaType.TEXT_PLAIN)
    public String actors(String startsWith){
        return filmRepository.actors(startsWith)
                .map(f -> String.format("%s (%d min) - %s",
                        f.getTitle(),
                        f.getLength(),
                        f.getActors().stream().map(
                                a -> String.format("%s %s", a.getFirstName(), a.getLastName()))
                                .collect(Collectors.joining(", "))
                        )).collect(Collectors.joining("\n"));
    }

}
