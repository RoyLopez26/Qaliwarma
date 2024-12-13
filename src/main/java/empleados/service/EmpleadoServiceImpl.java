package empleados.service;

import empleados.model.entity.EmpleadoEntity;
import empleados.model.projection.EmpleadoRequest;
import empleados.model.projection.EmpleadoResponse;
import empleados.repository.EmpleadoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import util.Constant;

import java.util.List;

@Slf4j
@ApplicationScoped
public class EmpleadoServiceImpl implements EmpleadoService{

    @Inject
    EmpleadoRepository empleadoRepository;

    @Override
    @Transactional
    public Constant registrarEmpleado(EmpleadoRequest empleadoRequest) {
        try {
            // Validación del DNI
            if (!empleadoRequest.dni().matches("\\d{8}")) {
                log.error("DNI inválido. Debe tener exactamente 8 dígitos.");
                return Constant.INVALID_DNI;
            }

            boolean empleadoConMismoDni = empleadoRepository.count("dni", empleadoRequest.dni()) > 0;

            if (empleadoConMismoDni) {
                log.error("Ya existe un empleado con el DNI: {}", empleadoRequest.dni());
                return Constant.EXISTING;
            }

            EmpleadoEntity empleado = new EmpleadoEntity();
            empleado.setDni(empleadoRequest.dni());
            empleado.setNombres(empleadoRequest.nombres());
            empleado.setApellidos(empleadoRequest.apellidos());
            empleado.setNumeroCelular(empleadoRequest.numeroCelular());
            empleado.setCorreo(empleadoRequest.correo());
            empleadoRepository.persist(empleado);

            return Constant.SUCCESS;
        } catch (Exception e) {
            log.error("Error al registrar empleado: {}", e.getMessage());
            return Constant.ERROR;
        }
    }

    @Override
    public List<EmpleadoResponse> lisatarEmpleados() {
        return empleadoRepository.find("estado != ?1", "INACTIVO")
                .project(EmpleadoResponse.class)
                .list();
    }

    @Override
    public EmpleadoResponse obtenerEmpleadoPorId(Integer empleadoId) {
        return empleadoRepository.find("empleadoId = ?1 AND estado != ?2", empleadoId, "INACTIVO")
                .project(EmpleadoResponse.class)
                .firstResult();
    }

    @Override
    @Transactional
    public Constant actualizarEmpleado(Integer empleadoId, EmpleadoRequest empleadoRequest) {
        try {
            // Validación del DNI
            if (!empleadoRequest.dni().matches("\\d{8}")) {
                log.error("DNI inválido. Debe tener exactamente 8 dígitos.");
                return Constant.INVALID_DNI;
            }

            EmpleadoEntity empleado = empleadoRepository.findById(empleadoId);

            if (empleado == null) {
                log.error("Empleado con ID {} no encontrado.", empleadoId);
                return Constant.NOT_FOUND;
            }

            boolean empleadoConMismoDni = empleadoRepository.count("dni = ?1 AND empleadoId != ?2", empleadoRequest.dni(), empleadoId) > 0;

            if (empleadoConMismoDni) {
                log.error("Ya existe otro empleado con el DNI: {}", empleadoRequest.dni());
                return Constant.EXISTING;
            }

            empleado.setDni(empleadoRequest.dni());
            empleado.setNombres(empleadoRequest.nombres());
            empleado.setApellidos(empleadoRequest.apellidos());
            empleado.setNumeroCelular(empleadoRequest.numeroCelular());
            empleado.setCorreo(empleadoRequest.correo());
            empleado.setEstado(empleadoRequest.estado());

            return Constant.SUCCESS;
        } catch (Exception e) {
            log.error("Error al actualizar empleado: {}", e.getMessage());
            return Constant.ERROR;
        }
    }

    @Override
    @Transactional
    public Constant eliminarEmpleado(Integer empleadoId) {
        try {

            EmpleadoEntity empleado = empleadoRepository.findById(empleadoId);

            if (empleado == null) {
                log.error("Empleado con ID {} no encontrado.", empleadoId);
                return Constant.NOT_FOUND;
            }

            empleado.setEstado("INACTIVO");

            return Constant.SUCCESS;
        } catch (Exception e) {
            log.error("Error al eliminar empleado: {}", e.getMessage());
            return Constant.ERROR;
        }
    }
}
