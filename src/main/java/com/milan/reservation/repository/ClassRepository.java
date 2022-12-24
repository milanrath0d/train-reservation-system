package com.milan.reservation.repository;

import com.milan.reservation.model.Class;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Milan Rathod
 */
public interface ClassRepository extends JpaRepository<Class, Long> {

    Optional<Class> findByClassCode(String classCode);
}
