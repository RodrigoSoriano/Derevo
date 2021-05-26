package backend;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConeccionBD {
    public Connection BaseDeDatosLink;

    public Connection getConnection(){
        String usuarioBD = "sa";
        String contrasenaBD = "#Cabezaco018";
        String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;database=Derevo";
        try{
            BaseDeDatosLink = DriverManager.getConnection(url, usuarioBD, contrasenaBD);
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }

        return  BaseDeDatosLink;
    }
}
