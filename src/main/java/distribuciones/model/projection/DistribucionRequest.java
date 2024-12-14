package distribuciones.model.projection;

import java.util.Date;
import java.util.List;

public record DistribucionRequest(
        String nombre,
        String fechaDistribucion,
        String observaciones,
        String estadoEntrega,
        String estado,
        Integer colegioId,
        Integer empleadoId,
        List<DetalleDistribucionRequest> detalle
) {
    public record DetalleDistribucionRequest(
            Double cantidad,
            Integer productoId
    ) {}
}
