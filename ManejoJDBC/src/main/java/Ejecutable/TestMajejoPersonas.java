package Ejecutable;

import datos.PersonaDAO;
import domain.Persona;
import java.util.List;

public class TestMajejoPersonas {
    public static void main(String[] args) {
        PersonaDAO personaDao = new PersonaDAO();
        
        
        //Insertamos un nuevo objeto de tipo Persona
        Persona otraPersona = new Persona("Philip", "Gibbs", "phg@mail.com", "21453698");
        personaDao.insertar(otraPersona);
        
        List<Persona> personas = personaDao.seleccionar();
        personas.forEach(persona -> {
            System.out.println("persona = " + persona);
        });
    }
}
