package at.fh.sve.service;


import at.fh.sve.dao.ParkingDAO;
import at.fh.sve.domain.ParkingPlace;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class ParkingServiceImpl implements ParkingService {

    @Inject
    private ParkingDAO parkingDAO;

    @Override
    public ParkingPlace addParkingPlace(ParkingPlace ps) {
        return parkingDAO.create(ps);
    }

    @Override
    public List<ParkingPlace> getAllParkingPlaces() {
        return parkingDAO.getAll();
    }
}
