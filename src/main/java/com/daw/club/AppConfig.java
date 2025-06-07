package com.daw.club;

import com.daw.club.model.*;
import com.daw.club.model.dao.*;
import com.daw.club.qualifiers.DAOJpa;
import com.daw.club.qualifiers.DAOMap;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.Startup;
import jakarta.enterprise.inject.Default;
import jakarta.faces.annotation.FacesConfig;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import jakarta.security.enterprise.authentication.mechanism.http.FormAuthenticationMechanismDefinition;
import jakarta.security.enterprise.authentication.mechanism.http.LoginToContinue;
import org.glassfish.soteria.identitystores.annotation.Credentials;
import org.glassfish.soteria.identitystores.annotation.EmbeddedIdentityStoreDefinition;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@EmbeddedIdentityStoreDefinition({

        @Credentials(callerName = "admin", password = "adminterco", groups = {"ADMINISTRADORES"}),
        @Credentials(callerName = "profedaw", password = "profedaw", groups = {"ADMINISTRADORES"}),

        @Credentials(callerName = "antonio", password = "antonio", groups = {"CLIENTE"}),
        @Credentials(callerName = "garcilla", password = "garcilla", groups = {"CLIENTE"}),
        @Credentials(callerName = "hechun", password = "hechun", groups = {"CLIENTE"})

})

@BasicAuthenticationMechanismDefinition(realmName = "Vista logueada")
@FormAuthenticationMechanismDefinition(
        loginToContinue = @LoginToContinue(
                loginPage = "/login.jsf",
                errorPage = "/login.jsf?error",
                useForwardToLogin = false
        )
)
@Named("appConfigClub")
@Default
@ApplicationScoped
@FacesConfig
public class AppConfig {

        public List<String> getCredentialNames() {
                return Stream.of( "profedaw", "antonio", "garcilla", "hechun")
                        .collect(Collectors.toList());
        }

        @Inject @DAOMap
        ClienteDAO clienteDAO;

        @Inject @DAOJpa
        EjercicioDAO ejercicioDAO;

        @Inject @DAOJpa
        RecetaDAOJPA recetaDAO;

        @Inject @DAOJpa
        BiometriaDAOJPA biometriaDAO;

        private Logger logger = Logger.getLogger(AppConfig.class.getName());


        public void onStartup(@Observes Startup event) {
                logger.info(">>>Inicializando aplicación");
                createSampleData();
        }



        public void createSampleData() {
                String nuevaClave= "PBKDF2WithHmacSHA512:3072:kN6Xy8mLfmpS15I2QQ6oww2GV8ahZGZMKi8jq8CXge7mRQtItsqXl7EJ/JSEX4I/VofdPpWqLj20mgkkk4+hZw==:phiHq1GmgmNMFusGuCsarWtbiiKKkuAs+PEla7mlrmU=";
                clienteDAO.crea(new Cliente(0, "Antonio Armenteros Iranzo", "11111111-A", false).setClaveCifrada(nuevaClave));
                clienteDAO.crea(new Cliente(0, "David Garcia Padilla", "22222222-B", true).setClaveCifrada(nuevaClave));
                clienteDAO.crea(new Cliente(0, "Hechun Ouyang", "33333333-C", true).setClaveCifrada(nuevaClave));

                if(ejercicioDAO.listarTodos()!=null) {
                        ejercicioDAO.borrarTodos();
                        ejercicioDAO.guardar(new Ejercicio("Curl Predicador", "Ejercicio en barra para ejercitar la cabeza interna del bíceps", "curlpredicador.mp4", GrupoMuscular.BICEPS));
                        ejercicioDAO.guardar(new Ejercicio("Peso muerto", "Ideal para hacer femoral o fortalecer espalda", "pesomuerto.mp4", GrupoMuscular.PIERNA));
                        ejercicioDAO.guardar(new Ejercicio("Press banca", "Ejercicio estrella para entrenar pecho", "pressbanca.mp4", GrupoMuscular.PECHO));
                        ejercicioDAO.guardar(new Ejercicio("Press militar", "Ejercicio sentado en banca para trabajar los hombros", "pressmilitar.mp4", GrupoMuscular.HOMBRO));
                        ejercicioDAO.guardar(new Ejercicio("Dominada", "Ejercicio fundamental para espalda sin peso", "dominadas.mp4", GrupoMuscular.ESPALDA));
                        ejercicioDAO.guardar(new Ejercicio("Correr", "Ejercicio de cardio para correr y mantenerse activo", "cinta.mp4", GrupoMuscular.CARDIO));
                        ejercicioDAO.guardar(new Ejercicio("Press Frances", "Ejercicio tumbado para trabajar los triceps", "pressFrances.mp4", GrupoMuscular.TRICEPS));
                        ejercicioDAO.guardar(new Ejercicio("Sentadilla", "Ejercicio fundamental para cuádriceps", "sentadilla.mp4", GrupoMuscular.PIERNA));
                }
                recetaDAO.guardar(new Receta("Avena Moca Proteica", "Copos de avena, cacao en polvo, café instantáneo, semillas de chía, proteína en polvo, leche, endulzante", "Mezclar ingredientes y refrigerar por 4 horas.", "Rica en proteínas y fibra", 350, 20, 8, 50, 10, "avenaMoca.png"));
                recetaDAO.guardar(new Receta("Pollo Tex-Mex con Quinoa", "Quinoa, yogur, aguacate, lima, chile chipotle, pollo, alubias negras, maíz, tomate cherry, cebolleta, espinacas, semillas de calabaza", "Cocinar quinoa y mezclar con los demás ingredientes.", "Plato completo con proteínas y fibra", 540, 45, 15, 55, 12, "estofado.png"));
                recetaDAO.guardar(new Receta("Tortitas de Plátano y Avena", "Plátano, huevos, avena, polvo de hornear, canela, sal, aceite", "Triturar plátano y mezclar con los demás ingredientes. Cocinar en sartén.", "Alternativa saludable y energética", 300, 12, 7, 50, 6, "tortitas.png"));
                recetaDAO.guardar(new Receta("Ensalada de Quinoa y Verduras", "Quinoa, pimiento, calabacín, berenjena, aceite de oliva, garbanzos, espinaca, limón", "Asar verduras y mezclar con quinoa y garbanzos.", "Fuente de fibra y proteínas vegetales", 400, 15, 12, 60, 10, "quinoa.png"));

                biometriaDAO.guardar(new Biometria(80,3500,3,4,21,150));

        }
}
