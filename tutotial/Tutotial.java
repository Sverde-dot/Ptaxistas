package tutotial;

import javax.persistence.*;
import java.util.*;

public class Tutotial {
    public static void main(String[] args) {
        // Open a database connection
        // (create a new database if it doesn't exist yet):
        EntityManagerFactory emf =
        Persistence.createEntityManagerFactory("p3.odb");
        EntityManager em = emf.createEntityManager();

        // Almacena 10 puntos en la base de datos
        // Parte 1
        em.getTransaction().begin();
        for (int i = 0; i < 10; i++) {
            Point p = new Point(i, i);
            em.persist(p);
        }
        em.getTransaction().commit();
        // Parte 2
        TypedQuery<Point> m1 = em.createQuery("SELECT p from Point p",Point.class);
        List<Point> res = m1.getResultList();
        for (Point p :res){
            System.out.println("Point id:" +p.getId());
            System.out.println("Point Atributos:" + p);
        }
        // Parte 3
        TypedQuery<Point> m2 = em.createQuery("SELECT p from Point P where id = 10",Point.class);
        Point p1 = m2.getSingleResult();
        System.out.println("Punto con id="+p1.getId()+"\n Atributos:" + p1);
        // Parte 4
        System.out.println("Update del point con id=10");
        em.getTransaction().begin();
        Query m3 = em.createQuery("UPDATE Point SET y = y+2 WHERE id=10");
        m3.executeUpdate();
        em.getTransaction().commit();
        // Parte 5
        /*
        System.out.println("delete del point con id=5");
        em.getTransaction().begin();
        int ok = em.createQuery("DELETE from Point WHERE id=5").executeUpdate();
        em.getTransaction().commit();
        */
        // Parte 6
        /*
        System.out.println("actualizar point con y < 6");
        em.getTransaction().begin();
        Query quer = em.createQuery("UPDATE Point SET y=:var WHERE y<6");
        quer.setParameter("var",100);
        quer.executeUpdate();
        em.getTransaction().commit();
        */
        // Parte 7
        /*
        System.out.println("Borrado de points con x < al valor pasado por parametro");
        em.getTransaction().begin();
        Query quer2 = em.createQuery("DELETE from Point WHERE x<:var");
        quer2.setParameter("var", 3);
        quer2.executeUpdate();
        em.getTransaction().commit();
        */

        // Close the database connection:
        em.close();
        emf.close();
        System.out.println("consulta");
        emf = Persistence.createEntityManagerFactory("$objectdb/db/p2.odb");
        em = emf.createEntityManager();
        m1 = em.createQuery("SELECT p from Point p", Point.class);
        res = m1.getResultList();
        for (Point p : res){
            System.out.println("Point id:"+p.getId());
            System.out.println("Point Atributos:" + p);
        }
        em.close();
        emf.close();
    }
}