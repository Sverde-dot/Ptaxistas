package taxistasodb;

import java.io.Serializable;
import javax.persistence.*;

@Entity
public class Taxistas implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id 
    private Double numa;

    private String circulo;
    private String nombre;
    private Double dni;
    private Double edad;
    
    public Taxistas() {
    }

    public Taxistas(Double numa, String circulo, String nombre, Double dni, Double edad) {
        this.numa = numa;
        this.circulo = circulo;
        this.nombre = nombre;
        this.dni = dni;
        this.edad = edad;
    }

    public Double getnuma() {
        return numa;
    }

    public String getcirculo() {
         return circulo;
    }

    public String getnombre() {
         return nombre;
    }

    public Double getdni() {
        return dni;
    }

    public Double getedad() {
        return edad;
    }

    @Override
    public String toString() {
        return "Taxistas{" + "numa=" + numa + ", circulo=" + circulo + ", nombre=" + nombre + ", dni=" + dni + ", edad=" + edad + '}';
    }

    


    
    
}
