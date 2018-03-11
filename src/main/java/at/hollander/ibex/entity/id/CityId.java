package at.hollander.ibex.entity.id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class CityId implements Serializable {

    @Positive
    private int postcode;
    @NotEmpty
    private String name;

}