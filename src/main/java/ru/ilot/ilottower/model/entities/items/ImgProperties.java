package ru.ilot.ilottower.model.entities.items;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "image_properties")
public class ImgProperties {
    @Id
    @Column(name = "id")
    public int id;

    @Column(name = "image_id")
    public int imageId;

    @Column(name = "male_padding_x")
    public int malePaddingX;

    @Column(name = "male_padding_y")
    public int malePaddingY;
    @Column(name = "female_padding_x")
    public int femalePaddingX;
    @Column(name = "female_padding_y")
    public int femalePaddingY;

    @Column(name = "is_close_body")
    public boolean isCloseBody;
}
