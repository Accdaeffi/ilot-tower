package ru.ilot.ilottower.logic.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ru.ilot.ilottower.model.entities.dungeon.Dungeon;
import ru.ilot.ilottower.model.entities.dungeon.DungeonCell;
import ru.ilot.ilottower.model.entities.dungeon.DungeonInstance;
import ru.ilot.ilottower.model.entities.dungeon.DungeonParty;
import ru.ilot.ilottower.model.enums.dungeon.DungeonCellType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Stack;

@Service
@Scope("prototype")
public class DungeonGenerator {
    private final int DISTANCE = 2; // do not edit without remaking base matrice and todo

    DungeonParty party;
    int partySize;

    Dungeon reference;

    DungeonInstance instance;
    int height;
    int width;

    public DungeonGenerator(DungeonParty party, Dungeon buildingDungeon) {
        this.party = party;
        partySize = party.getPlayers().size();
        reference = buildingDungeon;
    }

    public DungeonInstance generateDungeon() {
        instance = new DungeonInstance();
        instance.setParty(party);

        //Generation sizes: reference + party size + outer walls

        height = reference.getHeight() + 2 * ((partySize) / 2) + 2;
        width = reference.getWidth() + 2 * ((partySize - 1) / 2) + 2;

        instance.setHeight(height);
        instance.setWidth(width);

        List<DungeonCell> cells = Arrays.stream(generateRooms()).flatMap(Arrays::stream).toList();
        instance.setRooms(cells);

        return instance;
    }

    private DungeonCell[][] generateRooms() {
        Random r = new Random();
        DungeonCell[][] result = generateBase();
        boolean[][] visited = new boolean[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                visited[i][j] = false;
            }
        }

        Cell currentCell = new Cell(1, 1); //https://m.habr.com/ru/post/262345/
        Cell neighbourCell = new Cell();
        Stack<Cell> cells = new Stack<Cell>();
        do {
            List<Cell> neighbours = getNeighbours(currentCell, visited, false);
            if (!neighbours.isEmpty()) {
                int rand = r.nextInt(0, neighbours.size());
                neighbourCell = neighbours.get(rand);
                cells.push(currentCell);
                result[(currentCell.height + neighbourCell.height) / 2][(currentCell.width + neighbourCell.width) / 2].setCellType(DungeonCellType.EMPTY);
                currentCell = neighbourCell;
                visited[currentCell.height][currentCell.width] = true;
            } else if (!cells.isEmpty()) {
                visited[currentCell.height][currentCell.width] = true;
                currentCell = cells.pop();
            } else {
                break;
            }
        }
        while (true);

        List<DungeonCell> emptyCells = new ArrayList<>();

        result[1][1].setCellType(DungeonCellType.ENTER);
        result[height - 2][width - 2].setCellType(DungeonCellType.EXIT);

        for (int i = 1; i < height - 1; i++) {
            for (int j = 1; j < width - 1; j++) {
                if (result[i][j].getCellType() == DungeonCellType.WALL) {
                    if (r.nextInt(100) < 20) {
                        List<Cell> neighbours = getNeighbours(new Cell(i, j), visited, true);
                        int countEmpty = 0;
                        for (Cell c : neighbours) {
                            if (result[c.height][c.width].getCellType() == DungeonCellType.EMPTY) countEmpty++;
                        }
                        if (countEmpty >= 2) {
                            result[i][j].setCellType(DungeonCellType.EMPTY);
                        }
                    }
                }

                if (result[i][j].getCellType() == DungeonCellType.EMPTY) {
                    emptyCells.add(result[i][j]);
                }
            }
        }

