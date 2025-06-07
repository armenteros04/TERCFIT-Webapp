package com.daw.club.model.service;

import com.daw.club.model.Receta;
import com.daw.club.model.dao.RecetaDAO;
import com.daw.club.qualifiers.DAOJpa;
import jakarta.inject.Inject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class RecetaService {

    @Inject @DAOJpa
    private RecetaDAO recetaDAO;

    public List<Receta> obtenerTodos() {
        return recetaDAO.listarTodos();
    }

    public void crear(Receta receta) {
        recetaDAO.guardar(receta);
    }

    public void actualizar(Receta receta) {
        recetaDAO.actualizar(receta);
    }

    public void eliminar(int id) {
        recetaDAO.eliminar(id);
    }
}