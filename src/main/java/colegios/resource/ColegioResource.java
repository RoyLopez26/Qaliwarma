package colegios.resource;

import colegios.model.projection.ColegioRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/colegios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface ColegioResource {

    @POST
    Response registrarColegio(ColegioRequest colegioRequest);

    @GET
    Response listarColegios();

    @GET
    @Path("/{colegioId}")
    Response obtenerColegioPorId(@PathParam("colegioId") Integer colegioId);

    @PUT
    @Path("/{colegioId}")
    Response actualizarColegio(@PathParam("colegioId") Integer colegioId, ColegioRequest colegioRequest);

    @DELETE
    @Path("/{colegioId}")
    Response eliminarColegio(@PathParam("colegioId") Integer colegioId);
}
