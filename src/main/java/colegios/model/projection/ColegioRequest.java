package colegios.model.projection;

public record ColegioRequest(
        String nombre,
        String direccion,
        String nivelEducativo,
        String numeroCelular,
        String estado
) {
}
