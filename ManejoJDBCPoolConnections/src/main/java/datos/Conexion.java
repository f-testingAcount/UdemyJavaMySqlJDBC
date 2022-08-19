package datos;

import java.sql.*;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;

public class Conexion {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/testprueba?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "adminmin23";
    
    public static DataSource getDataSource(){  //Utilizamos esta clase en lugar de DriverManger
        BasicDataSource ds = new BasicDataSource();
        ds.setUrl(JDBC_URL);
        ds.setUsername(JDBC_USER);
        ds.setPassword(JDBC_PASSWORD);
        ds.setInitialSize(5); //Definimos el tamaño del pool de conexiones
        return ds;
    }
    
    public static Connection getConnection() throws SQLException {
        //return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        return getDataSource().getConnection(); //Utilizamos esta linea en lugar de la anterior para llamar al pool de conexiones configuradas
    }
    
    public static void close(ResultSet rs) throws SQLException {
        rs.close();
    }
    
    public static void close(Statement stmt) throws SQLException {
        stmt.close();
    }
    
    public static void close(PreparedStatement stmt) throws SQLException {
        stmt.close();
    }
    
    public static void close(Connection conn) throws SQLException {
        conn.close();
    }
}
