package com.daw.club.model.dao;

import com.daw.club.model.Ejercicio;
import com.daw.club.model.Receta;
import java.util.List;

public interface RecetaDAO {

    /**
     * Verifica si existen recetas en la base de datos.
     * @return true si existen recetas, false si no
     */
    boolean existenRecetas();

    void borrarTodos();

    /**
     * Obtiene la lista de todas las recetas.
     * @return Lista de recetas
     */
    List<Receta> listarTodos();

    /**
     * Busca una receta por su ID.
     * @param id Identificador de la receta
     * @return Objeto Receta si se encuentra, null si no
     */
    Receta buscarPorId(int id);

    Receta buscarPorId(Long id);

    /**
     * Guarda una nueva receta en la base de datos.
     * @param receta Objeto Receta a guardar
     * @return true si se guardó correctamente, false si hubo un error
     */
    boolean guardar(Receta receta);

    /**
     * Actualiza una receta existente en la base de datos.
     * @param receta Objeto Receta con los datos actualizados
     * @return true si se actualizó correctamente, false si hubo un error
     */
    boolean actualizar(Receta receta);

    /**
     * Elimina una receta por su ID.
     * @param id Identificador de la receta a eliminar
     * @return true si se eliminó correctamente, false si hubo un error
     */
    boolean eliminar(int id);

    Receta crea(Receta receta);

    boolean eliminar(Long id);
}
