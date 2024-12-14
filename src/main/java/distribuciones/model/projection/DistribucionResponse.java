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
        String nombreColegio,
        Integer empleadoId,
        String nombreCompleto,
        List<DetalleDistribucionRequest> detalle
) {
    public record DetalleDistribucionRequest(
            Integer detalleDistribucionId,
            Double cantidad,
            Integer productoId,
            String nombre
    ) {}
}
