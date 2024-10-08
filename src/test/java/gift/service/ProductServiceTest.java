package gift.service;

import gift.DTO.Product.ProductRequest;
import gift.DTO.Product.ProductResponse;
import gift.TestUtil;
import gift.domain.Category;
import gift.domain.Option;
import gift.domain.Product;
import gift.repository.CategoryRepository;
import gift.repository.OptionRepository;
import gift.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private OptionRepository optionRepository;
    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("saveTest")
    void test1() {
        // given
        ArgumentCaptor<String> captor_s = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Product> captor_p = ArgumentCaptor.forClass(Product.class);
        ArgumentCaptor<Option> captor_o = ArgumentCaptor.forClass(Option.class);
        ProductRequest productRequest = new ProductRequest(
                "product", 4500, "url", "신규", "[0] 기본"
        );
        given(categoryRepository.findByName(captor_s.capture())).willAnswer(invocation -> {
            String capturedName = captor_s.getValue();
            return new Category(capturedName, "", "", "");
        });
        given(productRepository.save(captor_p.capture())).willAnswer(invocation -> captor_p.getValue());
        given(optionRepository.save(captor_o.capture())).willAnswer(invocation -> captor_o.getValue());
        // when
        ProductResponse savedProduct = productService.save(productRequest);
        // then
        Assertions.assertThat(savedProduct).isNotNull();
        Assertions.assertThat(savedProduct.getName()).isEqualTo("product");
        Assertions.assertThat(savedProduct.getPrice()).isEqualTo(4500);
        Assertions.assertThat(savedProduct.getImageUrl()).isEqualTo("url");
    }

    @Test
    @DisplayName("readAllProductASCTest")
    void test2() {
        // given
        List<Product> products = new ArrayList<>();
        Category category = new Category("물품", "", "", "");
        TestUtil.setId(category, 1L);
        for (int i = 1; i <= 5; i++) {
            Product product = new Product("product" + i, 4500, "url" + i, category);
            products.add(product);
        }

        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.asc("name"));
        Pageable pageable = PageRequest.of(0, 5, Sort.by(sorts));
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), products.size());
        List<Product> subList = products.subList(start, end);

        Page<Product> page = new PageImpl<>(subList, pageable, products.size());
        given(productRepository.findAllByCategoryId(pageable, anyLong())).willAnswer(invocation -> page);
        // when
        Page<ProductResponse> pageResult = productService.readAllProduct(pageable, 1L);
        // then
        Assertions.assertThat(pageResult).isNotNull();
        Assertions.assertThat(pageResult.get().count()).isEqualTo(5);
        List<ProductResponse> content = pageResult.getContent();
        for(int i = 1; i <= 5; i++){
            String name = content.get(i-1).getName();
            Assertions.assertThat(name).isEqualTo("product"+i);
        }
    }

    @Test
    @DisplayName(("updateTest"))
    void test4(){
        // given
        ProductRequest productRequest = new ProductRequest(
                "newProduct", 4000, "url", "신규", ""
        );
        Category category = new Category("물품", "", "", "");
        Product product = new Product("product", 4500, "none", category);

        given(productRepository.findById(any(Long.class))).willAnswer(invocation -> Optional.of(product));

        Category newCategory = new Category("신규", "", "", "");
        given(categoryRepository.findByName(any(String.class))).willAnswer(invocation -> newCategory);

        // when
        productService.updateProduct(productRequest, 1L);

        // then
        Assertions.assertThat(product.getName()).isEqualTo("newProduct");
        Assertions.assertThat(product.getPrice()).isEqualTo(4000);
        Assertions.assertThat(product.getImageUrl()).isEqualTo("url");
        Assertions.assertThat(product.getCategory().getName()).isEqualTo("신규");
    }

    @Test
    @DisplayName("deleteTest")
    void test5() {
        // when
        productService.deleteProduct(1L);
        // then
        then(productRepository).should(times(1)).deleteById(1L);
    }
}
