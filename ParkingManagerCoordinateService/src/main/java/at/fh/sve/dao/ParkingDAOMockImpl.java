package at.fh.sve.dao;

import at.fh.sve.domain.ParkingPlace;
import at.fh.sve.domain.Coordinates;
import org.hibernate.cfg.NotYetImplementedException;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Alternative
@ApplicationScoped
public class ParkingDAOMockImpl implements ParkingDAO {

    private ArrayList<Coordinates> coordinatesList = new ArrayList<>();

    @Override
    public List<Coordinates> findByCity(String city) {
        return coordinatesList.stream().filter(c -> c.getCity().equals(city)).collect(Collectors.toList());
    }

    @Override
    public void create(Coordinates coordinates) {
        coordinatesList.add(coordinates);
    }

    @Override
    public List<ParkingPlace> findAllFor(String city) {
        throw new NotYetImplementedException();
    }
}
