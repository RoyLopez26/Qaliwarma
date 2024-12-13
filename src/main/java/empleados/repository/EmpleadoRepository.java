package empleados.repository;

import empleados.model.entity.EmpleadoEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EmpleadoRepository implements PanacheRepositoryBase<EmpleadoEntity, Integer> {
}
