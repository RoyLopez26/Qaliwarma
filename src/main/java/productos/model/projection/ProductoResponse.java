package productos.model.projection;

public record ProductoResponse(
        Integer productoId,
        String nombre,
        Double cantidad,
        String unidadMedida,
        String estado,
        Integer categoriaId
) {
}
