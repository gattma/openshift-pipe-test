package at.fh.sve.dao;

import at.fh.sve.domain.ParkingPlace;
import at.fh.sve.domain.Coordinates;

import java.util.List;

public interface ParkingDAO {
    List<Coordinates> findByCity(String city);
    void create(Coordinates coordinates);

    List<ParkingPlace> findAllFor(String city);
}
