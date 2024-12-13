package productos.service;

import productos.model.entity.ProductoEntity;
import productos.model.projection.ProductoRequest;
import productos.model.projection.ProductoResponse;
import productos.repository.ProductoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import util.Constant;

import java.util.List;

@Slf4j
@ApplicationScoped
public class ProductoServiceImpl implements ProductoService {

    @Inject
    ProductoRepository productoRepository;

    @Override
    @Transactional
    public Constant registrarProducto(ProductoRequest productoRequest) {
        try {

            boolean productoConMismoNombre = productoRepository.count("nombre", productoRequest.nombre()) > 0;

            if (productoConMismoNombre) {
                log.error("Ya existe un producto con el nombre: {}", productoRequest.nombre());
                return Constant.EXISTING;
            }

            ProductoEntity producto = new ProductoEntity();
            producto.setNombre(productoRequest.nombre());
            producto.setCantidad(productoRequest.cantidad());
            producto.setUnidadMedida(productoRequest.unidadMedida());
            producto.setCategoriaId(productoRequest.categoriaId());
            productoRepository.persist(producto);

            return Constant.SUCCESS;
        } catch (Exception e) {
            log.error("Error al registrar producto: {}", e.getMessage());
            return Constant.ERROR;
        }
    }

    @Override
    public List<ProductoResponse> lisatarProductos() {
        return productoRepository.find("estado != ?1", "INACTIVO")
                .project(ProductoResponse.class)
                .list();
    }

    @Override
    public ProductoResponse obtenerProductoPorId(Integer productoId) {
        return productoRepository.find("productoId = ?1 AND estado != ?2", productoId, "INACTIVO")
                .project(ProductoResponse.class)
                .firstResult();
    }

    @Override
    @Transactional
    public Constant actualizarProducto(Integer productoId, ProductoRequest productoRequest) {

        try {
            ProductoEntity producto = productoRepository.findById(productoId);

            if (producto == null) {
                log.error("Producto con ID {} no encontrado.", productoId);
                return Constant.NOT_FOUND;
            }

            boolean productoConMismoNombre = productoRepository.count("nombre = ?1 AND productoId != ?2", productoRequest.nombre(), productoId) > 0;

            if (productoConMismoNombre) {
                log.error("Ya existe otro producto con el nombre: {}", productoRequest.nombre());
                return Constant.EXISTING;
            }

            producto.setNombre(productoRequest.nombre());
            producto.setCantidad(productoRequest.cantidad());
            producto.setUnidadMedida(productoRequest.unidadMedida());
            producto.setEstado(productoRequest.estado());
            producto.setCategoriaId(productoRequest.categoriaId());

            return Constant.SUCCESS;
        } catch (Exception e) {
            log.error("Error al actualizar producto: {}", e.getMessage());
            return Constant.ERROR;
        }
    }

    @Override
    @Transactional
    public Constant eliminarProducto(Integer productoId) {
        try {
            ProductoEntity producto = productoRepository.findById(productoId);

            if (producto == null) {
                log.error("Producto con ID {} no encontrado.", productoId);
                return Constant.NOT_FOUND;
            }

            producto.setEstado("INACTIVO");

            return Constant.SUCCESS;
        } catch (Exception e) {
            log.error("Error al eliminar producto: {}", e.getMessage());
            return Constant.ERROR;
        }
    }
}
