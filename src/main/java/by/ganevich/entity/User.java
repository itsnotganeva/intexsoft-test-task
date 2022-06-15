package by.ganevich.entity;

import by.ganevich.entity.enums.State;
import com.vladmihalcea.hibernate.type.array.ListArrayType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.List;

@TypeDefs({
        @TypeDef(
                name = "list-array",
                typeClass = ListArrayType.class
        )
})
@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "securityCode")
    private String code;

    @OneToOne(mappedBy = "user")
    private Client client;

    @Column(name = "userState")
    @Enumerated(EnumType.STRING)
    private State state;

    @Type(type = "list-array")
    @Column(
            name = "roles",
            columnDefinition = "text[]"
    )
    private List<String> roles;
}
