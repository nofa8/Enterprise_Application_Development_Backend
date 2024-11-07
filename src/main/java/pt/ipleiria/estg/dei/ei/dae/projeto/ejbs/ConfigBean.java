package pt.ipleiria.estg.dei.ei.dae.projeto.ejbs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import pt.ipleiria.estg.dei.ei.dae.projeto.entities.PackageType;
import pt.ipleiria.estg.dei.ei.dae.projeto.entities.ProductType;
import pt.ipleiria.estg.dei.ei.dae.projeto.entities.enums.OrderState;
import pt.ipleiria.estg.dei.ei.dae.projeto.entities.enums.SensorsType;
import pt.ipleiria.estg.dei.ei.dae.projeto.entities.enums.VolumeState;

import java.time.Instant;
import java.time.LocalTime;
import java.util.Date;
import java.util.logging.Logger;

@Singleton
@Startup
public class ConfigBean {

    private static final Logger logger = Logger.getLogger("ejbs.ConfigBean");
    @EJB
    private ClientBean clientBean;

    @EJB
    private ManagerBean managerBean;
    @EJB
    private OrderBean orderBean;
    @EJB
    private SensorBean sensorBean ;
    @EJB
    private ProductBean productBean ;
    @EJB
    private VolumeBean volumeBean ;

    @EJB
    private ProductTypeBean productTypeBean ;

    @EJB
    private PackageTypeBean packageTypeBean ;

    @PostConstruct
       public void populateDB() {

        try{

            clientBean.create("username", "password", "name", "email@email.email");
            clientBean.create("um", "um", "um", "email1@email.email");
            clientBean.create("dois", "dois", "dois", "email2@email.email");
            clientBean.create("três", "três", "três", "email3@email.email");
            clientBean.create("quatro", "quatro", "quatro", "email4@email.email");


            managerBean.create("username1", "password1", "name1", "admin1@admin.admin");
            managerBean.create("username2", "password2", "name2", "admin2@admin.admin");
            managerBean.create("username3", "password3", "name3", "admin3@admin.admin");
            managerBean.create("username4", "password4", "name4", "admin4@admin.admin");

            productTypeBean.create(1);
            productTypeBean.create(2);
            productTypeBean.create(3);
            productTypeBean.create(4);
            productTypeBean.create(5);

            packageTypeBean.create(1);
            packageTypeBean.create(2);
            packageTypeBean.create(3);
            packageTypeBean.create(4);

            orderBean.create(1, OrderState.PROCESSED, Date.from(Instant.now()),Date.from(Instant.now()), "username" );

            volumeBean.create(1, VolumeState.PACKED,1 ,1,Date.from(Instant.now()));


            productBean.create(111, "Primeiro", "Volkswagen", 1, "Grande PóPó", 1, 1   );

            sensorBean.create(1, SensorsType.ACCELERATION, "20", Date.from(Instant.now()),1);



        } catch (Exception e){
            logger.severe(e.getMessage());
        }




    }
}
