package ru.ilot.ilottower.logic.command.geo.lookaround;

import org.springframework.stereotype.Service;
import ru.ilot.ilottower.model.entities.geo.Building;
import ru.ilot.ilottower.model.entities.geo.Location;
import ru.ilot.ilottower.model.enums.geo.BuildingType;

@Service
public class GetBuildingsAroundService {

    public String getBuildingsAround(Location location) {
        var sb = new StringBuilder();
        for (Building building : location.getBuildingList()) {
            if (building.getBuildingType() != BuildingType.DUNGEON) {
                sb.append(processBuildingText(building)).append("\n");
            }
        }
        return sb.toString();
    }

    private String processBuildingText(Building building) {
        return switch (building.getBuildingType()) {
            case TELEPORT -> "<i>⛩Врата перемещения</i> /gate";
            case BLACKSMITH -> STR."<i>\{building.getName()}</i> /blacksmith";
            case ARENA -> STR."<i>🤺 \{building.getName()}</i> /arena";

            default -> STR."<i>\{building.getName()}</i> /\{building.getBuildingType().name().toLowerCase()}";
        };
    }
}
