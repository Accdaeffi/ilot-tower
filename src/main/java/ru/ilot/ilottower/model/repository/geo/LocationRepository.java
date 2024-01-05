package ru.ilot.ilottower.model.repository.geo;

import jakarta.persistence.Column;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ilot.ilottower.model.entities.geo.Location;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, String> {
    Optional<Location> findFirstByLevelIdAndLocationXAndLocationY(int levelId, int locationX, int locationY);
}
