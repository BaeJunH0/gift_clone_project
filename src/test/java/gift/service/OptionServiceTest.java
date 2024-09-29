package gift.service;

import gift.DTO.Option.OptionRequest;
import gift.DTO.Option.OptionResponse;
import gift.TestUtil;
import gift.domain.Category;
import gift.domain.Option;
import gift.domain.Product;
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

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;


@ExtendWith(MockitoExtension.class)
public class OptionServiceTest {
    @Mock
    private OptionRepository optionRepository;
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private OptionService optionService;

    @Test
    @DisplayName("saveTest")
    void test1(){
        // given
        ArgumentCaptor<Option> captor_o = ArgumentCaptor.forClass(Option.class);

        OptionRequest optionRequest = new OptionRequest("[1] 기본", 1);
        Category  category = new Category("신규", "", "", "");
        Product product = new Product("product", 10000, "url", category);
        TestUtil.setId(product, 1L);
        given(productRepository.findById(1L)).willAnswer(invocation -> Optional.of(product));
        given(optionRepository.save(captor_o.capture())).willAnswer(invocation -> captor_o.getValue());
        // when
        OptionResponse savedOption = optionService.save(1L, optionRequest);
        // then
        Assertions.assertThat(savedOption.getName()).isEqualTo("[1] 기본");
        Assertions.assertThat(savedOption.getQuantity()).isEqualTo(1L);
    }

    @Test
    @DisplayName("findOptionsTest")
    void test2(){
        // given
        Category category = new Category("물품", "물품입니다", "Red", "Url");
        Product product = new Product("product", 10000, "url", category);
        TestUtil.setId(product, 1L);

        given(productRepository.findById(1L)).willAnswer(invocation -> Optional.of(product));
        // when
        List<OptionResponse> savedOptions = optionService.findOptions(1L);
        // then
        Assertions.assertThat(savedOptions).isNotNull();
        Assertions.assertThat((long) savedOptions.size()).isEqualTo(5);
        for(int i = 1; i <= 5; i++){
            String name = savedOptions.get(i-1).getName();
            Assertions.assertThat(name).isEqualTo("옵션"+i);
        }
    }

    @Test
    @DisplayName("updateTest")
    void test4(){
        // give
        Category category = new Category("물품", "물품입니다", "Red", "Url");
        Product product = new Product("product", 4500, "url", category);
        OptionRequest optionRequest = new OptionRequest("[1] 수정", 50);
        Option option = new Option("[1] 기본", 100, product);
        TestUtil.setId(option, 1L);
        given(optionRepository.findById(1L)).willAnswer(invocation -> Optional.of(option));
        // when
        optionService.update(1L, optionRequest);
        // then
        Assertions.assertThat(option.getName()).isEqualTo("[1] 수정");
        Assertions.assertThat(option.getQuantity()).isEqualTo(50L);
    }

    @Test
    @DisplayName("deleteTestSuccess")
    void test5(){
        // given
        Category category = new Category("물품", "물품입니다", "Red", "Url");
        Product product = new Product("product", 10000, "url", category);
        TestUtil.setId(product, 1L);

        given(productRepository.findById(1L)).willAnswer(invocation -> Optional.of(product));
        // when
        optionService.delete(1L, 1L);
        // then
        then(optionRepository).should(times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("deleteTestFail")
    void test6(){
        // given
        Category category = new Category("물품", "물품입니다", "Red", "Url");
        Product product = new Product("product", 10000, "url", category);
        TestUtil.setId(product, 1L);

        given(productRepository.findById(1L)).willAnswer(invocation -> Optional.of(product));
        // when
        optionService.delete(1L, 1L);
        // then
        then(optionRepository).should(times(0)).deleteById(1L);
    }
}
