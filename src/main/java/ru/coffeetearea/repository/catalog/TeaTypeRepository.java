package ru.coffeetearea.repository.catalog;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.coffeetearea.model.catalog.CoffeeType;
import ru.coffeetearea.model.catalog.GeneralCatalog;
import ru.coffeetearea.model.catalog.TeaType;

import java.util.List;

public interface TeaTypeRepository extends JpaRepository<TeaType, Long> {

    List<GeneralCatalog> findAllByDeletedIsFalse(Sort sort);

    boolean existsByName(String teaTypeName);
}