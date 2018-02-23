package at.hollander.ibex.entity;

import at.hollander.ibex.entity.id.CityId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class City implements Serializable {

    @EmbeddedId
    private CityId cityId;

    private boolean enabled;

    public City(CityId cityId) {
        this.cityId = cityId;
    }

    public City(int postcode, String name) {
        this(postcode, name, false);
    }

    public City(int postcode, String name, boolean enabled) {
        this(new CityId(postcode, name), false);
    }

}
