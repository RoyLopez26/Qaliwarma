package productos.resource;

import productos.model.projection.ProductoRequest;
import productos.service.ProductoService;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import util.Constant;

import java.util.HashMap;

public class ProductoResourceImpl implements ProductoResource {

    @Inject
    ProductoService productoService;

    @Override
    public Response registrarProducto(ProductoRequest productoRequest) {

        var response = new HashMap<String, Object>();

        Constant resp = productoService.registrarProducto(productoRequest);

        return switch (resp) {
            case SUCCESS -> {
                response.put("message", "El producto ha sido registrado correctamente");
                yield Response.status(Response.Status.CREATED).entity(response).build();
            }
            case EXISTING -> {
                response.put("message", "Ya existe otro producto con el mismo nombre");
                yield Response.status(Response.Status.CONFLICT).entity(response).build();
            }
            default -> {
                response.put("message", "Error inesperado al registrar el producto");
                yield Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();
            }
        };
    }

    @Override
    public Response listarProductos() {

        var productos = productoService.lisatarProductos();

        return Response.status(Response.Status.OK).entity(productos).build();
    }

    @Override
    public Response obtenerProductoPorId(Integer categoriaId) {

        var response = new HashMap<String, Object>();

        var producto = productoService.obtenerProductoPorId(categoriaId);

        if (producto == null) {
            response.put("message", "No se encontró el producto con el ID proporcionado");
            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }

        return  Response.status(Response.Status.OK).entity(producto).build();
    }

    @Override
    public Response actualizarProducto(Integer productoId, ProductoRequest productoRequest) {

        var response = new HashMap<String, Object>();

        Constant resp = productoService.actualizarProducto(productoId, productoRequest);

        return switch (resp) {
            case SUCCESS -> {
                response.put("message", "El producto ha sido actualizado correctamente");
                yield Response.status(Response.Status.OK).entity(response).build();
            }
            case NOT_FOUND -> {
                response.put("message", "No se ha encontrado el producto que quiere actualizar");
                yield Response.status(Response.Status.NOT_FOUND).entity(response).build();
            }
            case EXISTING -> {
                response.put("message", "Ya existe otro producto con el mismo nombre");
                yield Response.status(Response.Status.CONFLICT).entity(response).build();
            }
            default -> {
                response.put("message", "Error inesperado al actualizar el producto");
                yield Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();
            }
        };
    }

    @Override
    public Response eliminarProducto(Integer productoId) {

        var response = new HashMap<String, Object>();

        Constant resp = productoService.eliminarProducto(productoId);

        return switch (resp) {
            case SUCCESS -> {
                response.put("message", "El producto ha sido eliminado correctamente");
                yield Response.status(Response.Status.OK).entity(response).build();
            }
            case NOT_FOUND -> {
                response.put("message", "No se ha encontrado el producto que quiere eliminar");
                yield Response.status(Response.Status.NOT_FOUND).entity(response).build();
            }
            default -> {
                response.put("message", "Error inesperado al eliminar el producto");
                yield Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();
            }
        };
    }
}
