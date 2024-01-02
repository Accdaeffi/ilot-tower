package ru.ilot.ilottower.model.entities.items;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "image_properties")
public class ImgProperties {
    @Id
    public int id;
    public int imageId;
    public int malePaddingX;
    public int malePaddingY;
    public int femalePaddingX;
    public int femalePaddingY;
    public boolean isCloseBody;
}
