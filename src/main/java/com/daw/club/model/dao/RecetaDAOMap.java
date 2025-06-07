package com.daw.club.model.dao;

import com.daw.club.model.Receta;
import com.daw.club.qualifiers.DAOMap;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
@DAOMap
public class RecetaDAOMap implements RecetaDAO {

    private final Map<Long, Receta> recetas = new HashMap<>();
    private long currentId = 1;

    public RecetaDAOMap() {
        // Constructor vacío necesario para CDI
    }

    @Override
    public boolean existenRecetas() {
        return false;
    }

    @Override
    public void borrarTodos(){
        List<Receta> recetas = listarTodos();
        for(int i=0;i<recetas.size();i++){
            eliminar(recetas.get(i).getId());
        }
    }

    @Override
    public List<Receta> listarTodos() {
        System.out.println("RecetaDAOMap.listarTodos(): Devolviendo " + recetas.size() + " recetas");
        return recetas.values().stream().collect(Collectors.toList());
    }

    @Override
    public Receta buscarPorId(int id) {
        return null;
    }

    @Override
    public Receta buscarPorId(Long id) {
        return recetas.get(id);
    }

    @Override
    public boolean guardar(Receta receta) {
        if (receta.getId() == 0) {
            receta.setId((int) currentId++);
        }
        recetas.put(receta.getId(), receta);
        return true;
    }

    @Override
    public Receta crea(Receta receta) {
        receta.setId((int) currentId++);
        recetas.put(receta.getId(), receta);
        return receta;
    }

    @Override
    public boolean actualizar(Receta receta) {
        if (receta.getId() != 0 && recetas.containsKey(receta.getId())) {
            recetas.put(receta.getId(), receta);
            return true;
        }
        return false;
    }

    @Override
    public boolean eliminar(int id) {
        return false;
    }

    @Override
    public boolean eliminar(Long id) {
        return recetas.remove(id) != null;
    }
}