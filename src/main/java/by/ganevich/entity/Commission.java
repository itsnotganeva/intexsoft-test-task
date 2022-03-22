package by.ganevich.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(name = "commissionForClients")
public class Commission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(
            cascade = CascadeType.DETACH,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "bankId")
    @Valid
    private Bank bank;

    @Column(name = "clientType")
    private Integer clientType;

    @Column(name = "commission")
    @DecimalMax("10.0") @DecimalMin("0.0")
    @NotNull(message = "Commission should not be null")
    private Double commission;

    @Override
    public String toString() {
        return "Commission{"
                + ", clientType=" + clientType
                + ", commission=" + commission
                + '}';
    }
}
