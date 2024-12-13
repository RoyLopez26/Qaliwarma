package productos.model.projection;

public record ProductoRequest(
        String nombre,
        Double cantidad,
        String unidadMedida,
        String estado,
        Integer categoriaId
) {
}
