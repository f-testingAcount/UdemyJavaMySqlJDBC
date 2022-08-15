package Ejecutable;

import datos.UsuarioJDBC;
import domain.Usuario;
import java.util.List;

public class ManejoUsuario {
    public static void main(String[] args) {
        UsuarioJDBC usuarioJdbc = new UsuarioJDBC();
        
        
        
        //Insertar un nuevo usuario
//        Usuario usuario = new Usuario("Karl.lager", "789");
//        usuarioJdbc.insertar(usuario);

        //Modificar usuario existente
//        Usuario otroUsuario = new Usuario(3, "Karl.lager", "987");
//        usuarioJdbc.actualizar(otroUsuario);

        usuarioJdbc.eliminar(new Usuario(3));
        
        //Ejecutando el listado de usuarios
        List<Usuario> usuarios = usuarioJdbc.seleccionar();
        for (Usuario usuario : usuarios) {
            System.out.println("usuario = " + usuario);
        }
    }
}
