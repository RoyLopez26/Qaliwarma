package distribuciones.repository;

import distribuciones.model.entity.DetalleDistribucionEntity;
import distribuciones.model.entity.DistribucionEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DetalleDistribucionRepository implements PanacheRepositoryBase<DetalleDistribucionEntity, Integer> {
}
