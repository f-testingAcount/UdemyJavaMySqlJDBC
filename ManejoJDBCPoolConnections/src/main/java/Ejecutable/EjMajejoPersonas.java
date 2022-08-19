package Ejecutable;

import datos.Conexion;
import datos.PersonaDAO;
import domain.Persona;
import java.sql.*;

public class EjMajejoPersonas {
    public static void main(String[] args) {
        
        Connection conexion = null;
        
        try {
            conexion = Conexion.getConnection();
            if (conexion.getAutoCommit()) {
                conexion.setAutoCommit(false);
            }
            
            PersonaDAO personaDao = new PersonaDAO(conexion);

            Persona cambioPersona = new Persona();
            cambioPersona.setIdPersona(2);
            cambioPersona.setNombre("Paul");
//            cambioPersona.setApellido("Schuartz11111111111111111111111111111111111111111111111");
            cambioPersona.setApellido("Schuartz");
            cambioPersona.setEmail("ps@mail.com");
            cambioPersona.setTelefono("32658965");
            personaDao.actualizar(cambioPersona);
            
            Persona otraPersona = new Persona();
            otraPersona.setNombre("Catherine");
            otraPersona.setApellido("Fox");
            otraPersona.setEmail("cf@mail.com");
            otraPersona.setTelefono("32659878");
            personaDao.insertar(otraPersona);
            
            conexion.commit();

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            System.out.println("Ejecutado Rollback");
            try {
                conexion.rollback();
            } catch (SQLException ex1) {
                ex1.printStackTrace(System.out);
            }
        }
    }
}
