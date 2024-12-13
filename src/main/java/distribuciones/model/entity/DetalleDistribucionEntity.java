package distribuciones.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "detalle_distribuciones")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DetalleDistribucionEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer detalleDistribucionId;
    private Double cantidad;
    private String estado = "ACTIVO";
    private Integer productoId;
    private Integer distribucionId;
}
