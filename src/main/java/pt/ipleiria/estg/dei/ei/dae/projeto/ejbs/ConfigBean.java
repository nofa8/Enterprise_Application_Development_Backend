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
        try {
            // Existing Clients
            clientBean.create("email@email.com", "password", "name");
            clientBean.create("email1@email.com", "um", "um");
            clientBean.create("email2@email.com", "dois", "dois");
            clientBean.create("email3@email.com", "três", "três");
            clientBean.create("email4@email.com", "quatro", "quatro");
            clientBean.create("email5@email.com", "cinco", "cinco");
            clientBean.create("email6@email.com", "seis", "seis");
            clientBean.create("email7@email.com", "sete", "sete");
            clientBean.create("email8@email.com", "oito", "oito");
            clientBean.create("email9@email.com", "nove", "nove");

            // Existing Managers
            managerBean.create("admin1@admin.com", "password1", "name1");
            managerBean.create("admin2@admin.com", "password2", "name2");
            managerBean.create("admin3@admin.com", "password3", "name3");
            managerBean.create("admin4@admin.com", "password4", "name4");
            managerBean.create("admin5@admin.com", "password5", "name5");
            managerBean.create("admin6@admin.com", "password6", "name6");
            managerBean.create("admin7@admin.com", "password7", "name7");
            managerBean.create("admin8@admin.com", "password8", "name8");


            // Existing Sensor Types
            sensorTypeBean.create(1, "Temperature Sensor");
            sensorTypeBean.create(2, "Atmospheric Pressure Sensor");
            sensorTypeBean.create(3, "Accelerometer");
            sensorTypeBean.create(4, "Global Positioning Sensor");
            sensorTypeBean.create(5, "Humidity Sensor");
            sensorTypeBean.create(6, "Light Sensor");
            sensorTypeBean.create(7, "Infrared Sensor");
            sensorTypeBean.create(8, "Ultrasonic Sensor");

            // Existing Sensors
            sensorBean.create(1, 1, "20", Date.from(Instant.now()), 1);
            sensorBean.create(2, 2, "30", Date.from(Instant.now()), 2);
            sensorBean.create(3, 3, "40", Date.from(Instant.now()), 3);
            sensorBean.create(4, 4, "50", Date.from(Instant.now()), 4);
            sensorBean.create(5, 5, "60", Date.from(Instant.now()), 5);


            // Existing Product Types
            productTypeBean.create(1);
            productTypeBean.create(2);
            productTypeBean.create(3);
            productTypeBean.create(4);
            productTypeBean.create(5);
            productTypeBean.create(6);
            productTypeBean.create(7);
            productTypeBean.create(8);
            productTypeBean.create(9);
            productTypeBean.create(10);

            // Existing Package Types
            packageTypeBean.create(1, "Térmica");
            packageTypeBean.create(2, "Caixa Metal");
            packageTypeBean.create(3, "Caixa Madeira");
            packageTypeBean.create(4, "Saco Plástico");
            packageTypeBean.create(5, "Bolha Plástica");
            packageTypeBean.create(6, "Envelope Almofadado");
            packageTypeBean.create(7, "Recipiente de Vidro");
            packageTypeBean.create(8, "Caixa de Papelão");

            // Existing Orders
            orderBean.create(1, 1.2f, OrderState.PROCESSED, Date.from(Instant.now()), Date.from(Instant.now()), "email@email.com");
            orderBean.create(2, 2.5f, OrderState.DELIVERED, Date.from(Instant.now()), Date.from(Instant.now()), "email1@email.com");
            orderBean.create(3, 3.0f, OrderState.SHIPPED, Date.from(Instant.now()), Date.from(Instant.now()), "email2@email.com");
            orderBean.create(4, 4.0f, OrderState.IN_TRANSIT, Date.from(Instant.now()), Date.from(Instant.now()), "email3@email.com");
            orderBean.create(5, 5.5f, OrderState.PROCESSED, Date.from(Instant.now()), Date.from(Instant.now()), "email4@email.com");

            // Existing Volumes
            volumeBean.create(1, VolumeState.PACKED, 1, 1, Date.from(Instant.now()));
            volumeBean.create(2, VolumeState.PACKED, 2, 2, Date.from(Instant.now()));
            volumeBean.create(3, VolumeState.SHIPPED, 3, 3, Date.from(Instant.now()));
            volumeBean.create(4, VolumeState.DELIVERED, 4, 4, Date.from(Instant.now()));
            volumeBean.create(5, VolumeState.IN_TRANSIT, 5, 5, Date.from(Instant.now()));

            // Existing Products
            productBean.create(111, "Primeiro", "Volkswagen", 1, "Grande PóPó", 1, 1);
            productBean.create(112, "Segundo", "Ford", 2, "Compacto", 2, 2);
            productBean.create(113, "Terceiro", "Tesla", 3, "Elétrico", 3, 3);
            productBean.create(114, "Quarto", "Toyota", 4, "Híbrido", 4, 4);
            productBean.create(115, "Quinto", "Honda", 5, "SUV", 5, 5);


        } catch (Exception e) {
            logger.severe(e.getMessage());
        }
    }

}
