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

            for (DistribucionRequest.DetalleDistribucionRequest detalleDistribucion : distribucionRequest.distribuciones()) {
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

        var distribuciones = distribucionRepository.find("estado != ?1", "INACTIVO").list();

        var detalles = detalleDistribucionRepository.find("estado != ?1", "INACTIVO").list();

        var productos = productoRepository.find("estado != ?1", "INACTIVO").list();
        Map<Integer, String> productoNombreMap = productos.stream()
                .collect(Collectors.toMap(ProductoEntity::getProductoId, ProductoEntity::getNombre));

        return distribuciones.stream().map(distribucion -> {
            List<DistribucionResponse.DetalleDistribucionRequest> detallesDistribucion = detalles.stream()
                    .filter(detalle -> detalle.getDistribucionId().equals(distribucion.getDistribucionId()))
                    .map(detalle -> new DistribucionResponse.DetalleDistribucionRequest(
                            detalle.getDetalleDistribucionId(),
                            detalle.getCantidad(),
                            detalle.getProductoId(),
                            productoNombreMap.get(detalle.getProductoId())
                    ))
                    .toList();

            return new DistribucionResponse(
                    distribucion.getDistribucionId(),
                    DATE_FORMAT.format(distribucion.getFechaDistribucion()),
                    distribucion.getObservaciones(),
                    distribucion.getEstadoEntrega(),
                    distribucion.getEstado(),
                    distribucion.getColegioId(),
                    distribucion.getEmpleadoId(),
                    detallesDistribucion
            );
        }).toList();
    }

    @Override
    public DistribucionResponse obtenerDistribucionPorId(Integer distribucionId) {

        DistribucionEntity distribucion = distribucionRepository.find("distribucionId", distribucionId).firstResult();

        if (distribucion == null || "INACTIVO".equals(distribucion.getEstado())) {
            log.error("Distribución con ID {} no encontrada", distribucionId);
            return null;
        }

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
                distribucion.getDistribucionId(),
                DATE_FORMAT.format(distribucion.getFechaDistribucion()),
                distribucion.getObservaciones(),
                distribucion.getEstadoEntrega(),
                distribucion.getEstado(),
                distribucion.getColegioId(),
                distribucion.getEmpleadoId(),
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
