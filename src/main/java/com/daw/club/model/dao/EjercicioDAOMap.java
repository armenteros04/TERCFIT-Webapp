package com.daw.club.model.dao;

import com.daw.club.model.Ejercicio;
import com.daw.club.model.GrupoMuscular;
import com.daw.club.qualifiers.DAOMap;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
@DAOMap
public class EjercicioDAOMap implements EjercicioDAO {

    private final Map<Long, Ejercicio> ejercicios = new HashMap<>();
    private long currentId = 1;

    public EjercicioDAOMap() {
        // Constructor vacío necesario para CDI
    }
    @Override
    public void borrarTodos(){
        List<Ejercicio> ejercicios = listarTodos();
        for(int i=0;i<ejercicios.size();i++){
            eliminar(ejercicios.get(i).getId());
        }
    }

    @Override
    public List<Ejercicio> listarTodos() {
        System.out.println("EjercicioDAOMap.listarTodos(): Devolviendo " + ejercicios.size() + " ejercicios");
        return ejercicios.values().stream().collect(Collectors.toList());
    }

    @Override
    public Ejercicio buscarPorId(Long id) {
        return ejercicios.get(id);
    }

    @Override
    public boolean guardar(Ejercicio ejercicio) {
        if (ejercicio.getId() == null) {
            ejercicio.setId(currentId++);
        }
        ejercicios.put(ejercicio.getId(), ejercicio);
        return true;
    }


    @Override
    public Ejercicio crea(Ejercicio ejercicio) {
        ejercicio.setId(currentId++);
        ejercicios.put(ejercicio.getId(), ejercicio);
        return ejercicio;
    }

    @Override
    public boolean actualizar(Ejercicio ejercicio) {
        if (ejercicio.getId() != null && ejercicios.containsKey(ejercicio.getId())) {
            ejercicios.put(ejercicio.getId(), ejercicio);
            return true;
        }
        return false;
    }

    @Override
    public boolean eliminar(Long id) {
        return ejercicios.remove(id) != null;
    }


}