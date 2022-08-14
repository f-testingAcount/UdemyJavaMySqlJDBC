package datos;

import static datos.Conexion.*;
import domain.Persona;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PersonaDAO { //DAO (data access object)

    private static final String SQL_SELECT = "SELECT idpersona, nombre, apellido, email, telefono FROM persona"; // Para simplificar esta sentencia se puede colocar SELECT * FROM persona
    private static final String SQL_INSERT = "INSERT INTO persona(nombre, apellido, email, telefono) VALUES(?, ?, ?, ?)";
    public List<Persona> seleccionar() {
        Connection conn = null;
        PreparedStatement stmt = null; //PreparedStatement es una interface
        ResultSet rs = null;
        Persona persona = null;
        List<Persona> personas = new ArrayList<>();

        try {
            conn = getConnection(); //Tenemos importado el static de clase Conexion.getconection() para evitar citar la clase
            stmt = conn.prepareStatement(SQL_SELECT); //preparedStatement es un metodo implementado de la interface PreparedStatement
            rs = stmt.executeQuery(); //con la variable resultado ejecutamos la consulta
            while (rs.next()) {
                int idPersona = rs.getInt("idpersona");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String email = rs.getString("email");
                String telefono = rs.getString("telefono");
                persona = new Persona(idPersona, nombre, apellido, email, telefono); //Convertimos informacion de la base de datos en objetos java (en este caso de tipo Persona)
                //Esta informacion puede ser utilizada y reutilizada por otros sistemas.
                //En frameworks como hybernetes y jpa ejecutan esto internamente. En jdbc vemos la mecanica utilizada por esos sistemas
                personas.add(persona);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            try {
                close(rs); //Los objetos se cierran en forma inversa a como fueron abiertos.
                close(stmt);
                close(conn);
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
        }
        return personas;
    }
    
    public int insertar(Persona persona) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setString(1, persona.getNombre());
            stmt.setString(2, persona.getApellido());
            stmt.setString(3, persona.getEmail());
            stmt.setString(4, persona.getTelefono());
            registros = stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        finally {
            try {
                close(stmt);
                close(conn);
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
        }
        return registros;
    }
}
