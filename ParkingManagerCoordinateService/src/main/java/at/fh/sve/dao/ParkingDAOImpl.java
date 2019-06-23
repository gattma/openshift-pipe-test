package at.fh.sve.dao;

import at.fh.sve.domain.BaseEntity;
import at.fh.sve.domain.ParkingPlace;
import at.fh.sve.domain.Coordinates;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Transactional
public class ParkingDAOImpl implements ParkingDAO {

    @Inject
    private EntityManager em;

    @Override
    public List<Coordinates> findByCity(String city) {
        return em.createQuery(
                "SELECT c FROM Coordinates c WHERE c.city = :city")
                .setParameter("city", city)
                .getResultList();
    }

    @Override
    public void create(Coordinates coordinates) {
        em.persist(coordinates);
        em.flush();
    }

    @Override
    public List<ParkingPlace> findAllFor(String city) {
        List<ParkingPlace> res2 = em.createQuery(
                "FROM ParkingPlace pp WHERE pp.city LIKE :city", ParkingPlace.class)
                .setParameter("city", city)
                .getResultList();

        return res2;
    }
}
