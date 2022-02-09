@RestController
public class AlumnosController{

@GetMapping("/alumnos")
public String getAlumnos(){
return "Hugo, Paco, Luis";
}
}
