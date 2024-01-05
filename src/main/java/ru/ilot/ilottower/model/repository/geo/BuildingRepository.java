package ru.ilot.ilottower.model.repository.geo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ilot.ilottower.model.entities.geo.Building;
import ru.ilot.ilottower.model.enums.geo.BuildingType;

import java.util.Optional;

@Repository
public interface BuildingRepository extends JpaRepository<Building, Integer> {
    Optional<Building> findFirstByBuildingTypeAndLocationId(BuildingType buildingType, String locationId);
}
