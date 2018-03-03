package at.hollander.ibex.entity;

import at.hollander.ibex.View;
import at.hollander.ibex.entity.id.CityId;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonView;
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

    @JsonView({View.City.Details.class})
    private boolean enabled;

    public City(CityId cityId) {
        this.cityId = cityId;
    }

    public City(int postcode, String name) {
        this(postcode, name, false);
    }

    public City(int postcode, String name, boolean enabled) {
        this(new CityId(postcode, name), enabled);
    }

    @JsonGetter("name")
    @JsonView({View.City.Details.class})
    public String getName() {
        return cityId.getName();
    }

    @JsonGetter("postcode")
    @JsonView({View.City.Details.class})
    public int getPostcode() {
        return cityId.getPostcode();
    }

}
