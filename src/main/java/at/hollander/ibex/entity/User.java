package at.hollander.ibex.entity;

import at.hollander.ibex.View;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "account")
@ToString(exclude = "account")
@Builder
public class User {

    @Id
    @JsonView({View.User.Details.class})
    private String email;

    @Column(nullable = false, length = 60)
    private String password;

    @Builder.Default
    @Column(nullable = false)
    @JsonView({View.User.Details.class})
    private Role role = Role.USER;

    @Column(nullable = false)
    @JsonView({View.User.Name.class})
    private String name;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonView({View.User.Details.class})
    private Account account;

    public enum Role {
        USER, ADMIN
    }

}
