package distribuciones.model.projection;

import java.util.Date;
import java.util.List;

public record DistribucionResponse(
        Integer distribucionId,
        String fechaDistribucion,
        String observaciones,
        String estadoEntrega,
        String estado,
        Integer colegioId,
        Integer empleadoId,
        List<DetalleDistribucionRequest> distribuciones
) {
    public record DetalleDistribucionRequest(
            Integer detalleDistribucionId,
            Double cantidad,
            Integer productoId,
            String nombre
    ) {}
}
