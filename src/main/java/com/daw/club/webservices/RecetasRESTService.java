package com.daw.club.webservices;

import com.daw.club.model.Receta;
import com.daw.club.model.dao.RecetaDAO;
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
 * Recetas JSON REST Web Service
 *
 */
@Path("recetas")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
//@RolesAllowed({"USUARIOS","ADMINISTRADORES"}) //By default, only authenticated users can access Endpoints
public class RecetasRESTService {

    @PostConstruct
    public void init() {
        // Inicializar algunas recetas de ejemplo
        if(recetaDAO.listarTodos().isEmpty()) {
            recetaDAO.crea(new Receta("Avena Moca Proteica", "Copos de avena, cacao en polvo, café instantáneo, semillas de chía, proteína en polvo, leche, endulzante", "Mezclar ingredientes y refrigerar por 4 horas.", "Rica en proteínas y fibra", 350, 20, 8, 50, 10, "avenaMoca.png"));
            recetaDAO.crea(new Receta("Pollo Tex-Mex con Quinoa", "Quinoa, yogur, aguacate, lima, chile chipotle, pollo, alubias negras, maíz, tomate cherry, cebolleta, espinacas, semillas de calabaza", "Cocinar quinoa y mezclar con los demás ingredientes.", "Plato completo con proteínas y fibra", 540, 45, 15, 55, 12, "estofado.png"));
            recetaDAO.crea(new Receta("Tortitas de Plátano y Avena", "Plátano, huevos, avena, polvo de hornear, canela, sal, aceite", "Triturar plátano y mezclar con los demás ingredientes. Cocinar en sartén.", "Alternativa saludable y energética", 300, 12, 7, 50, 6, "tortitas.png"));
            recetaDAO.crea(new Receta("Ensalada de Quinoa y Verduras", "Quinoa, pimiento, calabacín, berenjena, aceite de oliva, garbanzos, espinaca, limón", "Asar verduras y mezclar con quinoa y garbanzos.", "Fuente de fibra y proteínas vegetales", 400, 15, 12, 60, 10, "quinoa.png"));        }
    }

    @Context
    private UriInfo context;

    @Inject     @DAOMap
    RecetaDAO recetaDAO;

    public RecetasRESTService() {
    }

    @GET
    public List<Receta> getRecetas() {
        return recetaDAO.listarTodos();
    }

    @GET
    @Path("/{id}")
    public Response getReceta(@PathParam("id") int id) {
        Response response;
        Receta r = recetaDAO.buscarPorId(id);
        if (r != null) {
            response = Response.ok(r).build();
        } else {
            //Error messages (using Map for create generic json objects)
            List<Map<String,Object>> errores = new ArrayList<>();
            Map<String,Object> err = new HashMap<>();
            err.put("message", "La receta no existe");
            errores.add(err);
            response = Response.status(Response.Status.BAD_REQUEST)
                    .entity(errores).build();
        }
        return response;
    }

    @DELETE
    @Path("/{id}")
    //@RolesAllowed("ADMINISTRADORES") //Only admins can delete recipes
    public Response borraReceta(@PathParam("id") int id) {
        Response response;

        if (recetaDAO.eliminar(id) == true) {
            response = Response.ok(id).build();
        } else {
            //Error messages (using Map for create generic json objects)
            List<Map<String,Object>> errores = new ArrayList<>();
            Map<String,Object> err = new HashMap<>();
            err.put("message", "La receta no existe");
            errores.add(err);
            response = Response.status(Response.Status.BAD_REQUEST)
                    .entity(errores).build();
        }

        return response;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response creaReceta(@Valid Receta r) {
        Response response;
        if (recetaDAO.crea(r) != null) {
            response = Response.ok(r).build();
        } else {
            //Error messages (using Map for create generic json objects)
            List<Map<String,Object>> errores = new ArrayList<>();
            Map<String,Object> err = new HashMap<>();
            err.put("message", "No se ha podido crear la receta");
            err.put("receta", r);
            errores.add(err);
            response = Response.status(Response.Status.BAD_REQUEST)
                    .entity(errores).build();
        }
        return response;
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response modificaReceta(@Valid Receta r, @PathParam("id") Integer id) {
        Response response;
        r.setId(id);
        if (recetaDAO.guardar(r)) {
            response = Response.ok(r).build();
        } else {
            //Error messages (using Map for create generic json objects)
            List<Map<String,Object>> errores = new ArrayList<>();
            Map<String,Object> err = new HashMap<>(); //Error messages
            err.put("message", "No se ha podido modificar la receta");
            err.put("receta", r);
            errores.add(err);
            response = Response.status(Response.Status.BAD_REQUEST)
                    .entity(errores).build();
        }
        return response;
    }
}