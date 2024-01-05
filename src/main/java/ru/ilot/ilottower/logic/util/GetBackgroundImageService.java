package ru.ilot.ilottower.logic.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.ilot.ilottower.model.entities.user.Player;
import ru.ilot.ilottower.model.enums.geo.BuildingType;
import ru.ilot.ilottower.model.enums.geo.LocationType;

import java.io.InputStream;
import java.util.Optional;

@Slf4j
@Service
public class GetBackgroundImageService {
    private final String BACKGROUND_BASE_DIRECTORY = "images/sources/background/";
    private final String FILE_EXTENSION = ".png";

    public Optional<InputStream> getBackgroundForPlayer(Player player) {
        Optional<InputStream> optionalBuildingImage = getBackground(player.getBuildingLocation());
        if (optionalBuildingImage.isEmpty()) {
            optionalBuildingImage = getBackground(player.getLocation().getLocationType());
        }
        return optionalBuildingImage;
    }

    private Optional<InputStream> getBackground(LocationType locationType) {
        String fileName = STR."back_\{locationType.toString().toLowerCase()}\{FILE_EXTENSION}";

        log.info(STR."Reading from \{BACKGROUND_BASE_DIRECTORY}\{fileName}");

        try {
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(BACKGROUND_BASE_DIRECTORY + fileName);
            log.info("Image readed!");
            return Optional.ofNullable(inputStream);
        } catch (Exception ex) {
            log.error("error during reading file {}", fileName, ex);
            return Optional.empty();
        }
    }

    private Optional<InputStream> getBackground(BuildingType buildingType) {
        String fileName = switch (buildingType) {
            case BuildingType.MINING -> "back_cave.png";
            case BuildingType.FISHING -> "back_pier.png";
            case BuildingType.ALCHEMICAL_GLADE -> "back_glade.png";
            case BuildingType.SAWMILL -> "back_lumbermill.png";
            case BuildingType.ARENA -> "back_arena.png";
            default -> null;
        };

        if (fileName == null) {
            return Optional.empty();
        }
        log.info(STR."Reading from \{BACKGROUND_BASE_DIRECTORY}\{fileName}");

        try {
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(BACKGROUND_BASE_DIRECTORY + fileName);

            log.info("Image readed!");
            return Optional.ofNullable(inputStream);
        } catch (Exception ex) {
            log.error("error during reading file {}", fileName, ex);
            return Optional.empty();
        }
    }
}
