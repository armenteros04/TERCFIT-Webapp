package beans;


import com.daw.club.AppConfig;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;

@Named("dashboardBean")
@RequestScoped
public class dashboardBean {

    @Inject
    private AppConfig appConfig;

    public List<String> getCredentialNames() {
        return appConfig.getCredentialNames();
    }
}