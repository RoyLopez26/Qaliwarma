package categorias.repository;

import categorias.model.entity.CategoriaEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CategoriaRepository implements PanacheRepositoryBase<CategoriaEntity, Integer> {
}
