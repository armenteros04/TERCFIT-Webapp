package com.daw.club;

import com.daw.club.model.Cliente;
import com.daw.club.model.ClubPrincipal;
import com.daw.club.model.dao.ClienteDAO;
import com.daw.club.qualifiers.DAOMap;
import com.daw.club.services.ClubAuthService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;

import java.util.*;
import java.util.logging.Logger;

import static jakarta.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;
import static java.util.Arrays.asList;


@ApplicationScoped
public class ClubIdentityStore implements IdentityStore {

    private Map<String,String> credenciales; //ejemplo de almacén de credenciales

    private static final Logger logger = Logger.getLogger(ClubIdentityStore.class.getName());

    @Inject
    private ClubAuthService authService;

    @Inject @DAOMap
    private ClienteDAO clientesDAO;


    public CredentialValidationResult validate (UsernamePasswordCredential
                                                        usernamePasswordCredential ) {//Recuperar credenciales proporcionadas por el servidor

        String username = usernamePasswordCredential.getCaller();
        String password = usernamePasswordCredential.getPasswordAsString();//Ejemplo simple de verificación de credenciales

        boolean authenticated = false;

        Cliente cliente = clientesDAO.buscaPorNombreUsuario(username);

        if(cliente!=null && authService.verifyPassword(password, cliente.getClaveCifrada())) {
            authenticated = true;
            logger.info(String.format("Autenticación completada para %s", username));
        } else {
            logger.warning(String.format("Autenticación fallida para %s", username));
        }

        if (authenticated) {

            Set<String> roles =  new HashSet<>(asList("CLIENTE"));

            return new CredentialValidationResult(new ClubPrincipal(cliente), roles);
        }

        return INVALID_RESULT; //Autenticación inválida
    }
}
