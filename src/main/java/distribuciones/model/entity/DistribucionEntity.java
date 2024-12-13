package distribuciones.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "distribuciones")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DistribucionEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer distribucionId;
    private Date fechaDistribucion;
    private String observaciones;
    private String estadoEntrega;
    private String estado = "ACTIVO";
    private Integer colegioId;
    private Integer empleadoId;
}
