package ru.ilot.ilottower.model.enums.dungeon;

public enum DungeonCellType {
    EMPTY,
    WALL,
    EXIT,
    ENTER,

    MONSTER,
    MIMIC,

    BOSS,

    CHEST;

    public String getEmoji()
    {
        return switch (this) {
            case DungeonCellType.EMPTY -> "⬜️";
            case DungeonCellType.WALL -> "⬛️";
            case DungeonCellType.EXIT -> "🏁";
            case DungeonCellType.ENTER -> "🚪";
            case DungeonCellType.MONSTER -> "💀";
            case DungeonCellType.CHEST, DungeonCellType.MIMIC -> "🗃";
            case DungeonCellType.BOSS -> "👹";
            default -> "❔";
        };
    }

    public String GetUnknownEmoji()
    {
        return "❔";
    }

    public String getString()
    {
        return switch (this) {
            case DungeonCellType.EMPTY -> "⬜️ Пусто";
            case DungeonCellType.WALL -> "⬛️ Ты как в стену попал?";
            case DungeonCellType.EXIT -> "🏁 Выход";
            case DungeonCellType.ENTER -> "🚪 Вход";
            case DungeonCellType.CHEST, DungeonCellType.MIMIC -> "🗃 Сундук";
            default -> "❔ Что-то не так, напиши разрабам, будь няшкой";
        };
    }

    // TODO return monsters
//    public String getString(Monster monster)
//    {
//        switch (dungeonCellType)
//        {
//            case DungeonCellType.MONSTER:
//                return $"💀 {monster.Name}";
//            case DungeonCellType.BOSS:
//                return $"👹 {monster.Name}";
//            default:
//                return "❔ Разраб напутал функции, напиши разрабам, будь няшкой";
//        }
//    }
}
