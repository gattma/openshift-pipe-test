package at.fh.sve.domain;

import javax.persistence.Entity;
import java.util.StringJoiner;

@Entity
public class Coordinate extends BaseEntity {

    private Long longitude;
    private Long latitude;

    public Coordinate() {
        super();
    }

    public Coordinate(Long longitude, Long latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Long getLongitude() {
        return longitude;
    }

    public void setLongitude(Long longitude) {
        this.longitude = longitude;
    }

    public Long getLatitude() {
        return latitude;
    }

    public void setLatitude(Long latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Coordinate.class.getSimpleName() + "[", "]")
                .add("longitude=" + longitude)
                .add("latitude=" + latitude)
                .toString();
    }
}
