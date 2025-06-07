    package com.daw.club.webservices;

    import com.daw.club.model.Ejercicio;
    import com.daw.club.model.GrupoMuscular;
    import com.daw.club.model.dao.EjercicioDAO;
    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

    import com.daw.club.qualifiers.DAOMap;
    import jakarta.annotation.PostConstruct;
    import jakarta.enterprise.context.RequestScoped;
    import jakarta.inject.Inject;
    import jakarta.validation.Valid;
    import jakarta.ws.rs.core.Context;
    import jakarta.ws.rs.core.UriInfo;
    import jakarta.ws.rs.Produces;
    import jakarta.ws.rs.Consumes;
    import jakarta.ws.rs.DELETE;
    import jakarta.ws.rs.GET;
    import jakarta.ws.rs.POST;
    import jakarta.ws.rs.Path;
    import jakarta.ws.rs.PUT;
    import jakarta.ws.rs.PathParam;
    import jakarta.ws.rs.core.MediaType;
    import jakarta.ws.rs.core.Response;

    /**
     * Ejercicios JSON REST Web Service
     *
     */
    @Path("ejercicios")
    @Produces(MediaType.APPLICATION_JSON)
    @RequestScoped
    //@RolesAllowed({"USUARIOS","ADMINISTRADORES"}) //By default, only authenticated users can access Endpoints
    public class EjerciciosRESTService {

        @PostConstruct
        public void init() {
            // Inicializar algunos ejercicios de ejemploç
            if(ejercicioDAO.listarTodos().isEmpty()){
            ejercicioDAO.crea(new Ejercicio("Curl Predicador", "Ejercicio en barra para ejercitar la cabeza interna del bíceps", "curlpredicador.mp4", GrupoMuscular.BICEPS));
            ejercicioDAO.crea(new Ejercicio("Press de Banca", "Ejercicio para pectorales", "pressbanca.mp4", GrupoMuscular.PECHO));
            ejercicioDAO.crea(new Ejercicio("Peso muerto", "Ideal para hacer femoral o fortalecer espalda", "pesomuerto.mp4", GrupoMuscular.PIERNA));
            ejercicioDAO.crea(new Ejercicio("Dominada", "Ejercicio fundamental para espalda sin peso", "dominada.mp4", GrupoMuscular.PIERNA));
            ejercicioDAO.crea(new Ejercicio("Press Frances", "Ejercicio tumbado para trabajar los triceps", "pressfrances.mp4", GrupoMuscular.PIERNA));
            ejercicioDAO.crea(new Ejercicio("Sentadilla", "Ejercicio compuesto para piernas", "sentadilla.mp4", GrupoMuscular.PIERNA));
        }}

        @Context
        private UriInfo context;

        @Inject     @DAOMap
        EjercicioDAO ejercicioDAO;

        public EjerciciosRESTService() {
        }

        @GET
        public List<Ejercicio> getEjercicios() {
            return ejercicioDAO.listarTodos();
        }

        @GET
        @Path("/{id}")
        public Response getEjercicio(@PathParam("id") int id) {
            Response response;
            Ejercicio e = ejercicioDAO.buscarPorId((long) id);
            if (e != null) {
                response = Response.ok(e).build();
            } else {
                //Error messages (using Map for create generic json objects)
                List<Map<String,Object>> errores = new ArrayList<>();
                Map<String,Object> err = new HashMap<>();
                err.put("message", "El ejercicio no existe");
                errores.add(err);
                response = Response.status(Response.Status.BAD_REQUEST)
                        .entity(errores).build();
            }
            return response;
        }

        @DELETE
        @Path("/{id}")
        //@RolesAllowed("ADMINISTRADORES") //Only admins can delete exercises
        public Response borraEjercicio(@PathParam("id") int id) {
            Response response;

            if (ejercicioDAO.eliminar((long) id) == true) {
                response = Response.ok(id).build();
            } else {
                //Error messages (using Map for create generic json objects)
                List<Map<String,Object>> errores = new ArrayList<>();
                Map<String,Object> err = new HashMap<>();
                err.put("message", "El ejercicio no existe");
                errores.add(err);
                response = Response.status(Response.Status.BAD_REQUEST)
                        .entity(errores).build();
            }

            return response;
        }

        @POST
        @Consumes(MediaType.APPLICATION_JSON)
        public Response creaEjercicio(@Valid Ejercicio e) {
            Response response;
            if (ejercicioDAO.crea(e) == null) {
                Integer newId = Math.toIntExact(e.getId());
                response = Response.ok(e).build();
            } else {
                //Error messages (using Map for create generic json objects)
                List<Map<String,Object>> errores = new ArrayList<>();
                Map<String,Object> err = new HashMap<>();
                err.put("message", "No se ha podido crear el ejercicio");
                err.put("ejercicio", e);
                errores.add(err);
                response = Response.status(Response.Status.BAD_REQUEST)
                        .entity(errores).build();
            }
            return response;
        }

        @PUT
        @Path("/{id}")
        @Consumes(MediaType.APPLICATION_JSON)
        public Response modificaEjercicio(@Valid Ejercicio e, @PathParam("id") Integer id) {
            Response response;
            e.setId(Long.valueOf(id));
            if (ejercicioDAO.guardar(e)) {
                response = Response.ok(e).build();
            } else {
                //Error messages (using Map for create generic json objects)
                List<Map<String,Object>> errores = new ArrayList<>();
                Map<String,Object> err = new HashMap<>(); //Error messages
                err.put("message", "No se ha podido modificar el ejercicio");
                err.put("ejercicio", e);
                errores.add(err);
                response = Response.status(Response.Status.BAD_REQUEST)
                        .entity(errores).build();
            }
            return response;
        }
    }