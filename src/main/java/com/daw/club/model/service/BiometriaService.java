package com.daw.club.model.service;

import com.daw.club.model.Biometria;
import com.daw.club.model.dao.BiometriaDAOJPA;
import com.daw.club.qualifiers.DAOJpa;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class BiometriaService {

    @Inject @DAOJpa
    private BiometriaDAOJPA biometriaDAO;

    public List<Biometria> obtenerTodas() {
        return biometriaDAO.listarTodos();
    }

    public Biometria obtenerPorId(Long id) {
        return biometriaDAO.buscarPorId(id);
    }

    public void crear(Biometria biometria) {
        biometriaDAO.guardar(biometria);
    }

    public void actualizar(Biometria biometria) {
        biometriaDAO.actualizar(biometria);
    }

    public void eliminar(Long id) {
        biometriaDAO.eliminar(id);
    }
}
