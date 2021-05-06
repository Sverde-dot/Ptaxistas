package ptaxisoracle.mongo;


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
import java.sql.PreparedStatement;
import org.bson.Document;
import org.bson.types.ObjectId;


public class PtaxisoracleMongo {

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
    
    //COSAS DE ORACLE
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
    
    //LA MAIN
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        
        //Lectura oracle y mongo
        Connection conn = getConnection();
        conectar_a_servidor();
        conectar_a_base("taxis");
        
        //Cambio a semana en oracle
        String sem = "select * from semana";
            ResultSet rs =null;
            PreparedStatement statement = conn.prepareStatement(sem);
            rs = statement.executeQuery();
        //1 Bucle con semana
        while (rs.next()) {
            String tri = rs.getString("triangulo");
            String dia = rs.getString("dia");
            System.out.println(tri + ", " + dia);
        
        //Cambio a taxistas en mongo
        coleciontaxistas = database.getCollection("taxistas");
        BasicDBObject condicion = new BasicDBObject("circulo",tri);
            
            FindIterable<Document> findtaxistas = coleciontaxistas.find(condicion);
            MongoCursor<Document> iteratortaxistas = findtaxistas.iterator();
            
                while (iteratortaxistas.hasNext()){
                Document next2 = iteratortaxistas.next();
                Double numa = next2.getDouble("numa");
                String nom = next2.getString("nombre");
                Double dni = next2.getDouble("dni");
                Double edad = next2.getDouble("edad");
                System.out.println(numa+", "+ nom +", " + dni + ", " + edad);
                
                //Cambio a ingresos en oracle
                String ing = "select cobro,propina from ingresos where numerot = ? ";
                        ResultSet rs2 =null;
                        PreparedStatement st2 = conn.prepareStatement(ing);
                        st2.setDouble(1,numa);
                        rs2 = st2.executeQuery();
                        while (rs2.next()) {
                        String cob = rs2.getString("cobro");
                        String pro = rs2.getString("propina");
                        System.out.println(cob + ", " + pro);
                    }
            }
        }
        conn.close();
    }
    
}