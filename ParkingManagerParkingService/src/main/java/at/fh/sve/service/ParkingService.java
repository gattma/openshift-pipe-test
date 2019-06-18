package at.fh.sve.service;


import at.fh.sve.domain.ParkingPlace;

import java.util.List;

public interface ParkingService {

    ParkingPlace addParkingPlace(ParkingPlace ps);
    List<ParkingPlace> getAllParkingPlaces();
}
