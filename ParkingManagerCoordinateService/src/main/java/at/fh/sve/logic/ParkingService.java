package at.fh.sve.logic;


import at.fh.sve.domain.AnalyzedParkingPlace;
import at.fh.sve.domain.Coordinates;

import java.util.List;


public interface ParkingService {

    List<Coordinates> readCoordinate(String city);

    AnalyzedParkingPlace getBestParkingPlaceFor(String city, Double longitude, Double latitude);
}
