package ru.ilot.ilottower.logic.command.dungeon;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.ilot.ilottower.model.entities.dungeon.DungeonCell;
import ru.ilot.ilottower.model.entities.dungeon.DungeonInstance;
import ru.ilot.ilottower.model.entities.dungeon.DungeonPartyPlayer;
import ru.ilot.ilottower.model.entities.user.StatsPlayer;
import ru.ilot.ilottower.model.enums.dungeon.DungeonCellType;
import ru.ilot.ilottower.model.repository.dungeon.DungeonCellRepository;
import ru.ilot.ilottower.telegram.exception.DataLogicException;
import ru.ilot.ilottower.telegram.keyboard.DungeonInlineKeyboardGetter;
import ru.ilot.ilottower.telegram.response.StringWithKeyboardResponse;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VisionService {
    private final DungeonCellRepository dungeonCellRepository;
    private final DungeonInlineKeyboardGetter dungeonInlineKeyboardGetter;

    public StringWithKeyboardResponse getVision(DungeonInstance dungeonInstance, DungeonPartyPlayer partyPlayer) {
        StringBuilder sb = new StringBuilder();

        DungeonCell cellWithUser = dungeonInstance.getRooms().stream().filter(cell -> cell.equals(partyPlayer.getDungeonCell())).findFirst()
                .orElseThrow(() -> new DataLogicException(STR."Несуществующая ячейка: \{dungeonInstance.getId()}:\{partyPlayer.getDungeonCell().getX()}:\{partyPlayer.getDungeonCell().getY()}"));

        sb.append(getMinimap(dungeonInstance, cellWithUser)).append("\n");

        sb.append("На твоей клетке:").append("\n");

        if (cellWithUser.getCellType() == DungeonCellType.MONSTER || cellWithUser.getCellType() == DungeonCellType.BOSS) {
            sb.append("Монстры пока не сделаны :(").append("\n");
            // TODO return monsters
//            Monster monster = db.Monsters.Find(cellWithUser.Inside);
//            sb.append(cellWithUser.getCellType().GetString(monster));
        } else {
            sb.append(cellWithUser.getCellType().getString()).append("\n");
        }
        sb.append("\n");

        List<DungeonPartyPlayer> sameCellPlayers = cellWithUser.getDungeonPartyPlayers();
        if (sameCellPlayers != null && !sameCellPlayers.isEmpty()) {
            sb.append("Игроки рядом:").append("\n");
            for (DungeonPartyPlayer pp : sameCellPlayers) {
                sb.append(pp.getPlayer().getUsername());
            }
            sb.append("\n");
        }

        sb.append("Твоё состояние:").append("\n");
        StatsPlayer statsPlayer = partyPlayer.getPlayer().getStats();
        sb.append(STR."💚\{statsPlayer.getHpCurrent()}/\{statsPlayer.getHpTotal()} 🗡\{statsPlayer.getAttackTotal()} 🛡\{statsPlayer.getDefenceTotal()}");

        // TODO return weight
        //sb.append(STR."🎒{WeightSystem.WeightCalculator.CountWeightNow(user)}/{user.Backpack.MaxCount}");

        InlineKeyboardMarkup markup = dungeonInlineKeyboardGetter.getKeyboard(cellWithUser.getCellType());

        return new StringWithKeyboardResponse(sb.toString(), markup);
    }

    //Получает карту 3 на 3
    public String getMinimap(DungeonInstance dungeonInstance, DungeonCell cellWithPlayer) {
        final int VISION_RADIUS = 1;
        StringBuilder sb = new StringBuilder();

        try {
            int baseX = cellWithPlayer.getX();
            int baseY = cellWithPlayer.getY();

            for (int x = baseX - VISION_RADIUS; x <= baseX + VISION_RADIUS; x++) {
                for (int y = baseY - VISION_RADIUS; y <= baseY + VISION_RADIUS; y++) {
                    int finalX = x;
                    int finalY = y;
                    DungeonCell cell = dungeonInstance.getRooms().stream().filter(room -> room.getX() == finalX && room.getY() == finalY).findFirst()
                            .orElseThrow(() -> new DataLogicException(STR."Несуществующая ячейка: \{dungeonInstance.getId()}:\{finalX}:\{finalY}"));
                    if (!cell.isKnown()) {
                        cell.setKnown(true);
                        dungeonCellRepository.save(cell);
                    }
                    sb.append(cell.getCellType().getEmoji());
                }
                sb.append("\n");
            }
        } catch (Exception ex) {
            log.error("Error writing minimap for instance:{}", dungeonInstance.getId(), ex);
        }

        return sb.toString();
    }
}
