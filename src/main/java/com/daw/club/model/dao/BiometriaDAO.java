package com.daw.club.model.dao;

import com.daw.club.model.Biometria;
import java.util.List;

public interface BiometriaDAO {

    /**
     * Obtiene la lista de todas las biometrías.
     * @return Lista de objetos Biometria
     */
    List<Biometria> listarTodos();

    /**
     * Busca una biometría por su ID.
     * @param id Identificador
     * @return Objeto Biometria si se encuentra, null si no
     */
    Biometria buscarPorId(Long id);

    /**
     * Guarda una nueva biometría en la base de datos.
     * @param biometria Objeto Biometria a guardar
     * @return true si se guardó correctamente, false si hubo un error
     */
    boolean guardar(Biometria biometria);

    /**
     * Actualiza una biometría existente.
     * @param biometria Objeto Biometria con los datos actualizados
     * @return true si se actualizó correctamente, false si hubo un error
     */
    boolean actualizar(Biometria biometria);

    /**
     * Elimina una biometría por su ID.
     * @param id Identificador de la biometría a eliminar
     * @return true si se eliminó correctamente, false si hubo un error
     */
    boolean eliminar(Long id);
}
