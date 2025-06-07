package beans;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Named("fechaBean")
@SessionScoped
public class fechaBean implements Serializable{

    public String getFechaActual() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(new Date());
    }
}
