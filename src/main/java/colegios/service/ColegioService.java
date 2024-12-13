package colegios.service;

import colegios.model.projection.ColegioRequest;
import colegios.model.projection.ColegioResponse;
import util.Constant;

import java.util.List;

public interface ColegioService {

    Constant registrarColegio(ColegioRequest colegioRequest);

    List<ColegioResponse> lisatarColegios();

    ColegioResponse obtenerColegioPorId(Integer colegioId);

    Constant actualizarColegio(Integer colegioId, ColegioRequest colegioRequest);

    Constant eliminarColegio(Integer colegioId);
}
