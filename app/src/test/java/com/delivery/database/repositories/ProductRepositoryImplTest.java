package com.delivery.database.repositories;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

import com.delivery.core.domain.Identity;
import com.delivery.core.domain.Product;
import com.delivery.core.entities.TestCoreEntityGenerator;
import com.delivery.core.mappers.CousineDomainDbMapper;
import com.delivery.core.mappers.ProductDomainDbMapper;
import com.delivery.core.mappers.StoreDomainDbMapper;
import com.delivery.database.entities.ProductDb;
import com.delivery.database.repositories.impl.ProductRepositoryImpl;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProductRepositoryImplTest {

    @InjectMocks private ProductRepositoryImpl productRepository;
    @Mock private DbProductRepository dbProductRepository;
    @InjectMocks private ProductDomainDbMapper productDomainDbMapper  = Mockito.spy(Mappers.getMapper(ProductDomainDbMapper.class));
    @InjectMocks private StoreDomainDbMapper storeDomainDbMapper  = Mockito.spy(Mappers.getMapper(StoreDomainDbMapper.class));
    @InjectMocks private CousineDomainDbMapper cousineDomainDbMapper  = Mockito.spy(Mappers.getMapper(CousineDomainDbMapper.class));

    @Test
    public void findProductsByStoreAndProductsIdReturnListOfProducts() {
        // given
        Identity storeId = TestCoreEntityGenerator.randomId();
        final Identity productId = TestCoreEntityGenerator.randomId();

        // and
        Product product = TestCoreEntityGenerator.randomProduct();
        ProductDb productData = productDomainDbMapper.mapToDb(product);

        doReturn(singletonList(productData))
                .when(dbProductRepository)
                .findByStoreIdAndIdIsIn(eq(storeId.getNumber()), eq(singletonList(productId.getNumber())));

        // when
        List<Product> actual = productRepository.searchProductsByStoreAndProductsId(storeId, singletonList(productId));

        // then
        assertThat(actual).isEqualTo(singletonList(product));
    }

    @Test
    public void searchByNameReturnMatchingProducts() {
        // given
        Product product = TestCoreEntityGenerator.randomProduct();
        ProductDb productData = productDomainDbMapper.mapToDb(product);
        String searchText = "abc";

        // and
        doReturn(singletonList(productData))
                .when(dbProductRepository)
                .findByNameContainingOrDescriptionContainingAllIgnoreCase(searchText, searchText);

        // when
        List<Product> actual = productRepository.searchByNameOrDescription(searchText);

        // then
        assertThat(actual).containsOnly(product);
    }

    @Test
    public void getByIdentityReturnsEmpty() {
        // given
        Identity id = TestCoreEntityGenerator.randomId();

        // and
        doReturn(Optional.empty())
                .when(dbProductRepository)
                .findById(id.getNumber());

        // when
        Optional<Product> actual = productRepository.getById(id);

        // then
        assertThat(actual.isPresent()).isFalse();
    }

    @Test
    public void getByIdentityReturnsProduct() {
        // given
        Product product = TestCoreEntityGenerator.randomProduct();
        Identity id = product.getId();
        ProductDb productData = productDomainDbMapper.mapToDb(product);

        // and
        doReturn(Optional.of(productData))
                .when(dbProductRepository)
                .findById(id.getNumber());

        // when
        Optional<Product> actual = productRepository.getById(id);

        // then
        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get()).isEqualTo(product);
    }

    @Test
    public void getAllReturnsAllProducts() {
        // given
        Product product = TestCoreEntityGenerator.randomProduct();
        ProductDb productData = productDomainDbMapper.mapToDb(product);

        // and
        doReturn(singletonList(productData))
                .when(dbProductRepository)
                .findAll();

        // when
        List<Product> actual = productRepository.getAll();

        // then
        assertThat(actual).containsOnly(product);
    }
}