package gift.doamin;

import gift.domain.Category;
import gift.domain.Option;
import gift.domain.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProductTest {
    @Test
    void productTest(){
        Category category = new Category("물품", "물품입니다", "Red", "Url");
        Product product = new Product("name", 4500, "none", category);
        Assertions.assertThat(product).isNotNull();

        product.updateEntity("name_", 9000, "url", category);
        Assertions.assertThat(product.getName()).isEqualTo("name_");
        Assertions.assertThat(product.getPrice()).isEqualTo(9000);
        Assertions.assertThat(product.getImageUrl()).isEqualTo("url");
        Assertions.assertThat(product.getCategory()).isEqualTo(category);
    }
}
