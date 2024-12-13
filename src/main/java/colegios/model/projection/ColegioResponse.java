package colegios.model.projection;

public record ColegioResponse(
        Integer colegioId,
        String nombre,
        String direccion,
        String nivelEducativo,
        String numeroCelular,
        String estado
) {
}
