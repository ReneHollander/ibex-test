package at.hollander.ibex.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsletterRegistration {

    @Id
    @Email
    private String email;

    @NotNull
    @Valid
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private City city;

}
