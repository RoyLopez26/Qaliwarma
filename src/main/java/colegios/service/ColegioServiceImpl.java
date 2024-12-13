package colegios.service;

import colegios.model.entity.ColegioEntity;
import colegios.model.projection.ColegioRequest;
import colegios.model.projection.ColegioResponse;
import colegios.repository.ColegioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import util.Constant;

import java.util.List;

@Slf4j
@ApplicationScoped
public class ColegioServiceImpl implements ColegioService {

    @Inject
    ColegioRepository colegioRepository;

    @Override
    @Transactional
    public Constant registrarColegio(ColegioRequest colegioRequest) {
        try {

            boolean colegioConMismoNombre = colegioRepository.count("nombre", colegioRequest.nombre()) > 0;

            if (colegioConMismoNombre) {
                log.error("Ya existe un colegio con el nombre: {}", colegioRequest.nombre());
                return Constant.EXISTING;
            }

            ColegioEntity colegio = new ColegioEntity();
            colegio.setNombre(colegioRequest.nombre());
            colegio.setDireccion(colegioRequest.direccion());
            colegio.setNivelEducativo(colegioRequest.nivelEducativo());
            colegio.setNumeroCelular(colegioRequest.numeroCelular());
            colegioRepository.persist(colegio);

            return Constant.SUCCESS;
        } catch (Exception e) {
            log.error("Error al registrar colegio: {}", e.getMessage());
            return Constant.ERROR;
        }
    }

    @Override
    public List<ColegioResponse> lisatarColegios() {
        return colegioRepository.find("estado != ?1", "INACTIVO")
                .project(ColegioResponse.class)
                .list();
    }

    @Override
    public ColegioResponse obtenerColegioPorId(Integer colegioId) {
        return colegioRepository.find("colegioId = ?1 AND estado != ?2", colegioId, "INACTIVO")
                .project(ColegioResponse.class)
                .firstResult();
    }

    @Override
    @Transactional
    public Constant actualizarColegio(Integer colegioId, ColegioRequest colegioRequest) {

        try {
            ColegioEntity colegio = colegioRepository.findById(colegioId);

            if (colegio == null) {
                log.error("Colegio con ID {} no encontrado.", colegioId);
                return Constant.NOT_FOUND;
            }

            boolean colegioConMismoNombre = colegioRepository.count("nombre = ?1 AND colegioId != ?2", colegioRequest.nombre(), colegioId) > 0;

            if (colegioConMismoNombre) {
                log.error("Ya existe otro colegio con el nombre: {}", colegioRequest.nombre());
                return Constant.EXISTING;
            }

            colegio.setNombre(colegioRequest.nombre());
            colegio.setDireccion(colegioRequest.direccion());
            colegio.setNivelEducativo(colegioRequest.nivelEducativo());
            colegio.setNumeroCelular(colegioRequest.numeroCelular());
            colegio.setEstado(colegioRequest.estado());

            return Constant.SUCCESS;
        } catch (Exception e) {
            log.error("Error al actualizar colegio: {}", e.getMessage());
            return Constant.ERROR;
        }
    }

    @Override
    @Transactional
    public Constant eliminarColegio(Integer colegioId) {
        try {
            ColegioEntity colegio = colegioRepository.findById(colegioId);

            if (colegio == null) {
                log.error("Colegio con ID {} no encontrado.", colegioId);
                return Constant.NOT_FOUND;
            }

            colegio.setEstado("INACTIVO");

            return Constant.SUCCESS;
        } catch (Exception e) {
            log.error("Error al eliminar colegio: {}", e.getMessage());
            return Constant.ERROR;
        }
    }
}
