package at.fh.sve.domain;

public class ParkingSpace {

    private Long id;
    private Address address;
    private Coordinate location;

    public ParkingSpace() {
        super();
    }

    public ParkingSpace(Long id, Address address, Coordinate location) {
        this.id = id;
        this.address = address;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
