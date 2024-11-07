package pt.ipleiria.estg.dei.ei.dae.projeto.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.projeto.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.projeto.entities.PackageType;
import pt.ipleiria.estg.dei.ei.dae.projeto.entities.Volume;
import pt.ipleiria.estg.dei.ei.dae.projeto.entities.enums.VolumeState;
import pt.ipleiria.estg.dei.ei.dae.projeto.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projeto.exceptions.MyEntityNotFoundException;

import java.util.Date;
import java.util.List;

@Stateless
public class VolumeBean {
    @PersistenceContext
    private EntityManager entityManager;



    public void create(long code, VolumeState state, PackageType typePackage, long orderCode, Date timestamp)
            throws MyEntityExistsException, MyEntityNotFoundException {

        if (entityManager.find(Volume.class, code) != null){
            throw new MyEntityNotFoundException("Volume with code " + code + " not found");
        }

        Order order = entityManager.find(Order.class,orderCode);
        if ( order == null){
            throw new MyEntityNotFoundException("Order with code " + code + " not found");
        }

        var volume = new Volume(code, state,typePackage,order,timestamp );

        entityManager.persist(volume);
    }

    public List<Volume> findAll() {
        // remember, maps to: “SELECT s FROM Student s ORDER BY s.name”
        return entityManager.createNamedQuery("getAllVolumes", Volume.class).getResultList();
    }
    public Volume find(long id) {
        var volume = entityManager.find(Volume.class,id);
        if (volume == null) {
            throw new RuntimeException("Volume " + id + " not found");
        }
        return volume;
    }

    public void delete(long volume) {
        entityManager.remove(entityManager.find(Volume.class, volume));
    }

    public void update(long code, VolumeState state, PackageType typePackage, long orderCode, Date timestamp) {
        Volume volume = entityManager.find(Volume.class, code);
        if (volume == null || !entityManager.contains(volume)){
            return;
        }
        volume.setState(state);
        volume.setTypePackage(typePackage);
        volume.setOrder(entityManager.find(Order.class, orderCode));
        volume.setTimestamp(timestamp);
        entityManager.merge(volume);
    }


}
