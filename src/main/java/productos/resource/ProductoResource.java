package productos.resource;

import productos.model.projection.ProductoRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/productos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface ProductoResource {

    @POST
    Response registrarProducto(ProductoRequest productoRequest);

    @GET
    Response listarProductos();

    @GET
    @Path("/{productoId}")
    Response obtenerProductoPorId(@PathParam("productoId") Integer productoId);

    @PUT
    @Path("/{productoId}")
    Response actualizarProducto(@PathParam("productoId") Integer productoId, ProductoRequest productoRequest);

    @DELETE
    @Path("/{productoId}")
    Response eliminarProducto(@PathParam("productoId") Integer productoId);
}
