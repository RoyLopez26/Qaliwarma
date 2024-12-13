package categorias.resource;

import categorias.model.projection.CategoriaRequest;
import categorias.service.CategoriaService;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import util.Constant;

import java.util.HashMap;

public class CategoriaResourceImpl implements CategoriaResource {

    @Inject
    CategoriaService categoriaService;

    @Override
    public Response registrarCategoria(CategoriaRequest categoriaRequest) {

        var response = new HashMap<String, Object>();

        Constant resp = categoriaService.registrarCategoria(categoriaRequest);

        return switch (resp) {
            case SUCCESS -> {
                response.put("message", "La categoria ha sido registrado correctamente");
                yield Response.status(Response.Status.CREATED).entity(response).build();
            }
            case EXISTING -> {
                response.put("message", "Ya existe otra categoria con el mismo nombre");
                yield Response.status(Response.Status.CONFLICT).entity(response).build();
            }
            default -> {
                response.put("message", "Error inesperado al registrar la categoria");
                yield Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();
            }
        };
    }

    @Override
    public Response listarCategorias() {

        var categorias = categoriaService.lisatarCategorias();

        return Response.status(Response.Status.OK).entity(categorias).build();
    }

    @Override
    public Response obtenerCategoriaPorId(Integer categoriaId) {

        var response = new HashMap<String, Object>();

        var categoria = categoriaService.obtenerCategoriaPorId(categoriaId);

        if (categoria == null) {
            response.put("message", "No se encontró la categoria con el ID proporcionado");
            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }

        return  Response.status(Response.Status.OK).entity(categoria).build();
    }

    @Override
    public Response actualizarCategoria(Integer categoriaId, CategoriaRequest categoriaRequest) {

        var response = new HashMap<String, Object>();

        Constant resp = categoriaService.actualizarCategoria(categoriaId, categoriaRequest);

        return switch (resp) {
            case SUCCESS -> {
                response.put("message", "La categoria ha sido actualizado correctamente");
                yield Response.status(Response.Status.OK).entity(response).build();
            }
            case NOT_FOUND -> {
                response.put("message", "No se ha encontrado la categoria que quiere actualizar");
                yield Response.status(Response.Status.NOT_FOUND).entity(response).build();
            }
            case EXISTING -> {
                response.put("message", "Ya existe otra categoria con el mismo nombre");
                yield Response.status(Response.Status.CONFLICT).entity(response).build();
            }
            default -> {
                response.put("message", "Error inesperado al actualizar la categoria");
                yield Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();
            }
        };
    }

    @Override
    public Response eliminarCategoria(Integer categoriaId) {

        var response = new HashMap<String, Object>();

        Constant resp = categoriaService.eliminarCategoria(categoriaId);

        return switch (resp) {
            case SUCCESS -> {
                response.put("message", "La categoria ha sido eliminado correctamente");
                yield Response.status(Response.Status.OK).entity(response).build();
            }
            case NOT_FOUND -> {
                response.put("message", "No se ha encontrado la categoria que quiere eliminar");
                yield Response.status(Response.Status.NOT_FOUND).entity(response).build();
            }
            default -> {
                response.put("message", "Error inesperado al eliminar la categoria");
                yield Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();
            }
        };
    }
}
