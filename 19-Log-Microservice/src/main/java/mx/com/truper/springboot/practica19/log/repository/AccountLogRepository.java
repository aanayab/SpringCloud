package mx.com.truper.springboot.practica19.log.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.com.truper.springboot.practica19.log.model.Account;

public interface AccountLogRepository extends JpaRepository<Account, Integer> {

}
