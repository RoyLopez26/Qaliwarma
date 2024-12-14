package distribuciones.service;

import distribuciones.model.entity.DetalleDistribucionEntity;
import distribuciones.model.entity.DistribucionEntity;
import distribuciones.model.projection.DistribucionRequest;
import distribuciones.model.projection.DistribucionResponse;
import distribuciones.repository.DetalleDistribucionRepository;
import distribuciones.repository.DistribucionRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import productos.model.entity.ProductoEntity;
import productos.repository.ProductoRepository;
import util.Constant;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@ApplicationScoped
public class DistribucionServiceImpl implements DistribucionService {

    @Inject
    DistribucionRepository distribucionRepository;

    @Inject
    DetalleDistribucionRepository detalleDistribucionRepository;

    @Inject
    ProductoRepository productoRepository;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    @Transactional
    public Constant registrarDistribucion(DistribucionRequest distribucionRequest) {
        try {

            DistribucionEntity distribucion = new DistribucionEntity();
            distribucion.setFechaDistribucion(DATE_FORMAT.parse(distribucionRequest.fechaDistribucion()));
            distribucion.setObservaciones(distribucionRequest.observaciones());
            distribucion.setEstadoEntrega(distribucionRequest.estadoEntrega());
            distribucion.setColegioId(distribucionRequest.colegioId());
            distribucion.setEmpleadoId(distribucionRequest.empleadoId());
            distribucionRepository.persist(distribucion);

            for (DistribucionRequest.DetalleDistribucionRequest detalleDistribucion : distribucionRequest.detalle()) {
                DetalleDistribucionEntity detalle = new DetalleDistribucionEntity();
                detalle.setCantidad(detalleDistribucion.cantidad());
                detalle.setProductoId(detalleDistribucion.productoId());
                detalle.setDistribucionId(distribucion.getDistribucionId());
                detalleDistribucionRepository.persist(detalle);
            }

            return Constant.SUCCESS;
        } catch (Exception e) {
            log.error("Error al registrar distribucion: {}", e.getMessage());
            return Constant.ERROR;
        }
    }

    @Override
    public List<DistribucionResponse> lisatarDistribuciones() {

        var distribuciones = distribucionRepository.obtenerDistribuciones();

        var detalles = detalleDistribucionRepository.find("estado != ?1", "INACTIVO").list();

        var productos = productoRepository.find("estado != ?1", "INACTIVO").list();
        Map<Integer, String> productoNombreMap = productos.stream()
                .collect(Collectors.toMap(ProductoEntity::getProductoId, ProductoEntity::getNombre));

        return distribuciones.stream().map(distribucion -> {
            List<DistribucionResponse.DetalleDistribucionRequest> detallesDistribucion = detalles.stream()
                    .filter(detalle -> detalle.getDistribucionId().equals(distribucion[0]))
                    .map(detalle -> new DistribucionResponse.DetalleDistribucionRequest(
                            detalle.getDetalleDistribucionId(),
                            detalle.getCantidad(),
                            detalle.getProductoId(),
                            productoNombreMap.get(detalle.getProductoId())
                    ))
                    .toList();

            return new DistribucionResponse(
                    (Integer) distribucion[0],
                    DATE_FORMAT.format(distribucion[1]),
                    (String) distribucion[2],
                    (String) distribucion[3],
                    (String) distribucion[4],
                    (Integer) distribucion[5],
                    (String) distribucion[6],
                    (Integer) distribucion[7],
                    (String) distribucion[8] + " " + (String) distribucion[9],
                    detallesDistribucion
            );
        }).toList();
    }

    @Override
    public DistribucionResponse obtenerDistribucionPorId(Integer distribucionId) {

        var distribucionGuardada = distribucionRepository.findById(distribucionId);


        if (distribucionGuardada == null || "INACTIVO".equals(distribucionGuardada.getEstado())) {
            log.error("Distribución con ID {} no encontrada", distribucionId);
            return null;
        }

        var distribucion = distribucionRepository.obtenerDistribucionPorId(distribucionId);

        List<DetalleDistribucionEntity> detalles = detalleDistribucionRepository.find(
                "distribucionId = ?1 AND estado != ?2", distribucionId, "INACTIVO"
        ).list();

        var productos = productoRepository.find("estado != ?1", "INACTIVO").list();
        Map<Integer, String> productoNombreMap = productos.stream()
                .collect(Collectors.toMap(ProductoEntity::getProductoId, ProductoEntity::getNombre));


        List<DistribucionResponse.DetalleDistribucionRequest> detallesDistribucion = detalles.stream()
                .map(detalle -> new DistribucionResponse.DetalleDistribucionRequest(
                        detalle.getDetalleDistribucionId(),
                        detalle.getCantidad(),
                        detalle.getProductoId(),
                        productoNombreMap.get(detalle.getProductoId())
                ))
                .toList();

        return new DistribucionResponse(
                (Integer) distribucion[0],
                DATE_FORMAT.format(distribucion[1]),
                (String) distribucion[2],
                (String) distribucion[3],
                (String) distribucion[4],
                (Integer) distribucion[5],
                (String) distribucion[6],
                (Integer) distribucion[7],
                (String) distribucion[8] + " " + (String) distribucion[9],
                detallesDistribucion
        );
    }

    @Override
    @Transactional
    public Constant eliminarDistribucion(Integer distribucionId) {
        try {
            DistribucionEntity distribucion = distribucionRepository.findById(distribucionId);

            if (distribucion == null) {
                log.error("Distribucion con ID {} no encontrado.", distribucionId);
                return Constant.NOT_FOUND;
            }

            distribucion.setEstado("INACTIVO");

            List<DetalleDistribucionEntity> detalles = detalleDistribucionRepository.find(
                    "distribucionId = ?1 AND estado != ?2", distribucionId, "INACTIVO"
            ).list();

            for (DetalleDistribucionEntity detalle : detalles) {
                detalle.setEstado("INACTIVO");
            }

            return Constant.SUCCESS;
        } catch (Exception e) {
            log.error("Error al eliminar distribucion: {}", e.getMessage());
            return Constant.ERROR;
        }
    }
}
