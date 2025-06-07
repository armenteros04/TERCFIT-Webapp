package com.daw.club.model.dao;

import com.daw.club.model.Biometria;
import com.daw.club.qualifiers.DAOJpa;
import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Dependent
@DAOJpa
@Transactional
public class BiometriaDAOJPA implements BiometriaDAO, Serializable {

    private final Logger logger = Logger.getLogger(BiometriaDAOJPA.class.getName());

    @PersistenceContext(unitName = "ClubPU")
    private EntityManager em;

    public BiometriaDAOJPA() {}

    @Override
    public List<Biometria> listarTodos() {
        List<Biometria> biometrías = null;
        try {
            Query q = em.createQuery("SELECT b FROM Biometria b", Biometria.class);
            biometrías = (List<Biometria>) q.getResultList();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return biometrías;
    }

    @Override
    public Biometria buscarPorId(Long id) {
        Biometria b = null;
        try {
            b = em.find(Biometria.class, id);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return b;
    }

    @Override
    public boolean guardar(Biometria biometria) {
        boolean guardado = false;
        try {
            em.persist(biometria);
            guardado = true;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return guardado;
    }

    @Override
    public boolean actualizar(Biometria biometria) {
        boolean actualizado = false;
        try {
            em.merge(biometria);
            actualizado = true;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return actualizado;
    }

    @Override
    public boolean eliminar(Long id) {
        boolean eliminado = false;
        try {
            Biometria biometria = buscarPorId(id);
            if (biometria != null) {
                em.remove(biometria);
                eliminado = true;
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return eliminado;
    }
}

