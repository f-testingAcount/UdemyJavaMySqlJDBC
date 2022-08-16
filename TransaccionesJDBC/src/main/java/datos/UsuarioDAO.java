package datos;

import static datos.Conexion.*;
import domain.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private static final String SQL_SELECT = "SELECT idusuario, username, password FROM usuario"; // Para simplificar esta sentencia se puede colocar SELECT * FROM usuario
    private static final String SQL_INSERT = "INSERT INTO usuario(username, password) VALUES(?, ?)";
    private static final String SQL_UPDATE = "UPDATE usuario SET username = ?, password = ? WHERE idusuario = ?";
    private static final String SQL_DELETE = "DELETE FROM usuario WHERE idusuario = ?";
    private Connection conexionTransaccional;
    
    public UsuarioDAO(){}
    
    public UsuarioDAO(Connection conexionTransaccional){
        this.conexionTransaccional = conexionTransaccional;
    }
    
    public List<Usuario> seleccionar() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null; //PreparedStatement es una interface
        ResultSet rs = null;
        Usuario usuario = null;
        List<Usuario> usuarios = new ArrayList<>();

        try {
            conn = getConnection(); //Tenemos importado el static de clase Conexion.getconection() para evitar citar la clase
            stmt = conn.prepareStatement(SQL_SELECT); //preparedStatement es un metodo implementado de la interface PreparedStatement
            rs = stmt.executeQuery(); //con la variable resultado ejecutamos la consulta
            while (rs.next()) {
                int idUsuario = rs.getInt("idusuario");
                String username = rs.getString("username");
                String password = rs.getString("password");
                usuario = new Usuario();
                usuario.setIdusuario(idUsuario);
                usuario.setUsername(username);
                usuario.setPassword(password);
                usuarios.add(usuario);
            }
//        } catch (SQLException ex) {
//            ex.printStackTrace(System.out);
        } finally {
                close(rs); //Los objetos se cierran en forma inversa a como fueron abiertos.
                close(stmt);
                if (conexionTransaccional == null) {
                    Conexion.close(conn);
                }
        }
        return usuarios;
    }
    
    public int insertar(Usuario usuario) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setString(1, usuario.getUsername());
            stmt.setString(2, usuario.getPassword());
            System.out.println("Ejecutando query: " + SQL_INSERT);
            registros = stmt.executeUpdate();
            System.out.println("Registros afectados: " + registros);
//        } catch (SQLException ex) {
//            ex.printStackTrace(System.out);
        }
        finally {
                close(stmt);
                if (conexionTransaccional == null) {
                    Conexion.close(conn);
                }
        }
        return registros;
    }
    
    public int actualizar(Usuario usuario) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setString(1, usuario.getUsername());
            stmt.setString(2, usuario.getPassword());
            stmt.setInt(3, usuario.getIdusuario());
            registros = stmt.executeUpdate();
            System.out.println("Registros afectados: " + registros);
//        } catch (SQLException ex) {
//            ex.printStackTrace(System.out);
        }
        finally {
                close(stmt);
                if (conexionTransaccional == null) {
                Conexion.close(conn);
            }
        }
        return registros;
    }
    
    public int eliminar(Usuario usuario) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, usuario.getIdusuario());
            registros = stmt.executeUpdate();
            System.out.println("Registros afectados: " + registros);
//        } catch (SQLException ex) {
//            ex.printStackTrace(System.out);
        }
        finally {
                close(stmt);
                if (conexionTransaccional == null) {
                    Conexion.close(conn);
            }
        }
        return registros;
    }
}
