package empleados.model.projection;

public record EmpleadoRequest(
        String dni,
        String nombres,
        String apellidos,
        String numeroCelular,
        String correo,
        String estado
) {
}
