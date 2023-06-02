package persistencia;

import java.sql.*;

public class DatosPaisesDAO {

    private Connection connection;

    public DatosPaisesDAO(){
        persistencia.Conexion conexion = new persistencia.Conexion();
        this.connection = conexion.getConnection();
        this.crearTabla();
    }

    private void crearTabla(){
        try{
            Statement statement = this.connection.createStatement();
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS datosPais(ID INT AUTO_INCREMENT PRIMARY KEY, nombre VARCHAR(255), capital VARCHAR(255), poblacion VARCHAR(255), gini VARCHAR(255))");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public boolean crear(String nombre, String capital, String poblacion, String gini){
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(
                    "INSERT INTO datosPais( nombre, capital, poblacion, gini) VALUES (?, ?, ?, ?) "
            );
            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, capital);
            preparedStatement.setString(3, poblacion);
            preparedStatement.setString(4, gini);
            preparedStatement.execute();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }

    }

    public ResultSet listar(){
        ResultSet resultSet = null;
        try{
            Statement statement = this.connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM datospais");
            return resultSet;

        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

}
