package com.ironhack.midterm.project.repository;

import com.ironhack.midterm.project.models.account.Account;
import com.ironhack.midterm.project.models.clasees.Money;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findById(Long id);

    Account save(Account account);

    @Query("SELECT balance FROM Account WHERE id LIKE :id")
    Money getBalance(@Param("id") Long id);

}