package colegios.resource;

import colegios.model.projection.ColegioRequest;
import colegios.service.ColegioService;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import util.Constant;

import java.util.HashMap;

public class ColegioResourceImpl implements ColegioResource {

    @Inject
    ColegioService colegioService;

    @Override
    public Response registrarColegio(ColegioRequest colegioRequest) {

        var response = new HashMap<String, Object>();

        Constant resp = colegioService.registrarColegio(colegioRequest);

        return switch (resp) {
            case SUCCESS -> {
                response.put("message", "El colegio ha sido registrado correctamente");
                yield Response.status(Response.Status.CREATED).entity(response).build();
            }
            case EXISTING -> {
                response.put("message", "Ya existe otro colegio con el mismo nombre");
                yield Response.status(Response.Status.CONFLICT).entity(response).build();
            }
            default -> {
                response.put("message", "Error inesperado al registrar el colegio");
                yield Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();
            }
        };
    }

    @Override
    public Response listarColegios() {

        var colegios = colegioService.lisatarColegios();

        return Response.status(Response.Status.OK).entity(colegios).build();
    }

    @Override
    public Response obtenerColegioPorId(Integer categoriaId) {

        var response = new HashMap<String, Object>();

        var colegio = colegioService.obtenerColegioPorId(categoriaId);

        if (colegio == null) {
            response.put("message", "No se encontró el colegio con el ID proporcionado");
            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }

        return  Response.status(Response.Status.OK).entity(colegio).build();
    }

    @Override
    public Response actualizarColegio(Integer colegioId, ColegioRequest colegioRequest) {

        var response = new HashMap<String, Object>();

        Constant resp = colegioService.actualizarColegio(colegioId, colegioRequest);

        return switch (resp) {
            case SUCCESS -> {
                response.put("message", "El colegio ha sido actualizado correctamente");
                yield Response.status(Response.Status.OK).entity(response).build();
            }
            case NOT_FOUND -> {
                response.put("message", "No se ha encontrado el colegio que quiere actualizar");
                yield Response.status(Response.Status.NOT_FOUND).entity(response).build();
            }
            case EXISTING -> {
                response.put("message", "Ya existe otro colegio con el mismo nombre");
                yield Response.status(Response.Status.CONFLICT).entity(response).build();
            }
            default -> {
                response.put("message", "Error inesperado al actualizar el colegio");
                yield Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();
            }
        };
    }

    @Override
    public Response eliminarColegio(Integer colegioId) {

        var response = new HashMap<String, Object>();

        Constant resp = colegioService.eliminarColegio(colegioId);

        return switch (resp) {
            case SUCCESS -> {
                response.put("message", "El colegio ha sido eliminado correctamente");
                yield Response.status(Response.Status.OK).entity(response).build();
            }
            case NOT_FOUND -> {
                response.put("message", "No se ha encontrado el colegio que quiere eliminar");
                yield Response.status(Response.Status.NOT_FOUND).entity(response).build();
            }
            default -> {
                response.put("message", "Error inesperado al eliminar el colegio");
                yield Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();
            }
        };
    }
}
