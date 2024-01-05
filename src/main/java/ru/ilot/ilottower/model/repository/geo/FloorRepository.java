package ru.ilot.ilottower.model.repository.geo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ilot.ilottower.model.entities.geo.Floor;

@Repository
public interface FloorRepository extends JpaRepository<Floor, Integer> {
}
