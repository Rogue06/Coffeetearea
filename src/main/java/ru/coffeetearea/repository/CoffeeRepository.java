package ru.coffeetearea.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.coffeetearea.model.Coffee;

public interface CoffeeRepository extends JpaRepository<Coffee, Long>, JpaSpecificationExecutor<Coffee> {

    Page<Coffee> findAllByDeletedIsFalse(Pageable pageable);

    boolean existsByName(String coffeeName);
}