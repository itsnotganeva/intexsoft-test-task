package by.ganevich.service;

import by.ganevich.entity.Commission;
import by.ganevich.repository.CommissionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class CommissionService {

    private final CommissionRepository commissionRepository;

    public void delete(Commission commission) {
        commissionRepository.delete(commission);
    }
}
