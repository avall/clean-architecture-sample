package com.delivery.database.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import com.delivery.core.domain.Identity;
import com.delivery.core.domain.Product;
import com.delivery.core.domain.Store;
import com.delivery.core.entities.TestCoreEntityGenerator;
import com.delivery.core.mappers.CousineDomainDbMapper;
import com.delivery.core.mappers.CustomerDomainDbMapper;
import com.delivery.core.mappers.OrderDomainDbMapper;
import com.delivery.core.mappers.OrderItemDomainDbMapper;
import com.delivery.core.mappers.ProductDomainDbMapper;
import com.delivery.core.mappers.StoreDomainDbMapper;
import com.delivery.database.entities.ProductDb;
import com.delivery.database.entities.StoreDb;
import com.delivery.database.repositories.impl.StoreRepositoryImpl;
import java.util.Collections;
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
public class StoreRepositoryImplTest {

    @InjectMocks private StoreRepositoryImpl storeRepository;
    @InjectMocks private OrderItemDomainDbMapper orderItemDomainDbMapper  = Mockito.spy(Mappers.getMapper(OrderItemDomainDbMapper.class));
    @InjectMocks private CustomerDomainDbMapper customerDomainDbMapper  = Mockito.spy(Mappers.getMapper(CustomerDomainDbMapper.class));
    @InjectMocks private StoreDomainDbMapper storeDomainDbMapper = Mockito.spy(Mappers.getMapper(StoreDomainDbMapper.class));
    @InjectMocks private OrderDomainDbMapper orderDomainDbMapper = Mockito.spy(Mappers.getMapper(OrderDomainDbMapper.class));;
    @InjectMocks private CousineDomainDbMapper cousineDomainDbMapper = Mockito.spy(Mappers.getMapper(CousineDomainDbMapper.class));;
    @InjectMocks private ProductDomainDbMapper productDomainDbMapper = Mockito.spy(Mappers.getMapper(ProductDomainDbMapper.class));;

    @Mock private DbStoreRepository dbStoreRepository;

    @Test
    public void getAllReturnsAllStore() {
        // given
        Store store = TestCoreEntityGenerator.randomStore();
        StoreDb storeData = storeDomainDbMapper.mapToDb(store);

        // and
        doReturn(Collections.singletonList(storeData))
                .when(dbStoreRepository)
                .findAll();

        // when
        List<Store> actual = storeRepository.getAll();

        // then
        assertThat(actual).containsOnly(store);
    }

    @Test
    public void searchStoresByNameReturnsAllMatchStores() {
        // given
        String text = "abc";
        Store store = TestCoreEntityGenerator.randomStore();
        StoreDb storeData = storeDomainDbMapper.mapToDb(store);

        // and
        doReturn(Collections.singletonList(storeData))
                .when(dbStoreRepository)
                .findByNameContainingIgnoreCase(text);

        // when
        List<Store> actual = storeRepository.searchByName(text);

        // then
        assertThat(actual).containsOnly(store);
    }

    @Test
    public void getStoreByIdentityReturnsOptionalStore() {
        // given
        Store store = TestCoreEntityGenerator.randomStore();
        StoreDb storeData = storeDomainDbMapper.mapToDb(store);

        // and
        doReturn(Optional.of(storeData))
                .when(dbStoreRepository)
                .findById(store.getId().getNumber());

        // when
        Optional<Store> actual = storeRepository.getById(store.getId());

        // then
        assertThat(actual).isEqualTo(Optional.of(store));
    }

    @Test
    public void getStoreByIdentityReturnsOptionalEmpty() {
        // given
        Identity id = TestCoreEntityGenerator.randomId();

        // and
        doReturn(Optional.empty())
                .when(dbStoreRepository)
                .findById(id.getNumber());

        // when
        Optional<Store> actual = storeRepository.getById(id);

        // then
        assertThat(actual).isEqualTo(Optional.empty());
    }

    @Test
    public void getProductsByIdentityReturnsProducts() {
        // given
        Product product = TestCoreEntityGenerator.randomProduct();
        ProductDb productData = productDomainDbMapper.mapToDb(product);
        Identity id = product.getStore().getId();

        // and
        doReturn(Collections.singletonList(productData))
                .when(dbStoreRepository)
                .findProductsById(id.getNumber());

        //when
        List<Product> actual = storeRepository.getProductsById(id);

        // then
        assertThat(actual).containsOnly(product);
    }

    @Test
    public void getStoresByIdentityReturnsStores() {
        // given
        Store store = TestCoreEntityGenerator.randomStore();
        Identity id = TestCoreEntityGenerator.randomId();

        StoreDb storeData = storeDomainDbMapper.mapToDb(store);

        // and
        doReturn(Collections.singletonList(storeData))
            .when(dbStoreRepository)
            .findStoresById(id.getNumber());

        // when
        final List<Store> actual = storeRepository.getStoresById(id);

        // then
        assertThat(actual).containsOnly(store);
    }


}