package distribuciones.resource;

import distribuciones.model.projection.DistribucionRequest;
import distribuciones.service.DistribucionService;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import util.Constant;

import java.util.HashMap;

public class DistribucionResourceImpl implements DistribucionResource {

    @Inject
    DistribucionService distribucionService;

    @Override
    public Response registrarDistribucion(DistribucionRequest distribucionRequest) {

        var response = new HashMap<String, Object>();

        Constant resp = distribucionService.registrarDistribucion(distribucionRequest);

        return switch (resp) {
            case SUCCESS -> {
                response.put("message", "La distribucion ha sido registrado correctamente");
                yield Response.status(Response.Status.CREATED).entity(response).build();
            }
            default -> {
                response.put("message", "Error inesperado al registrar la distribucion");
                yield Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();
            }
        };
    }

    @Override
    public Response listarDistribuciones() {

        var distribuciones = distribucionService.lisatarDistribuciones();

        return Response.status(Response.Status.OK).entity(distribuciones).build();
    }

    @Override
    public Response obtenerDistribucionPorId(Integer distribucionId) {

        var response = new HashMap<String, Object>();

        var distribucion = distribucionService.obtenerDistribucionPorId(distribucionId);

        if (distribucion == null) {
            response.put("message", "No se encontró la distribucion con el ID proporcionado");
            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }

        return  Response.status(Response.Status.OK).entity(distribucion).build();
    }

    @Override
    public Response eliminarDistribucion(Integer distribucionId) {

        var response = new HashMap<String, Object>();

        Constant resp = distribucionService.eliminarDistribucion(distribucionId);

        return switch (resp) {
            case SUCCESS -> {
                response.put("message", "La distribucion ha sido eliminado correctamente");
                yield Response.status(Response.Status.OK).entity(response).build();
            }
            case NOT_FOUND -> {
                response.put("message", "No se ha encontrado la distribucion que quiere eliminar");
                yield Response.status(Response.Status.NOT_FOUND).entity(response).build();
            }
            default -> {
                response.put("message", "Error inesperado al eliminar la distribucion");
                yield Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();
            }
        };
    }
}
