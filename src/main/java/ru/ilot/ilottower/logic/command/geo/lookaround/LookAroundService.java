package ru.ilot.ilottower.logic.command.geo.lookaround;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ilot.ilottower.model.entities.geo.Building;
import ru.ilot.ilottower.model.entities.geo.Location;
import ru.ilot.ilottower.model.entities.user.Player;
import ru.ilot.ilottower.model.enums.geo.BuildingType;
import ru.ilot.ilottower.model.enums.geo.LocationType;
import ru.ilot.ilottower.model.repository.geo.BuildingRepository;
import ru.ilot.ilottower.telegram.exception.DataLogicException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LookAroundService {
    private final BuildingRepository buildingRepository;
    private final GetBuildingsAroundService getBuildingsAroundService;
    private final GetPlayersAroundService getPlayersAroundService;

    public String getLookAroungString(Player player) {
        Location location = player.getLocation();
        var sb = new StringBuilder();

        boolean inHarvestingBuilding = false;

        switch (player.getBuildingLocation()) {
            case BuildingType.MINING:
            case BuildingType.ARENA: {
                Building building = buildingRepository.findFirstByBuildingTypeAndLocationId(player.getBuildingLocation(), player.getLocation().getId())
                        .orElseThrow(() -> new DataLogicException(STR."User \{player.getId()} in non-existing building \{player.getBuildingLocation()} in \{player.getLocation().getId()}"));
                if (building != null) {
                    sb.append(STR."📌Ты в <b>\{building.getName()}</b>").append("\n");
                }
                inHarvestingBuilding = true;
            }
            break;
            case BuildingType.ALCHEMICAL_GLADE:
            case BuildingType.FISHING:
            case BuildingType.SAWMILL: {
                Building building = buildingRepository.findFirstByBuildingTypeAndLocationId(player.getBuildingLocation(), location.getId())
                        .orElseThrow(() -> new DataLogicException(STR."User \{player.getId()} in non-existing building \{player.getBuildingLocation()} in \{location.getId()}"));
                if (building != null) {
                    sb.append(STR."📌Ты на <b>\{building.getName()}</b>").append("\n");
                }
                inHarvestingBuilding = true;
            }
            break;
            default:
                //sb.append(STR."📌Ты здесь: \{EmojiGenerator.LocationEmoji(location.LocationType)} \{location.getLevelId()} Этаж (↔️\{location.getLocationX()}:↕️\{location.getLocationY()})").append("\n");
                sb.append(STR."📌Ты здесь: \{location.getLocationType()} \{location.getLevelId()} Этаж (↔️\{location.getLocationX()}:↕️\{location.getLocationY()})").append("\n");
                break;


        }

        //sb.append(STR."Время: \{EmojiGenerator.GetTimeEmoji()}").append("\n");
        sb.append("\n");
        if (player.getBuildingLocation() == BuildingType.NONE) {
            String stationaryAround = getBuildingsAroundService.getBuildingsAround(location);
            if (!stationaryAround.isBlank()) {
                sb.append("🏘Постройки:").append("\n");
                sb.append(stationaryAround); // при генерации строки уже добавляется перевод на новую, так что если использовать AppendLine, то будет пустая строка
                sb.append("\n");                // пустая строка для разделение создаётся отдельно, чтобы было нагляднее
            }
        }

        String playersNearby = getPlayersAroundService.getPlayerNearby(player, location);
        if (!playersNearby.isBlank()) {
            sb.append("👥Игроки поблизости:").append("\n");
            sb.append(playersNearby);
            sb.append("\n");
        }

        // TODO quests
//            if (!inHarvestingBuilding) {
//                var npcText = QuestNpcModule.NpcInteraction.GetNpcLocation(location);
//
//                if (string.IsNullOrEmpty(npcText) == false) {
//                    sb.AppendLine($"🚩NPC поблизости:");
//                    //sb.AppendLine();
//                    sb.Append(npcText);
//                    sb.AppendLine();
//                }
//            }


        if (!inHarvestingBuilding) {
            //var ch = new CommandHandler(user.Id);

            if (location.getLocationType() != LocationType.CITY) {
                sb.append("💀Найти монстра и атаковать /monsters").append("\n");
                sb.append("\n");
            }

            // TODO landbosses
//                var landBoss = db.LandBosses.FirstOrDefault(l = > l.LocationId == location.Id && l.BossState != LandBossState.sleeping );
//                if (landBoss != null) {
//                    ch.GameObjectType = GameObjectType.LandBoss;
//                    ch.CommandId = Commands.landBossToAttack;
//                    ch.MonsterId = landBoss.MonsterId;
//                    ch.TapControlDisabled = false;
//                    var cmd = ch.CodeCreated();
//                    //sb.AppendLine();
//                    var msg = landBoss.BossState switch
//                    {
//                        LandBossState.idle =>"Осторожно! Рядом бродит",
//                            LandBossState.attackWaiting =>"Внимание! Рядом готовится отражать атаку",
//                            LandBossState.fighting =>"Внимание! Рядом битва с ",
//                            _ =>""
//                    } ;
//
//                    msg = $ "{msg} {landBoss.Monster.Name}";
//
//                    if (landBoss.BossState == LandBossState.attackWaiting || landBoss.BossState == LandBossState.fighting) {
//                        msg = $ "{msg}. Возможно, ты сможешь присоединиться к атаке и заработать славу и деньги";
//                    }
//
//                    sb.AppendLine($"‼ {msg} /{cmd}");
//                }

            //var dungeon = db.Dungeons.FirstOrDefault(p => p.Location == location);
            Optional<Building> optionalDungeon = buildingRepository.findFirstByBuildingTypeAndLocationId(BuildingType.DUNGEON, location.getId());
            if (optionalDungeon.isPresent()) {
                Building dungeon = optionalDungeon.get();
                sb.append(STR."Рядом находится подземелье <i>\{dungeon.getName()}</i>!").append("\n");
                sb.append("Можешь поискать команду /viewParties или же создать свою /createParty").append("\n");
            }
        }
        return sb.toString();

    }


}
