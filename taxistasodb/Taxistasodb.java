package taxistasodb;

// imports para oracle (driver ojdbc6.jar o ojdbc7.jar)
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//imports para objectdb ( driver objectjdb.jar)
import javax.persistence.*;
import java.util.*;

public class Taxistasodb {
    
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        String usuario = "hr";
        String password = "hr";
        String host = "localhost";
        String puerto = "1521";
        String sid = "orcl";
        String ulrjdbc = "jdbc:oracle:thin:" + usuario + "/" + password + "@" + host + ":" + puerto + ":" + sid;

        Connection conn = DriverManager.getConnection(ulrjdbc);
        return conn;
    }
    
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        EntityManagerFactory emf =
        Persistence.createEntityManagerFactory("p3.odb");
        EntityManager em = emf.createEntityManager();
        
        Connection conn = getConnection();
        
        String tax = "select * from taxistas";
            ResultSet rs =null;
            PreparedStatement statement = conn.prepareStatement(tax);
            rs = statement.executeQuery();
            
        
        while (rs.next()) {
            Double numa = rs.getDouble("numa");
            String cir = rs.getString("circulo");
            String nom = rs.getString("nombre");
            Double dni = rs.getDouble("dni");
            Double edad = rs.getDouble("edad");
            System.out.println(numa+", "+ cir +", "+ nom + ", " + dni + ", "+ edad);
        
        
            em.getTransaction().begin();
        
            Taxistas p = new Taxistas(numa,cir,nom,dni,edad);
            
            em.persist(p);
            em.getTransaction().commit();
        }
        //em.getTransaction().commit();
        TypedQuery<Taxistas> m1 = em.createQuery("SELECT p from Taxistas p",Taxistas.class);
        List<Taxistas> res = m1.getResultList();
        for (Taxistas p :res){
            Double numa = p.getnuma();
            String circulo = p.getcirculo();
            String nombre = p.getnombre();
            Double dni = p.getdni();
            Double edad = p.getedad();
            System.out.println(numa+circulo+nombre+dni+edad);
            
            }
        em.close();
        }
    
    }

