package empleados.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "empleados")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmpleadoEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer empleadoId;
    private String dni;
    private String nombres;
    private String apellidos;
    private String numeroCelular;
    private String correo;
    private String estado = "ACTIVO";
}
