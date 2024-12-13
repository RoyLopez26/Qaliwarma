package empleados.resource;

import empleados.model.projection.EmpleadoRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/empleados")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface EmpleadoResource {

    @POST
    Response registrarEmpleado(EmpleadoRequest empleadoRequest);

    @GET
    Response listarEmpleados();

    @GET
    @Path("/{empleadoId}")
    Response obtenerEmpleadoPorId(@PathParam("empleadoId") Integer empleadoId);

    @PUT
    @Path("/{empleadoId}")
    Response actualizarEmpleado(@PathParam("empleadoId") Integer empleadoId, EmpleadoRequest empleadoRequest);

    @DELETE
    @Path("/{empleadoId}")
    Response eliminarEmpleado(@PathParam("empleadoId") Integer empleadoId);
}
