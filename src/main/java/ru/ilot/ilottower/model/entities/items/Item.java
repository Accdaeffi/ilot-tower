package ru.ilot.ilottower.model.entities.items;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import ru.ilot.ilottower.enums.RareType;
import ru.ilot.ilottower.enums.SlotType;
import ru.ilot.ilottower.model.entities.BackpackItem;

import java.util.List;

@Data
@Entity
@Table(name = "Items")
public class Item {
    @Id
    @Column(name = "Id")
    public int id;

    @Column(name = "Name")
    public String name;

    @Column(name = "Slot")
    @Enumerated(EnumType.ORDINAL)
    public SlotType slotType;

    @Column(name = "Rare")
    @Enumerated(EnumType.ORDINAL)
    public RareType rareType;

    @Column(name = "Weight")
    public double weight;
    //public virtual ItemProperties Properties;

    // TODO refactor items
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "item")
//    public List<BackpackItem> backpackItems;
//
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recipe")
//    public  List<IngridientRecipe> ingredientRecipes;
//
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "item")
//    public List<NpcItem> npcItems;
//
//    //public virtual ImgProperties Image;
//    @OneToOne(fetch = FetchType.LAZY)
//    public ImgProperties imageProperties;
//
//    public int CategoryId;
//    public virtual ItemCategory category;
//    public int hp = 0;
//    public int attack = 0;
//    public int defence = 0;
//    public Skill skill;
//    public Integer userLevel = 1;
//    //public int SkillLevel; = 1;
//    public int buyPrice = 100;
//    public int sellPrice = 25;
//    public String info;
//    public boolean selling;
//    public Integer floor;
}
