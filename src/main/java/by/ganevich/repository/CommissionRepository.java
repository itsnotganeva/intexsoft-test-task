package by.ganevich.repository;

import by.ganevich.entity.Commission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommissionRepository extends JpaRepository<Commission, Long> {

    Commission findCommissionByClientType(Integer clientType);
}
