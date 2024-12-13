package colegios.repository;

import colegios.model.entity.ColegioEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ColegioRepository implements PanacheRepositoryBase<ColegioEntity, Integer> {
}
