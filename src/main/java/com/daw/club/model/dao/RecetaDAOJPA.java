package com.daw.club.model.dao;

import com.daw.club.model.Receta;
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

@Dependent // Elegible para inyección de dependencias
@DAOJpa
@Transactional // Manejo automático de transacciones
public class RecetaDAOJPA implements RecetaDAO, Serializable {

    private final Logger logger = Logger.getLogger(RecetaDAOJPA.class.getName());

    @PersistenceContext(unitName = "ClubPU") // Solo para servidores JEE completos
    private EntityManager em;

    public RecetaDAOJPA() {
    }

    @Override
    public boolean existenRecetas() {
        boolean existen = false;
        try {
            Long count = em.createQuery("SELECT COUNT(r) FROM Receta r", Long.class).getSingleResult();
            existen = count > 0;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return existen;
    }

    @Override
    public void borrarTodos() {

    }

    @Override
    public Receta crea(Receta receta) {
        try {
            em.persist(receta);
            em.flush(); // Forzar la ejecución inmediata para obtener el ID generado
            return receta;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        }
    }

    @Override
    public boolean eliminar(Long id) {
        return false;
    }

    @Override
    public List<Receta> listarTodos() {
        List<Receta> recetas = null;
        try {
            Query q = em.createQuery("SELECT r FROM Receta r", Receta.class);
            recetas = q.getResultList();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return recetas;
    }

    @Override
    public Receta buscarPorId(int id) {
        Receta receta = null;
        try {
            receta = em.find(Receta.class, id);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return receta;
    }

    @Override
    public Receta buscarPorId(Long id) {
        return null;
    }

    @Override
    public boolean guardar(Receta receta) {
        boolean guardado = false;
        try {
            em.persist(receta);
            guardado = true;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return guardado;
    }

    @Override
    public boolean actualizar(Receta receta) {
        boolean actualizado = false;
        try {
            em.merge(receta);
            actualizado = true;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return actualizado;
    }

    @Override
    public boolean eliminar(int id) {
        boolean eliminado = false;
        try {
            Receta receta = buscarPorId(id);
            if (receta != null) {
                em.remove(receta);
                eliminado = true;
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return eliminado;
    }
}
