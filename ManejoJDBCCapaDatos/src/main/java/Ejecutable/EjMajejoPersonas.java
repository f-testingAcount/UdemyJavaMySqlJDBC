package Ejecutable;

import datos.Conexion;
import datos.PersonaDaoJDBC;
import domain.PersonaDTO;
import java.sql.*;
import java.util.List;

public class EjMajejoPersonas {
    public static void main(String[] args) {
        
        Connection conexion = null;
        
        try {
            conexion = Conexion.getConnection();
            if (conexion.getAutoCommit()) {
                conexion.setAutoCommit(false);
            }
            
            PersonaDaoJDBC personaDao = new PersonaDaoJDBC(conexion);
            
            List<PersonaDTO> personas = personaDao.seleccionar();
            for (PersonaDTO persona : personas) {
                System.out.println("Persona DTO = " + persona);
            }
            
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
