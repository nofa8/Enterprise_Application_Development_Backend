package pt.ipleiria.estg.dei.ei.dae.projeto.ejbs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

import java.util.logging.Logger;

@Singleton
@Startup
public class ConfigBean {

    private static final Logger logger = Logger.getLogger("ejbs.ConfigBean");

    @PostConstruct
       public void populateDB() {

        try{
            // Criação
        } catch (Exception e){
            logger.severe(e.getMessage());
        }




    }
}
