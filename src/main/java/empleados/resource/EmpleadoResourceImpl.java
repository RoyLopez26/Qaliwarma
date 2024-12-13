package empleados.resource;

import empleados.model.projection.EmpleadoRequest;
import empleados.service.EmpleadoService;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import util.Constant;

import java.util.HashMap;

public class EmpleadoResourceImpl implements EmpleadoResource {

    @Inject
    EmpleadoService empleadoService;

    @Override
    public Response registrarEmpleado(EmpleadoRequest empleadoRequest) {

        var response = new HashMap<String, Object>();

        Constant resp = empleadoService.registrarEmpleado(empleadoRequest);

        return switch (resp) {
            case SUCCESS -> {
                response.put("message", "El empleado ha sido registrado correctamente");
                yield Response.status(Response.Status.CREATED).entity(response).build();
            }
            case EXISTING -> {
                response.put("message", "Ya existe otro empleado con el mismo numero de DNI");
                yield Response.status(Response.Status.CONFLICT).entity(response).build();
            }
            case INVALID_DNI -> {
                response.put("message", "El número de DNI debe ser de 8 números");
                yield Response.status(Response.Status.BAD_REQUEST).entity(response).build();
            }
            default -> {
                response.put("message", "Error inesperado al registrar el empleado");
                yield Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();
            }
        };
    }

    @Override
    public Response listarEmpleados() {

        var empleados = empleadoService.lisatarEmpleados();

        return Response.status(Response.Status.OK).entity(empleados).build();
    }

    @Override
    public Response obtenerEmpleadoPorId(Integer empleadoId) {

        var response = new HashMap<String, Object>();

        var empleado = empleadoService.obtenerEmpleadoPorId(empleadoId);

        if (empleado == null) {
            response.put("message", "No se encontró el empleado con el ID proporcionado");
            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }

        return Response.status(Response.Status.OK).entity(empleado).build();
    }

    @Override
    public Response actualizarEmpleado(Integer empleadoId, EmpleadoRequest empleadoRequest) {

        var response = new HashMap<String, Object>();

        Constant resp = empleadoService.actualizarEmpleado(empleadoId, empleadoRequest);

        return switch (resp) {
            case SUCCESS -> {
                response.put("message", "El empleado ha sido actualizado correctamente");
                yield Response.status(Response.Status.OK).entity(response).build();
            }
            case NOT_FOUND -> {
                response.put("message", "No se ha encontrado al empleado que quiere actualizar");
                yield Response.status(Response.Status.NOT_FOUND).entity(response).build();
            }
            case EXISTING -> {
                response.put("message", "Ya existe otro empleado con el mismo número de DNI");
                yield Response.status(Response.Status.CONFLICT).entity(response).build();
            }
            case INVALID_DNI -> {
                response.put("message", "El número de DNI debe ser de 8 números");
                yield Response.status(Response.Status.BAD_REQUEST).entity(response).build();
            }
            default -> {
                response.put("message", "Error inesperado al actualizar el empleado");
                yield Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();
            }
        };
    }

    @Override
    public Response eliminarEmpleado(Integer empleadoId) {

        var response = new HashMap<String, Object>();

        Constant resp = empleadoService.eliminarEmpleado(empleadoId);

        return switch (resp) {
            case SUCCESS -> {
                response.put("message", "El empleado ha sido eliminado correctamente");
                yield Response.status(Response.Status.OK).entity(response).build();
            }
            case NOT_FOUND -> {
                response.put("message", "No se ha encontrado al empleado que quiere eliminar");
                yield Response.status(Response.Status.NOT_FOUND).entity(response).build();
            }
            default -> {
                response.put("message", "Error inesperado al eliminar el empleado");
                yield Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();
            }
        };
    }
}
