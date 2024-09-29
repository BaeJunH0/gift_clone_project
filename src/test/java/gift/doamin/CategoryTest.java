package gift.doamin;

import gift.domain.Category;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class CategoryTest {
    @Test
    void categoryTest(){
        Category category = new Category("물품", "물품입니다", "Red", "Url");
        Assertions.assertThat(category).isNotNull();

        category.update("신규", "새로 바뀐 카테고리", "Blue", "NewUrl");
        Assertions.assertThat(category.getName()).isEqualTo("신규");
    }
}
