package ru.ilot.ilottower.telegram.keyboard;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.ilot.ilottower.model.enums.dungeon.DungeonCellType;

import java.util.ArrayList;
import java.util.List;

@Service
public class DungeonInlineKeyboardGetter {

    public InlineKeyboardMarkup getKeyboard(DungeonCellType cellType) {
        String text = null;
        String callback = null;
        switch (cellType) {
            case DungeonCellType.CHEST:
            case DungeonCellType.MIMIC: {
                text = "🗃 Открыть";
                callback = "openChest";
            }
            break;
            case DungeonCellType.EXIT: {
                text = "🏃‍♂️ Выход";
                callback = "exitDungeon";
            }
            break;
            case DungeonCellType.MONSTER: {
                text = "🗡 Атаковать";
                callback = "attackMonster";
            }
            break;
            case DungeonCellType.BOSS: {
                text = "⚔️ Атаковать";
                callback = "attackBoss";
            }
            break;
        }
        List<List<InlineKeyboardButton>> result = new ArrayList<>();

        if (text != null && callback != null) {
            InlineKeyboardButton button = InlineKeyboardButton.builder().text(text).callbackData(callback).build();
            result.add(List.of(button));
        }

        List<InlineKeyboardButton> secondRow = List.of(InlineKeyboardButton.builder().text("🗺 Карта").callbackData("map").build(),
                InlineKeyboardButton.builder().text("⬆️ Вверх").callbackData("top").build(),
                InlineKeyboardButton.builder().text("🎒 Рюкзак").callbackData("backpack").build());
        result.add(secondRow);

        List<InlineKeyboardButton> thirdRow = List.of(
                InlineKeyboardButton.builder().text("⬅️ Влево").callbackData("left").build(),
                InlineKeyboardButton.builder().text("⬇️ Вниз").callbackData("down").build(),
                InlineKeyboardButton.builder().text("➡️ Вправо").callbackData("right").build());
        result.add(thirdRow);

        return new InlineKeyboardMarkup(result);
    }
}
