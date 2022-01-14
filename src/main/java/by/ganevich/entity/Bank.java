package by.ganevich.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Bank {
    private Long id;
    private String name;
    private Double commission;

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bank bank = (Bank) o;
        return Objects.equals(id, bank.id) && Objects.equals(name, bank.name) && Objects.equals(commission, bank.commission);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, commission);
    }

    @Override
    public String toString() {
        return "Bank{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", commission=" + commission +
                '}';
    }
}