        // TODO add monsters
//        using var db = new IlotDbContext();
//
//        try {
//
//            int monsterCount = reference.MonsterCount;
//
//            Monster[] monsters = db.Monsters.Where(m = > m.LocationType == TypeLocation.Dungeon && m.Level == reference.Level && !m.IsBoss).
//            ToArray();
//            Monster[] mimics = db.Monsters.Where(m = > m.LocationType == TypeLocation.Dungeon && m.Level == (reference.Level - (((reference.Level - 1) % 5) + 1) + 1) && !m.IsBoss).
//            ToArray();
//
//            for (int i = 0; i < monsterCount; i++) {
//                int index = r.Next(emptyCells.Count());
//                DungeonCell selected = emptyCells.ElementAt(index);
//
//                if (mimics.Length == 0 || r.Next(100) > 10) {
//                    int selectedmonster = r.Next(monsters.Count());
//
//                    result[selected.X, selected.Y].Type = DungeonCellType.Monster;
//                    result[selected.X, selected.Y].Inside = monsters[selectedmonster].Id;
//                } else {
//                    int selectedmimic = r.Next(mimics.Count());
//
//                    result[selected.X, selected.Y].Type = DungeonCellType.Mimic;
//                    result[selected.X, selected.Y].Inside = mimics[selectedmimic].Id;
//                }
//
//                emptyCells.Remove(selected);
//            }
//
//            int bossCount = reference.BossCount;
//
//            List<Monster> bosses = db.Monsters.Where(m = > m.LocationType == TypeLocation.Dungeon && m.Level == reference.Level && m.IsBoss).
//            ToList();
//
//            while (bossCount > 0 && bosses.Count > 0) {
//                int index = r.Next(emptyCells.Count());
//                DungeonCell selected = emptyCells.ElementAt(index);
//
//                int selectedmonster = r.Next(bosses.Count());
//
//                result[selected.X, selected.Y].Type = DungeonCellType.Boss;
//                result[selected.X, selected.Y].Inside = bosses[selectedmonster].Id;
//
//                bossCount--;
//                bosses.RemoveAt(selectedmonster);
//                emptyCells.Remove(selected);
//            }
//
//            int floor = Convert.ToInt32(db.Buildings.FirstOrDefault(b = > b.Id == reference.Id).Location.LevelId);
//            Item[] items = db.Items.Where(i = > i.Rare >= RareType.Rare && (i.Floor == floor) && (i.CategoryId == 6)).
//            ToArray(); //MONSTERDROP CATEGORY
//            int chestCount = reference.ChestCount;
//
//            for (int i = 0; i < chestCount; i++) {
//                int index = r.Next(emptyCells.Count());
//                int selecteditem = r.Next(items.Count());
//
//                Console.WriteLine($"{selecteditem} {items.Count()}");
//
//                DungeonCell selected = emptyCells.ElementAt(index);
//                result[selected.X, selected.Y].Type = DungeonCellType.Chest;
//                result[selected.X, selected.Y].Inside = items[selecteditem].Id;
//
//                emptyCells.Remove(selected);
//            }
//        } catch (Exception ex) {
//            Console.WriteLine($"{ex.Message} {ex.StackTrace}");
//        }

        return result;
    }

    private DungeonCell[][] generateBase() {
        DungeonCell[][] result = new DungeonCell[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                result[i][j] = DungeonCell.builder()
                        .x(i)
                        .y(j)
                        .dungeon(instance)
                        .build();
                if (i == height - 1 || j == width - 1 || i == 0 || j == 0) {
                    result[i][j].setCellType(DungeonCellType.WALL);
                    result[i][j].setKnown(true);
                } else {
                    if (((i % 2 == 0) || (j % 2 == 0))) {
                        result[i][j].setCellType(DungeonCellType.WALL);
                        result[i][j].setKnown(false);
                    } else {
                        result[i][j].setCellType(DungeonCellType.EMPTY);
                        result[i][j].setKnown(false);
                    }
                }
            }
        }
        return result;
    }

    private List<Cell> getNeighbours(Cell currentCell, boolean[][] visited, boolean notCareAboutVisited) {
        int x = currentCell.getHeight();
        int y = currentCell.getWidth();
        Cell up = new Cell(x, y - DISTANCE);
        Cell rt = new Cell(x + DISTANCE, y);
        Cell dw = new Cell(x, y + DISTANCE);
        Cell lt = new Cell(x - DISTANCE, y);
        Cell[] d = {dw, rt, up, lt};

        List<Cell> result = new ArrayList<>();

        for (Cell cell : d) {
            if ((cell.height > 0) &&
                    (cell.height < height) &&
                    (cell.width > 0) &&
                    (cell.width < width) &&
                    (notCareAboutVisited || (!visited[cell.height][cell.width]))) {
                result.add(cell);
            }
        }

        return result;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Cell {
        private int height;
        private int width;
    }
}
