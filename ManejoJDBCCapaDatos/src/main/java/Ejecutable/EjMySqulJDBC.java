
package Ejecutable;

import java.sql.*;


public class EjMySqulJDBC {  //jdbc:mysql://localhost:3307/test?useTimezone=true&serverTimezone=UTC
    public static void main(String[] args) { //localhosts es igual a colocar 127.0.0.1
        //String url = "jdbc:mysql://localhost:3306/testprueba?useSSL=false&useTimezone=true&serverTimezoneUTC&allowPublicKeyRetrieval=true";
        String url = "jdbc:mysql://localhost:3306/testprueba?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC";
       //createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC

        try {                                             //useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
            //Class.forName("com.mysql.cj.jdbc.Driver"); //Esta linea ya no es necesaria en las ultimas versiones. En apps locales no es necesaria pero podria requerirse en apps web.
            Connection conexion = DriverManager.getConnection(url, "root", "adminmin23");
            Statement instruccion = conexion.createStatement(); //El objeto de tipo Statement nos permite ejecutar sentencias en nuestra table de base de datos
                                                                //Es un tipo interface que nos devuelve la implementacion dependiendo del conector a base de datos que estemos utilizando (en este caso el conector de mysql) 
                                                                //Del lado derecho de la igualdad estamos realizando una implementacion (en este caso la de mysqul). El mismo metodo puede existir para cualquier otra base de datos.
                                                                //Para oracle, sqlsever, etc es exactamente el mismo codigo. 
                                                                //Del lado derecho nos devuelve una clase concreta que implementa una iterface y de lado izquierdo simplemente estamos utilizando un tipo interface.
            String sql = "SELECT idpersona, nombre, apellido, email, telefono FROM persona";
            ResultSet resultado = instruccion.executeQuery(sql); //ResultSet tambien es un tipo interface que la utilizamod para ejectuar esta instruccion
            while(resultado.next()) { //executeQuery puede devolver mas de un registro por lo cual es necesario iterar
                                      //El metodo next() (booleano) nos indica si todavia hay un elemento por iterar devolviendo true or false
                                      //De ser true se posiciona sobre el elemnto a iterar
                System.out.print("Id persona: " + resultado.getInt("idpersona"));
                System.out.print(" Nombre: " + resultado.getString("nombre"));
                System.out.print(" Apellido: " + resultado.getString("apellido"));
                System.out.print(" Email: " + resultado.getString("email"));
                System.out.print(" Telefono: " + resultado.getString("telefono"));
                System.out.println(" ");
            }
            resultado.close();
            instruccion.close();
            conexion.close();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } 
//        catch (ClassNotFoundException ex) { //Excepcion de Class.forname
//            ex.printStackTrace(System.out);
//        }
    }
}
