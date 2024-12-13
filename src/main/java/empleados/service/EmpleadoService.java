package empleados.service;

import empleados.model.projection.EmpleadoRequest;
import empleados.model.projection.EmpleadoResponse;
import util.Constant;

import java.util.List;

public interface EmpleadoService {

    Constant registrarEmpleado(EmpleadoRequest empleadoRequest);

    List<EmpleadoResponse> lisatarEmpleados();

    EmpleadoResponse obtenerEmpleadoPorId(Integer empleadoId);

    Constant actualizarEmpleado(Integer empleadoId, EmpleadoRequest empleadoRequest);

    Constant eliminarEmpleado(Integer empleadoId);
}
