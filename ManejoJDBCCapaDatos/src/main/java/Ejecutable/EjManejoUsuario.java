package Ejecutable;

import datos.UsuarioDAO;
import domain.Usuario;
import java.sql.Connection;
import datos.Conexion;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EjManejoUsuario {
    public static void main(String[] args) {
        
        Connection conexion = null;
        try {
            conexion = Conexion.getConnection();
            if (conexion.getAutoCommit()) {
                conexion.setAutoCommit(false);
            }
            
            UsuarioDAO usuarioDao = new UsuarioDAO(conexion);
            
            Usuario cambioUsuario = new Usuario();
            cambioUsuario.setIdusuario(2);
            cambioUsuario.setUsername("TomasJ.taylor");
//            cambioUsuario.setApellido("Schuartz11111111111111111111111111111111111111111111111");
            cambioUsuario.setPassword("963");
            usuarioDao.actualizar(cambioUsuario);
            
            Usuario otroUsuario = new Usuario();
            otroUsuario.setUsername("Jeniffer.keghan");
            otroUsuario.setPassword("258");
            usuarioDao.insertar(otroUsuario);
            
            conexion.commit();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Ejecutando rollback");
            try {
                conexion.rollback();
            } catch (SQLException ex1) {
                ex1.printStackTrace(System.out);
            }
        }
    }
}
