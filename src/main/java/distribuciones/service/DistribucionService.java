package distribuciones.service;

import distribuciones.model.projection.DistribucionRequest;
import distribuciones.model.projection.DistribucionResponse;
import util.Constant;

import java.util.List;

public interface DistribucionService {

    Constant registrarDistribucion(DistribucionRequest distribucionRequest);

    List<DistribucionResponse> lisatarDistribuciones();

    DistribucionResponse obtenerDistribucionPorId(Integer distribucionId);

    Constant eliminarDistribucion(Integer distribucionId);
}
