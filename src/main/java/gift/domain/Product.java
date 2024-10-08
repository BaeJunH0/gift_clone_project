package gift.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
public class Product extends BaseEntity{
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    @Column(name = "price", nullable = false)
    private int price;
    @Column(name = "imageUrl", nullable = false)
    private String imageUrl;
    @ManyToOne
    @JoinColumn(
            name = "category_id",
            foreignKey = @ForeignKey(name = "fk_product_category_id_ref_category_id"),
            nullable = false)
    private Category category;

    protected Product(){
        super();
    }

    public Product(String name, int price, String imageUrl, Category category) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
    }

    public Long getId() {
        return super.getId();
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Category getCategory(){
        return category;
    }

    public void updateEntity(String name, int price, String imageUrl, Category category){
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
    }
}
