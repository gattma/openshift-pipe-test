package at.fh.sve.ue4.ms.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@NamedQuery(name = "Coordinates.findAll", query = "SELECT c from Coordinates c")
public class Coordinates {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String city;
    private String url;
    private List<List<Integer>> coordinates;

    public Coordinates(String city, String url, List<List<Integer>> coordinates) {
        this.city = city;
        this.url = url;
        this.coordinates = coordinates;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<List<Integer>> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<List<Integer>> coordinates) {
        this.coordinates = coordinates;
    }
}
