package mx.com.truper.springboot.acmehrsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.com.truper.springboot.acmehrsystem.model.Desk;

public interface DeskRepository extends JpaRepository<Desk, Long> {

}
