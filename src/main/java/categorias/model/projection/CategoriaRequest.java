package categorias.model.projection;

public record CategoriaRequest(
        String nombre,
        String descripcion,
        String estado
) {
}
