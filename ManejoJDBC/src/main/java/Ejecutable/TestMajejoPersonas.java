package Ejecutable;

import datos.PersonaDAO;
import domain.Persona;
import java.util.List;

public class TestMajejoPersonas {
    public static void main(String[] args) {
        PersonaDAO personaDao = new PersonaDAO();
        
        
        //Insertamos un nuevo objeto de tipo Persona
//        Persona otraPersona = new Persona("Megan", "Taylor", "mt@mail.com", "32325665");
//        personaDao.insertar(otraPersona);
        
        //Modificar un objeto de persona existente
//        Persona personaModificada = new Persona(4, "Philip Antoni", "Gibbs", "pag@mail.com", "47647364");
//        personaDao.actualizar(personaModificada);

        //Eliminar un registro
        Persona elminarRegistro = new Persona(4);
        personaDao.eliminar(elminarRegistro);
        
        //Listar los objetos personas de la base de datos
        List<Persona> personas = personaDao.seleccionar();
        personas.forEach(persona -> {
            System.out.println("persona = " + persona);
        });
    }
}
