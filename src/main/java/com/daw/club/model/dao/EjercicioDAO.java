package com.daw.club.model.dao;

import com.daw.club.model.Ejercicio;
import java.util.List;

public interface EjercicioDAO {
    /**
     * Borra todos los ejercicios del dao.
     */
    public void borrarTodos();

    /**
     * Obtiene la lista de todos los ejercicios.
     * @return Lista de ejercicios
     */
    List<Ejercicio> listarTodos();

    /**
     * Busca un ejercicio por su ID.
     * @param id Identificador del ejercicio
     * @return Objeto Ejercicio si se encuentra, null si no
     */
    Ejercicio buscarPorId(Long id);

    /**
     * Guarda un nuevo ejercicio en la base de datos.
     * @param ejercicio Objeto Ejercicio a guardar
     * @return true si se guardó correctamente, false si hubo un error
     */
    boolean guardar(Ejercicio ejercicio);

    /**
     * Actualiza un ejercicio existente en la base de datos.
     * @param ejercicio Objeto Ejercicio con los datos actualizados
     * @return true si se actualizó correctamente, false si hubo un error
     */
    boolean actualizar(Ejercicio ejercicio);

    /**
     * Elimina un ejercicio por su ID.
     * @param id Identificador del ejercicio a eliminar
     * @return true si se eliminó correctamente, false si hubo un error
     */
    boolean eliminar(Long id);

    Ejercicio crea(Ejercicio ejercicio);

}
