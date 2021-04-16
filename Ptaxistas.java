package ptaxistas;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Ptaxistas {

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
        
        Connection conn = getConnection();

        String sem = "select * from semana";
            ResultSet rs =null;
            PreparedStatement statement = conn.prepareStatement(sem);
            rs = statement.executeQuery();
        
        while (rs.next()) {
            String tri = rs.getString("triangulo");
            String dia = rs.getString("dia");
            System.out.println(tri + ", " + dia);
       
            String tax = "select numa,dni,nombre from taxistas where circulo = ? ";
                ResultSet rs2 =null;
                PreparedStatement st2 = conn.prepareStatement(tax);
                st2.setString(1, tri);
                rs2 = st2.executeQuery();
                
                while (rs2.next()) {
                    String numa = rs2.getString("numa");
                    String nom = rs2.getString("nombre");
                    String dni = rs2.getString("dni");
                    System.out.println(numa+", "+ nom + ", " + dni);
                    
                    String ing = "select cobro,propina from ingresos where numerot = ? ";
                        ResultSet rs3 =null;
                        PreparedStatement st3 = conn.prepareStatement(tax);
                        st3.setString(1, numa);
                        rs3 = st3.executeQuery();
                
                        while (rs3.next()) {
                            String cob = rs3.getString("cobro");
                            String pro = rs3.getString("propina");
                            System.out.println(cob + ", " + pro);
     
                        }           
                }
        }
        conn.close();
        
    }  
}
