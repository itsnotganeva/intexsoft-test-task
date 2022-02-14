package by.ganevich.repository;

import by.ganevich.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {

//    @Query(value = "select * from banks where name=?", nativeQuery = true)
    Bank findByName(String name);

}
