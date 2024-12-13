package productos.service;

import productos.model.projection.ProductoRequest;
import productos.model.projection.ProductoResponse;
import util.Constant;

import java.util.List;

public interface ProductoService {

    Constant registrarProducto(ProductoRequest productoRequest);

    List<ProductoResponse> lisatarProductos();

    ProductoResponse obtenerProductoPorId(Integer productoId);

    Constant actualizarProducto(Integer productoId, ProductoRequest productoRequest);

    Constant eliminarProducto(Integer productoId);
}
