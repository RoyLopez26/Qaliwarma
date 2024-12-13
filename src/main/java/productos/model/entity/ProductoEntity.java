package productos.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "productos")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductoEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productoId;
    private String nombre;
    private Double cantidad;
    private String unidadMedida;
    private String estado = "ACTIVO";
    private Integer categoriaId;
}
