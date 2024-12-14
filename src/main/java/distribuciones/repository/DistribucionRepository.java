package distribuciones.repository;

import distribuciones.model.entity.DistribucionEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.List;

@ApplicationScoped
public class DistribucionRepository implements PanacheRepositoryBase<DistribucionEntity, Integer> {

    @PersistenceContext
    EntityManager entityManager;

    public List<Object[]> obtenerDistribuciones() {
        StringBuilder query = new StringBuilder("""
                SELECT d.distribucion_id,
                       d.fecha_distribucion,
                       d.observaciones,
                       d.estado_entrega,
                       d.estado,
                       d.colegio_id,
                       c.nombre,
                       d.empleado_id,
                       e.nombres,
                       e.apellidos
                FROM distribuciones d
                         INNER JOIN empleados e ON d.empleado_id = e.empleado_id
                         INNER JOIN colegios c ON d.colegio_id = c.colegio_id
                """);

        Query nativeQuery = entityManager.createNativeQuery(query.toString());

        return nativeQuery.getResultList();
    }

    public Object[] obtenerDistribucionPorId(Integer distribucionId) {
        StringBuilder query = new StringBuilder("""
                SELECT d.distribucion_id,
                       d.fecha_distribucion,
                       d.observaciones,
                       d.estado_entrega,
                       d.estado,
                       d.colegio_id,
                       c.nombre,
                       d.empleado_id,
                       e.nombres,
                       e.apellidos
                FROM distribuciones d
                         INNER JOIN empleados e ON d.empleado_id = e.empleado_id
                         INNER JOIN colegios c ON d.colegio_id = c.colegio_id
                         WHERE d.distribucion_id = :distribucionId
                """);

        Query nativeQuery = entityManager.createNativeQuery(query.toString());
        nativeQuery.setParameter("distribucionId", distribucionId);

        return (Object[]) nativeQuery.getSingleResult();
    }
}
