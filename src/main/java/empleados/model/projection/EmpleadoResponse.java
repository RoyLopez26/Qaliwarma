package empleados.model.projection;

public record EmpleadoResponse(
        Integer empleadoId,
        String dni,
        String nombres,
        String apellidos,
        String numeroCelular,
        String correo,
        String estado
) {
}
