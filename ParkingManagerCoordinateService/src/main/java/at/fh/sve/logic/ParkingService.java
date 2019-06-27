package at.fh.sve.logic;


import at.fh.sve.domain.AnalyzedParkingPlace;
import at.fh.sve.domain.Coordinates;

import java.util.List;


public interface ParkingService {

    AnalyzedParkingPlace getBestParkingPlaceFor(String city, Double longitude, Double latitude);
}
