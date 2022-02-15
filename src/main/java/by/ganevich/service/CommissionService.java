package by.ganevich.service;

import by.ganevich.entity.Commission;
import by.ganevich.repository.CommissionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class CommissionService {

    private final CommissionRepository commissionRepository;

    public Double findCommissionByClientType(Integer clientType) {
        Commission commission = commissionRepository.findCommissionByClientType(clientType);
        return commission.getCommission();
    }
}
