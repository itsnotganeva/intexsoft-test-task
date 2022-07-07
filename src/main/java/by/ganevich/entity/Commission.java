package by.ganevich.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "commission_for_clients")
public class Commission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_type")
    private Integer clientType;

    @Column(name = "commission")
    private Double commission;

    @Override
    public String toString() {
        return "Commission{"
                + ", clientType=" + clientType
                + ", commission=" + commission
                + '}';
    }
}
