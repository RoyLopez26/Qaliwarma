package categorias.model.projection;

public record CategoriaResponse(
        Integer categoriaId,
        String nombre,
        String descripcion,
        String estado
) {
}
