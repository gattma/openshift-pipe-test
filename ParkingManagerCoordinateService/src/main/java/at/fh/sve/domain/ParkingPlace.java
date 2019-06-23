package at.fh.sve.domain;

import javax.persistence.*;
import java.util.List;
import java.util.StringJoiner;

@Entity
public class ParkingPlace extends BaseEntity {

    private String webcamUrl;
    private String city;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Address address;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Coordinate location;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ParkingSpaceCoordinate> parkingSpaces;

    public ParkingPlace() {
        super();
    }

    public ParkingPlace(String webcamUrl, Address address, Coordinate location, List<ParkingSpaceCoordinate> parkingSpaces) {
        this.webcamUrl = webcamUrl;
        this.address = address;
        this.location = location;
        this.parkingSpaces = parkingSpaces;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Coordinate getLocation() {
        return location;
    }

    public void setLocation(Coordinate location) {
        this.location = location;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ParkingPlace.class.getSimpleName() + "[", "]")
                .add("id=" + getId())
                .add("webcamUrl='" + webcamUrl + "'")
                .add("city='" + city + "'")
                .add("address=" + address)
                .add("location=" + location)
                .add("parkingSpaces=" + parkingSpaces)
                .toString();
    }
}
