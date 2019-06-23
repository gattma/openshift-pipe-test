package at.fh.sve.logic;


import at.fh.sve.domain.ParkingPlace;
import at.fh.sve.domain.Coordinates;

import java.util.List;


public interface ParkingService {

    List<Coordinates> readCoordinate(String city);

    ParkingPlace getBestParkingPlaceFor(String city);
}
