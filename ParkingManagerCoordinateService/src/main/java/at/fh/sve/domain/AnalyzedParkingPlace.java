package at.fh.sve.domain;

import java.util.List;

public class AnalyzedParkingPlace {

    private String webcamUrl;
    private List<ParkingSpaceCoordinate> parkingSpaces;

    public AnalyzedParkingPlace() {
    }

    public AnalyzedParkingPlace(String webcamUrl, List<ParkingSpaceCoordinate> parkingSpaces) {
        this.webcamUrl = webcamUrl;
        this.parkingSpaces = parkingSpaces;
    }

    public String getWebcamUrl() {
        return webcamUrl;
    }

    public void setWebcamUrl(String webcamUrl) {
        this.webcamUrl = webcamUrl;
    }

    public List<ParkingSpaceCoordinate> getParkingSpaces() {
        return parkingSpaces;
    }

    public void setParkingSpaces(List<ParkingSpaceCoordinate> parkingSpaces) {
        this.parkingSpaces = parkingSpaces;
    }

    @Override
    public String toString() {
        return "AnalyzedParkingPlace{" +
                "webcamUrl='" + webcamUrl + '\'' +
                ", parkingSpaces=" + parkingSpaces +
                '}';
    }
}
