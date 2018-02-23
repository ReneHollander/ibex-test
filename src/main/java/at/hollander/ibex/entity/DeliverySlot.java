package at.hollander.ibex.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DeliverySlot implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalTime deliverBy;

    public DeliverySlot(String name, LocalTime deliverBy) {
        this.name = name;
        this.deliverBy = deliverBy;
    }
}
