package categorias.service;

import categorias.model.projection.CategoriaRequest;
import categorias.model.projection.CategoriaResponse;
import util.Constant;

import java.util.List;

public interface CategoriaService {

    Constant registrarCategoria(CategoriaRequest categoriaRequest);

    List<CategoriaResponse> lisatarCategorias();

    CategoriaResponse obtenerCategoriaPorId(Integer categoriaId);

    Constant actualizarCategoria(Integer categoriaId, CategoriaRequest categoriaRequest);

    Constant eliminarCategoria(Integer categoriaId);
}
