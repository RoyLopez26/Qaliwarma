package colegios.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "colegios")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ColegioEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer colegioId;
    private String nombre;
    private String direccion;
    private String nivelEducativo;
    private String numeroCelular;
    private String estado = "ACTIVO";
}
