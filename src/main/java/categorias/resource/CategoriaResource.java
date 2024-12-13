package categorias.resource;

import categorias.model.projection.CategoriaRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/categorias")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface CategoriaResource {

    @POST
    Response registrarCategoria(CategoriaRequest categoriaRequest);

    @GET
    Response listarCategorias();

    @GET
    @Path("/{categoriaId}")
    Response obtenerCategoriaPorId(@PathParam("categoriaId") Integer categoriaId);

    @PUT
    @Path("/{categoriaId}")
    Response actualizarCategoria(@PathParam("categoriaId") Integer categoriaId, CategoriaRequest categoriaRequest);

    @DELETE
    @Path("/{categoriaId}")
    Response eliminarCategoria(@PathParam("categoriaId") Integer categoriaId);
}
