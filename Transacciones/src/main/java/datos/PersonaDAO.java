package datos;

import static datos.Conexion.*;
import domain.Persona;
import java.sql.*;
import java.util.*;

public class PersonaDAO { //DAO (data access object)

    private Connection conexionTransaccional; //Para transacciones manejamos la conexion desde una clase externa y esta clase recibe un objeto conexion provisto desde otra clase
    private static final String SQL_SELECT = "SELECT idpersona, nombre, apellido, email, telefono FROM persona"; // Para simplificar esta sentencia se puede colocar SELECT * FROM persona
    private static final String SQL_INSERT = "INSERT INTO persona(nombre, apellido, email, telefono) VALUES(?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE persona SET nombre = ?, apellido = ?, email = ?, telefono = ? WHERE idpersona = ?";
    private static final String SQL_DELETE = "DELETE FROM persona WHERE idpersona = ?";

    public PersonaDAO() {}

    public PersonaDAO(Connection conexionTransaccional) { //Este constructor recibe el objeto de tipo Connection externo a esta clase
        this.conexionTransaccional = conexionTransaccional;
    }

    public List<Persona> seleccionar() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null; //PreparedStatement es una interface
        ResultSet rs = null;
        Persona persona = null;
        List<Persona> personas = new ArrayList<>();

        try {
            //conn = getConnection(); //Tenemos importado el static de clase Conexion.getconection() para evitar citar la clase
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection(); //Si el objeto conexion 
            //no es nulo lo utilizamos si no obtenemos una nueva conexion y en tal caso esta conexion debe cerrarce dentro de este metodo
            //si la conexion es un objeto externo no debe cerrarce aqui pq cerraria toda la transaccion.
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
//        } catch (SQLException ex) { //Eliminamos el bloque catch para propagar la excepcion y procesarla en la clase que ejecuta la transaccion. Igoal en todos los metodos subsiguientes
//            ex.printStackTrace(System.out);
        } finally {
                close(rs); //Los objetos se cierran en forma inversa a como fueron abiertos.
                close(stmt);
                if (this.conexionTransaccional == null) { //Indicamos desde donde se cierra segun el tipo de conexion ejectuada (interna de la clase o ejterna a traves de un objeto de otra clase)
                    Conexion.close(conn);
                }
        }
        return personas;
    }

    public int insertar(Persona persona) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;
        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setString(1, persona.getNombre());
            stmt.setString(2, persona.getApellido());
            stmt.setString(3, persona.getEmail());
            stmt.setString(4, persona.getTelefono());
            System.out.println("Ejecutando query: " + SQL_INSERT);
            registros = stmt.executeUpdate();
            System.out.println("Registros afectados: " + registros);
//        } catch (SQLException ex) {
//            ex.printStackTrace(System.out);
        } finally {
                close(stmt);
                if (this.conexionTransaccional == null) { //Indicamos desde donde se cierra segun el tipo de conexion ejectuada (interna de la clase o ejterna a traves de un objeto de otra clase)
                    close(conn);
                }
        }
        return registros;
    }

    public int actualizar(Persona persona) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;
        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setString(1, persona.getNombre());
            stmt.setString(2, persona.getApellido());
            stmt.setString(3, persona.getEmail());
            stmt.setString(4, persona.getTelefono());
            stmt.setInt(5, persona.getIdPersona());
            System.out.println("Ejecutando query: " + SQL_INSERT);
            registros = stmt.executeUpdate();
            System.out.println("Registros afectados: " + registros);
//        } catch (SQLException ex) {
//            ex.printStackTrace(System.out);
        } finally {
            close(stmt);
            if (this.conexionTransaccional == null) { //Indicamos desde donde se cierra segun el tipo de conexion ejectuada (interna de la clase o ejterna a traves de un objeto de otra clase)
                Conexion.close(conn);
            }
        }
        return registros;
    }

    public int eliminar(Persona persona) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;
        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, persona.getIdPersona());
            System.out.println("Executed query " + SQL_DELETE);
            registros = stmt.executeUpdate();
            System.out.println("Registros eliminados: " + registros);
//        } catch (SQLException ex) {
//            ex.printStackTrace(System.out);
        } finally {
            close(stmt);
            if (this.conexionTransaccional == null) { //Indicamos desde donde se cierra segun el tipo de conexion ejectuada (interna de la clase o ejterna a traves de un objeto de otra clase)
                Conexion.close(conn);
            }
        }
        return registros;
    }
}
