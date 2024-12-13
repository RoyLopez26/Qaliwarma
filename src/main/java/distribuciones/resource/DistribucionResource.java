package distribuciones.resource;

import distribuciones.model.projection.DistribucionRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/distribuciones")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface DistribucionResource {

    @POST
    Response registrarDistribucion(DistribucionRequest distribucionRequest);

    @GET
    Response listarDistribuciones();

    @GET
    @Path("/{distribucionId}")
    Response obtenerDistribucionPorId(@PathParam("distribucionId") Integer distribucionId);

    @DELETE
    @Path("/{distribucionId}")
    Response eliminarDistribucion(@PathParam("distribucionId") Integer distribucionId);
}
