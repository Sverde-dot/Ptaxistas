package ptaxismongo.oracle;

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


public class PtaxismongoOracle {

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
        
        //Lecturas
        Connection conn = getConnection();
        conectar_a_servidor();
        conectar_a_base("taxis");
        
        //Conexion semana en mongo
        colecionsemana = database.getCollection("semana");
        FindIterable<Document> findsemana = colecionsemana.find();
        MongoCursor<Document> iteratorsemana = findsemana.iterator();
            
            //1 Bucle con semana
            while (iteratorsemana.hasNext()){
            Document next1 = iteratorsemana.next();
            String tri = next1.getString("triangulo");
            String dia = next1.getString("dia");
            System.out.println(tri + ", " + dia);
            
            //Cambio a taxistas en oracle
            String tax = "select numa,dni,nombre from taxistas where circulo = ? ";
                ResultSet rs =null;
                PreparedStatement st = conn.prepareStatement(tax);
                st.setString(1, tri);
                rs = st.executeQuery();
                //2 Bucle con taxistas
                while (rs.next()) {
                    Double numa = rs.getDouble("numa");
                    String nom = rs.getString("nombre");
                    Double dni = rs.getDouble("dni");
                    System.out.println(numa+", "+ nom + ", " + dni);
                    
                //Cambio a ingresos en mongo
                    colecioningresos = database.getCollection("ingresos");
                    BasicDBObject condiciontaxistas = new BasicDBObject("numerot",numa);
            
                    FindIterable<Document> findingresos = colecioningresos.find(condiciontaxistas);
                    MongoCursor<Document> iteratoringresos = findingresos.iterator();
                    //3 Bucle con ingresos
                    while (iteratoringresos.hasNext()){
                        Document next2 = iteratoringresos.next();
                        Double cob = next2.getDouble("cobro");
                        Double pro = next2.getDouble("propina");
                        System.out.println(cob + ", " + pro);
                    }
                }
            }     
        }
}
