package com.daw.club.model.dao;

import com.daw.club.model.Cliente;
import com.daw.club.model.Ejercicio;
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
public class EjercicioDAOJPA implements EjercicioDAO, Serializable {

    private final Logger logger = Logger.getLogger(EjercicioDAOJPA.class.getName());

    @PersistenceContext(unitName = "ClubPU") // Solo para servidores JEE completos
    private EntityManager em;

    public EjercicioDAOJPA() {
    }

    public void borrarTodos(){
        List<Ejercicio> ejercicios = listarTodos();
        for(int i=0;i<ejercicios.size();i++){
            eliminar(ejercicios.get(i).getId());
        }
    }

    public List<Ejercicio> listarTodos() {
        List<Ejercicio> ejercicios = null;
        try {
            Query q = em.createQuery("SELECT e FROM Ejercicio e", Ejercicio.class);
            ejercicios = (List<Ejercicio>) q.getResultList();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return ejercicios;
    }

    public Ejercicio buscarPorId(Long id) {
        Ejercicio e = null;
        try {
            e = em.find(Ejercicio.class, id);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return e;
    }

    public boolean guardar(Ejercicio ejercicio) {
        boolean guardado = false;
        try {
            em.persist(ejercicio);
            guardado = true;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return guardado;
    }

    @Override
    public Ejercicio crea(Ejercicio ejercicio) {
        try {
            em.persist(ejercicio);
            em.flush(); // Forzar la ejecución inmediata para obtener el ID generado
            return ejercicio;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            return null;
        }
    }

    public boolean actualizar(Ejercicio ejercicio) {
        boolean actualizado = false;
        try {
            em.merge(ejercicio);
            actualizado = true;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return actualizado;
    }

    public boolean eliminar(Long id) {
        boolean eliminado = false;
        try {
            Ejercicio ejercicio = buscarPorId(id);
            if (ejercicio != null) {
                em.remove(ejercicio);
                eliminado = true;
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return eliminado;
    }

    public boolean anadirFavorito(Integer clienteId, Long ejercicioId) {
        boolean exito = false;
        try {
            Cliente cliente = em.find(Cliente.class, clienteId);
            Ejercicio ejercicio = em.find(Ejercicio.class, ejercicioId);
            if (cliente != null && ejercicio != null) {
                cliente.getFavoritos().add(ejercicio);
                em.merge(cliente);
                exito = true;
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return exito;
    }

    public boolean quitarFavorito(Integer clienteId, Long ejercicioId) {
        boolean exito = false;
        try {
            Cliente cliente = em.find(Cliente.class, clienteId);
            Ejercicio ejercicio = em.find(Ejercicio.class, ejercicioId);
            if (cliente != null && ejercicio != null) {
                cliente.getFavoritos().remove(ejercicio);
                em.merge(cliente);
                exito = true;
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return exito;
    }

    public List<Ejercicio> listarFavoritos(Integer clienteId) {
        List<Ejercicio> favoritos = null;
        try {
            Cliente cliente = em.find(Cliente.class, clienteId);
            if (cliente != null) {
                favoritos = List.copyOf(cliente.getFavoritos());
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return favoritos;
    }
}