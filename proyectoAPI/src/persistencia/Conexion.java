package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private Connection connection;

    public Conexion(){
        try{
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/usuarios", "root", "981019");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return this.connection;
    }
}
