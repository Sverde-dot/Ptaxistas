package pmongotaxistas;

// imports para oracle (driver ojdbc6.jar o ojdbc7.jar)
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//imports para objectdb ( driver objectjdb.jar)
import javax.persistence.*;
import java.util.*;

//imports para mongo (driver: mongo-java-driver-3.4.2.jar (no meu caso)) 
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.bson.types.ObjectId;


public class Pmongotaxistas {
    
//COSAS DE MONGO
    public static MongoClient client;
    public static MongoDatabase database;
    public static MongoCollection<Document> colecionsemana;
    public static MongoCollection<Document> coleciontaxistas;
    public static MongoCollection<Document> colecioningresos;
    
    public static void  conectar_a_servidor(){
        client = new MongoClient("localhost",27017);
    } 
    public static void  conectar_a_base(String nomebase){
        database = client.getDatabase(nomebase);
    }
    public static void  conectar_a_unha_colecion(String coleccion){
        colecionsemana = database.getCollection(coleccion);
    } 
    
 
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        conectar_a_servidor();
        conectar_a_base("taxis");
        //conectar_a_unha_colecion("semana");
        colecionsemana = database.getCollection("semana");
        FindIterable<Document> findsemana = colecionsemana.find();
        MongoCursor<Document> iteratorsemana = findsemana.iterator();
        
            Double megatotal=0.0;
        
            while (iteratorsemana.hasNext()){
            Document next1 = iteratorsemana.next();
            //String id = next.getString("_id");
            String tri = next1.getString("triangulo");
            String dia = next1.getString("dia");
            System.out.println(tri + ", " + dia);
            
            
            //CAMBIO A TAXISTAS
            coleciontaxistas = database.getCollection("taxistas");
            BasicDBObject condicion = new BasicDBObject("circulo",tri);
            
            FindIterable<Document> findtaxistas = coleciontaxistas.find(condicion);
            MongoCursor<Document> iteratortaxistas = findtaxistas.iterator();
            
                Double totaltotaltotal=0.0;
            
                while (iteratortaxistas.hasNext()){
                Document next2 = iteratortaxistas.next();
                //String id = next.getString("_id");
                Double numa = next2.getDouble("numa");
                String nom = next2.getString("nombre");
                Double dni = next2.getDouble("dni");
                Double edad = next2.getDouble("edad");
                System.out.println(numa+", "+ nom +", " + dni + ", " + edad);
                
                
                //CAMBIO A INGRESOS
                colecioningresos = database.getCollection("ingresos");
                BasicDBObject condiciontaxista = new BasicDBObject("numerot",numa);
            
                FindIterable<Document> findingresos = colecioningresos.find(condiciontaxista);
                MongoCursor<Document> iteratoringresos = findingresos.iterator();
                    
                //Variables para mostrar el cobro y propinas totales
                    Double totalcob=0.0;
                    Double totalpro=0.0;
                    Double totaltotal=0.0;
                    
                    while (iteratoringresos.hasNext()){
                    Document next3 = iteratoringresos.next();
                    //String id = next.getString("_id");
                    Double cob = next3.getDouble("cobro");
                    Double pro = next3.getDouble("propina");
                       
                    //Acumulamos el cobro y propinas de un taxista en una
                    totalcob=totalcob+cob;
                    totalpro=totalpro+pro;
                    
                    System.out.println(cob + ", " + pro);
                }
                //Mostramos el resultado de ambos totales     
                System.out.println(totalcob+", "+totalpro);
                System.out.println("El taxista consiguio: "+(totaltotal=totalcob+totalpro));
                totaltotaltotal=totaltotaltotal+totaltotal;
                iteratoringresos.close();
            }
            System.out.println("Las cuentas del dia son: "+totaltotaltotal);
            megatotal=megatotal+totaltotaltotal;
            iteratortaxistas.close();
        }
        System.out.println("Resultado de toda la semana: "+megatotal);
        iteratorsemana.close();
        client.close();
    }

}
