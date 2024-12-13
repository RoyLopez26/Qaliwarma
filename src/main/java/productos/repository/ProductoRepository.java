package productos.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import productos.model.entity.ProductoEntity;

@ApplicationScoped
public class ProductoRepository implements PanacheRepositoryBase<ProductoEntity, Integer> {
}
