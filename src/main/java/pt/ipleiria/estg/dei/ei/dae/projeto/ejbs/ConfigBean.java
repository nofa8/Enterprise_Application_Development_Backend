package pt.ipleiria.estg.dei.ei.dae.projeto.ejbs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import pt.ipleiria.estg.dei.ei.dae.projeto.entities.enums.OrderState;
import pt.ipleiria.estg.dei.ei.dae.projeto.entities.SensorsType;
import pt.ipleiria.estg.dei.ei.dae.projeto.entities.enums.VolumeState;

import java.time.Instant;
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
    private SensorTypeBean sensorTypeBean ;

    @EJB
    private ProductTypeBean productTypeBean ;

    @EJB
    private PackageTypeBean packageTypeBean ;

    @PostConstruct
       public void populateDB() {

        try{

            clientBean.create("email@email.email", "password", "name");
            clientBean.create("email1@email.email", "um", "um");
            clientBean.create("email2@email.email", "dois", "dois");
            clientBean.create("email3@email.email", "três", "três");
            clientBean.create("email4@email.email", "quatro", "quatro");


            managerBean.create("admin1@admin.admin", "password1", "name1");
            managerBean.create("admin2@admin.admin", "password2", "name2");
            managerBean.create("admin3@admin.admin", "password3", "name3");
            managerBean.create("admin4@admin.admin", "password4", "name4");

            productTypeBean.create(1);
            productTypeBean.create(2);
            productTypeBean.create(3);
            productTypeBean.create(4);
            productTypeBean.create(5);

            packageTypeBean.create(1, "Térmica");
            packageTypeBean.create(2 , "Caixa Metal");
            packageTypeBean.create(3, "Caixa Madeira");
            packageTypeBean.create(4, "Saco Plástico");

            orderBean.create(1,1.2f, OrderState.PROCESSED, Date.from(Instant.now()),Date.from(Instant.now()), "username" );

            volumeBean.create(1, VolumeState.PACKED,1 ,1,Date.from(Instant.now()));


            productBean.create(111, "Primeiro", "Volkswagen", 1, "Grande PóPó", 1, 1   );

            sensorTypeBean.create(1, "Temperature Sensor");
            sensorTypeBean.create(2, "Atmospheric Pressure Sensor");
            sensorTypeBean.create(3, "Accelerometer");
            sensorTypeBean.create(4, "Global Positioning Sensor");


            sensorBean.create(1, 1, "20", Date.from(Instant.now()),1);




        } catch (Exception e){
            logger.severe(e.getMessage());
        }
    }
}
