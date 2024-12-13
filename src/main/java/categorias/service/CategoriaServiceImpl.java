package categorias.service;

import categorias.model.entity.CategoriaEntity;
import categorias.model.projection.CategoriaRequest;
import categorias.model.projection.CategoriaResponse;
import categorias.repository.CategoriaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import util.Constant;

import java.util.List;

@Slf4j
@ApplicationScoped
public class CategoriaServiceImpl implements CategoriaService {

    @Inject
    CategoriaRepository categoriaRepository;

    @Override
    @Transactional
    public Constant registrarCategoria(CategoriaRequest categoriaRequest) {
        try {

            boolean categoriaConMismoNombre = categoriaRepository.count("nombre", categoriaRequest.nombre()) > 0;

            if (categoriaConMismoNombre) {
                log.error("Ya existe una categoria con el nombre: {}", categoriaRequest.nombre());
                return Constant.EXISTING;
            }

            CategoriaEntity categoria = new CategoriaEntity();
            categoria.setNombre(categoriaRequest.nombre());
            categoria.setDescripcion(categoriaRequest.descripcion());
            categoriaRepository.persist(categoria);

            return Constant.SUCCESS;
        } catch (Exception e) {
            log.error("Error al registrar categoria: {}", e.getMessage());
            return Constant.ERROR;
        }
    }

    @Override
    public List<CategoriaResponse> lisatarCategorias() {
        return categoriaRepository.find("estado != ?1", "INACTIVO")
                .project(CategoriaResponse.class)
                .list();
    }

    @Override
    public CategoriaResponse obtenerCategoriaPorId(Integer categoriaId) {
        return categoriaRepository.find("categoriaId = ?1 AND estado != ?2", categoriaId, "INACTIVO")
                .project(CategoriaResponse.class)
                .firstResult();
    }

    @Override
    @Transactional
    public Constant actualizarCategoria(Integer categoriaId, CategoriaRequest categoriaRequest) {

        try {
            CategoriaEntity categoria = categoriaRepository.findById(categoriaId);

            if (categoria == null) {
                log.error("Categoria con ID {} no encontrado.", categoriaId);
                return Constant.NOT_FOUND;
            }

            boolean categoriaConMismoNombre = categoriaRepository.count("nombre = ?1 AND categoriaId != ?2", categoriaRequest.nombre(), categoriaId) > 0;

            if (categoriaConMismoNombre) {
                log.error("Ya existe otra categoria con el nombre: {}", categoriaRequest.nombre());
                return Constant.EXISTING;
            }

            categoria.setNombre(categoriaRequest.nombre());
            categoria.setDescripcion(categoriaRequest.descripcion());
            categoria.setEstado(categoriaRequest.estado());

            return Constant.SUCCESS;
        } catch (Exception e) {
            log.error("Error al actualizar categoria: {}", e.getMessage());
            return Constant.ERROR;
        }
    }

    @Override
    @Transactional
    public Constant eliminarCategoria(Integer categoriaId) {
        try {
            CategoriaEntity categoria = categoriaRepository.findById(categoriaId);

            if (categoria == null) {
                log.error("Categoria con ID {} no encontrado.", categoriaId);
                return Constant.NOT_FOUND;
            }

            categoria.setEstado("INACTIVO");

            return Constant.SUCCESS;
        } catch (Exception e) {
            log.error("Error al eliminar categoria: {}", e.getMessage());
            return Constant.ERROR;
        }
    }
}
