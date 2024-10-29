package com.deverything.candidate.productstore;

import com.deverything.candidate.productstore.controller.RestTemplateResponseErrorHandler;
import com.deverything.candidate.productstore.model.api.*;
import com.deverything.candidate.productstore.model.exception.NoMatchingCriteriaException;
import com.deverything.candidate.productstore.service.ApiServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ApiServiceTest {

  @Mock
  private RestTemplate restTemplate;
  @Mock
  private RestTemplateResponseErrorHandler restTemplateResponseErrorHandler;
  @Mock
  private RestTemplateBuilder restTemplateBuilder;

  //@InjectMocks
  private ApiServiceImpl apiService;

  @BeforeEach
  public void setUp() {
    when(restTemplateBuilder.errorHandler(restTemplateResponseErrorHandler)).thenReturn(restTemplateBuilder);
    when(restTemplateBuilder.build()).thenReturn(restTemplate);
    apiService = new ApiServiceImpl(restTemplateBuilder, restTemplateResponseErrorHandler);
  }

  @Test
  public void calculatePackagingForProductsNoMatchingCriteriaException_NoProducts() {
    // TEST INPUTS
    long priceThreshold = 300;
    List<Integer> productIds = Arrays.asList(1, 2, 3);

    // PRODUCTOBJECT START
    ProductObject productObject1 = new ProductObject();

    Product product1 = new Product(1, "test1", 210);
    Product product2 = new Product(2, "test2", 220);
    Product product3 = new Product(3, "test3", 230);
    Product product4 = new Product(4, "test4", 310);
    Product product5 = new Product(5, "test5", 320);
    Product product6 = new Product(6, "test6", 330);

    productObject1.setStatusCode("200");
    productObject1.setProducts(Arrays.asList(
        product1,
        product2,
        product3,
        product4,
        product5,
        product6
    ));

    ResponseEntity<ProductObject> getProductsResponse1 = new ResponseEntity<>(productObject1, HttpStatus.OK);
    // PRODUCTOBJECT END

    // EXCEPTION EXPECTED MESSAGES START
    String eMessage = "Product list empty, no matching criteria";
    String eCause = "No products matched the criteria [productPrice > priceThreshold && productIds.contains(productId)] with threshold=" + priceThreshold + ",productIds=" + productIds;
    // EXCEPTION EXPECTED MESSAGE END

    ApiServiceImpl apiServiceSpy = spy(apiService);

    when(apiService.getProducts()).thenReturn(getProductsResponse1);

    Throwable exception = assertThrows(NoMatchingCriteriaException.class, () -> apiServiceSpy.calculatePackagingForProducts(priceThreshold, productIds));

    assertEquals(eMessage, exception.getMessage());
    assertEquals(eCause, exception.getCause().getMessage());

    verify(apiServiceSpy, times(1)).getProducts();
    verify(apiServiceSpy, times(1)).calculatePackagingForProducts(eq(300L), eq(Arrays.asList(1, 2, 3)));
  }

  @Test
  public void calculatePackagingForProductsNoMatchingCriteriaException_NoBox() {
    // TEST INPUTS
    long priceThreshold = 300;
    List<Integer> productIds = Arrays.asList(4, 5, 6);

    // PRODUCTOBJECT START
    ProductObject productObject1 = new ProductObject();

    Product product1 = new Product(1, "test1", 210);
    Product product2 = new Product(2, "test2", 220);
    Product product3 = new Product(3, "test3", 230);
    Product product4 = new Product(4, "test4", 310);
    Product product5 = new Product(5, "test5", 320);
    Product product6 = new Product(6, "test6", 330);

    productObject1.setStatusCode("200");
    productObject1.setProducts(Arrays.asList(
        product1,
        product2,
        product3,
        product4,
        product5,
        product6
    ));

    ResponseEntity<ProductObject> getProductsResponse1 = new ResponseEntity<>(productObject1, HttpStatus.OK);
    // PRODUCTOBJECT END

    // PRODUCTDIMENSIONOBJECT START
    ProductDimensionsObject productDimensionsObject1 = new ProductDimensionsObject("200", 1200, 2400);
    ProductDimensionsObject productDimensionsObject2 = new ProductDimensionsObject("200", 1200, 4200);
    ProductDimensionsObject productDimensionsObject3 = new ProductDimensionsObject("200", 1200, 5200);

    ResponseEntity<ProductDimensionsObject> getProductDimensionsResponse1 = new ResponseEntity<>(productDimensionsObject1, HttpStatus.OK);
    ResponseEntity<ProductDimensionsObject> getProductDimensionsResponse2 = new ResponseEntity<>(productDimensionsObject2, HttpStatus.OK);
    ResponseEntity<ProductDimensionsObject> getProductDimensionsResponse3 = new ResponseEntity<>(productDimensionsObject3, HttpStatus.OK);
    // PRODUCTDIMENSIONOBJECT END

    // BOXLISTOBJECT START
    Box box1 = new Box(1, 800, 200);
    Box box2 = new Box(2, 600, 2000);
    Box box3 = new Box(3, 600, 200);

    BoxListObject boxListObject1 = new BoxListObject("200", Arrays.asList(box1, box2, box3));

    ResponseEntity<BoxListObject> getBoxesResponse1 = new ResponseEntity<>(boxListObject1, HttpStatus.OK);
    // BOXLISTOBJECT END

    // PRODUCT DIMENSION MAX START
    int totalWidth = 3600;
    int maxHeight = 5200;
    // PRODUCT DIMENSION MAX END

    // EXCEPTION EXPECTED MESSAGES START
    String eMessage = "Box doesn't exist, no matching criteria";
    String eCause = "No box matched the criteria [boxHeight >= productsMaxHeight && boxWidth >= productsTotalWidth] with productsMaxHeight=" + maxHeight + ",productsTotalWidth=" + totalWidth;
    // EXCEPTION EXPECTED MESSAGE END

    ApiServiceImpl apiServiceSpy = spy(apiService);

    when(apiService.getProducts()).thenReturn(getProductsResponse1);
    when(apiService.getProductDimensions(product4.getId())).thenReturn(getProductDimensionsResponse1);
    when(apiService.getProductDimensions(product5.getId())).thenReturn(getProductDimensionsResponse2);
    when(apiService.getProductDimensions(product6.getId())).thenReturn(getProductDimensionsResponse3);
    when(apiService.getBoxes()).thenReturn(getBoxesResponse1);

    Throwable exception = assertThrows(NoMatchingCriteriaException.class, () -> apiServiceSpy.calculatePackagingForProducts(priceThreshold, productIds));

    assertEquals(eMessage, exception.getMessage());
    assertEquals(eCause, exception.getCause().getMessage());

    verify(apiServiceSpy, times(1)).getProducts();
    verify(apiServiceSpy, times(3)).getProductDimensions(anyInt());
    verify(apiServiceSpy, times(1)).getBoxes();
    verify(apiServiceSpy, times(1)).calculatePackagingForProducts(eq(300L), eq(Arrays.asList(4, 5, 6)));
  }

  @Test
  public void getProductsHasCorrectValues() {
    ProductObject productObject1 = new ProductObject();

    Product product1 = new Product(1, "test1", 210);
    Product product2 = new Product(2, "test2", 220);
    Product product3 = new Product(3, "test3", 230);
    Product product4 = new Product(4, "test4", 310);
    Product product5 = new Product(5, "test5", 320);
    Product product6 = new Product(6, "test6", 330);

    productObject1.setStatusCode("200");
    productObject1.setProducts(Arrays.asList(
        product1,
        product2,
        product3,
        product4,
        product5,
        product6
    ));

    ResponseEntity<ProductObject> getProductsResponse1 = new ResponseEntity<>(productObject1, HttpStatus.OK);

    when(apiService.getProducts()).thenReturn(getProductsResponse1);

    ResponseEntity<ProductObject> assertedProductObjectResponse = apiService.getProducts();
    assertNotNull(assertedProductObjectResponse);
    assertNotNull(assertedProductObjectResponse.getStatusCode());
    assertNotNull(assertedProductObjectResponse.getBody());
    assertEquals(HttpStatus.OK, assertedProductObjectResponse.getStatusCode());
    assertEquals("200", assertedProductObjectResponse.getBody().getStatusCode());
    assertEquals(Arrays.asList(product1, product2, product3, product4, product5, product6), assertedProductObjectResponse.getBody().getProducts());
  }

  @Test
  public void getProductDimensionsHasCorrectValues() {
    Product product1 = new Product(1, "test1", 210);

    ProductDimensionsObject productDimensionsObject1 = new ProductDimensionsObject("200", 200, 200);
    ResponseEntity<ProductDimensionsObject> getProductDimensionsResponse1 = new ResponseEntity<>(productDimensionsObject1, HttpStatus.OK);

    when(apiService.getProductDimensions(product1.getId())).thenReturn(getProductDimensionsResponse1);

    ResponseEntity<ProductDimensionsObject> assertedProductDimensionsObjectResponse = apiService.getProductDimensions(1);
    assertNotNull(assertedProductDimensionsObjectResponse);
    assertNotNull(assertedProductDimensionsObjectResponse.getStatusCode());
    assertNotNull(assertedProductDimensionsObjectResponse.getBody());
    assertEquals(HttpStatus.OK, assertedProductDimensionsObjectResponse.getStatusCode());
    assertEquals("200", assertedProductDimensionsObjectResponse.getBody().getStatusCode());
    assertEquals(200, assertedProductDimensionsObjectResponse.getBody().getWidth());
    assertEquals(200, assertedProductDimensionsObjectResponse.getBody().getHeight());
  }

  @Test
  public void getBoxesHasCorrectValues() {
    Box box1 = new Box(1, 800, 200);
    Box box2 = new Box(2, 600, 2000);
    Box box3 = new Box(3, 600, 200);

    BoxListObject boxListObject1 = new BoxListObject("200", Arrays.asList(box1, box2, box3));
    ResponseEntity<BoxListObject> getBoxesResponse1 = new ResponseEntity<>(boxListObject1, HttpStatus.OK);

    when(apiService.getBoxes()).thenReturn(getBoxesResponse1);

    ResponseEntity<BoxListObject> assertedBoxListObjectResponse = apiService.getBoxes();
    assertNotNull(assertedBoxListObjectResponse);
    assertNotNull(assertedBoxListObjectResponse.getStatusCode());
    assertNotNull(assertedBoxListObjectResponse.getBody());
    assertEquals(HttpStatus.OK, assertedBoxListObjectResponse.getStatusCode());
    assertEquals("200", assertedBoxListObjectResponse.getBody().getStatusCode());
    assertEquals(Arrays.asList(box1, box2, box3), assertedBoxListObjectResponse.getBody().getBoxes());
  }

  @Test
  public void calculatePackagingForProductsHappyCase() {
    // TEST INPUTS
    long priceThreshold = 300;
    List<Integer> productIds = Arrays.asList(4, 5, 6);

    // PRODUCTOBJECT START
    ProductObject productObject1 = new ProductObject();

    Product product1 = new Product(1, "test1", 210);
    Product product2 = new Product(2, "test2", 220);
    Product product3 = new Product(3, "test3", 230);
    Product product4 = new Product(4, "test4", 310);
    Product product5 = new Product(5, "test5", 320);
    Product product6 = new Product(6, "test6", 330);

    productObject1.setStatusCode("200");
    productObject1.setProducts(Arrays.asList(
        product1,
        product2,
        product3,
        product4,
        product5,
        product6
    ));

    ResponseEntity<ProductObject> getProductsResponse1 = new ResponseEntity<>(productObject1, HttpStatus.OK);
    // PRODUCTOBJECT END

    // PRODUCTDIMENSIONOBJECT START
    ProductDimensionsObject productDimensionsObject1 = new ProductDimensionsObject("200", 200, 200);
    ProductDimensionsObject productDimensionsObject2 = new ProductDimensionsObject("200", 200, 200);
    ProductDimensionsObject productDimensionsObject3 = new ProductDimensionsObject("200", 200, 200);

    ResponseEntity<ProductDimensionsObject> getProductDimensionsResponse1 = new ResponseEntity<>(productDimensionsObject1, HttpStatus.OK);
    ResponseEntity<ProductDimensionsObject> getProductDimensionsResponse2 = new ResponseEntity<>(productDimensionsObject2, HttpStatus.OK);
    ResponseEntity<ProductDimensionsObject> getProductDimensionsResponse3 = new ResponseEntity<>(productDimensionsObject3, HttpStatus.OK);
    // PRODUCTDIMENSIONOBJECT END

    // BOXLISTOBJECT START
    Box box1 = new Box(1, 800, 200);
    Box box2 = new Box(2, 600, 2000);
    Box box3 = new Box(3, 600, 200);

    BoxListObject boxListObject1 = new BoxListObject("200", Arrays.asList(box1, box2, box3));

    ResponseEntity<BoxListObject> getBoxesResponse1 = new ResponseEntity<>(boxListObject1, HttpStatus.OK);
    // BOXLISTOBJECT END

    // CHECKOUTSUMMARYOBJECT START
    CheckoutObject checkoutObject1 = new CheckoutObject(
        box3.getId(),
        Arrays.asList(product4, product5, product6).stream()
            .map(Product::getId)
            .collect(Collectors.toList())
    );

    CheckoutSummaryObject checkoutSummaryObject1 = new CheckoutSummaryObject("200", "something went well!");

    ResponseEntity<CheckoutSummaryObject> checkoutResponse1 = new ResponseEntity<>(checkoutSummaryObject1, HttpStatus.OK);
    // CHECKOUTSUMMARYOBJECT END

    ApiServiceImpl apiServiceSpy = spy(apiService);

    when(apiService.getProducts()).thenReturn(getProductsResponse1);
    when(apiService.getProductDimensions(product4.getId())).thenReturn(getProductDimensionsResponse1);
    when(apiService.getProductDimensions(product5.getId())).thenReturn(getProductDimensionsResponse2);
    when(apiService.getProductDimensions(product6.getId())).thenReturn(getProductDimensionsResponse3);
    when(apiService.getBoxes()).thenReturn(getBoxesResponse1);
    when(apiService.checkout(checkoutObject1)).thenReturn(checkoutResponse1);

    apiServiceSpy.calculatePackagingForProducts(priceThreshold, productIds);

    verify(apiServiceSpy, times(1)).getProducts();
    verify(apiServiceSpy, times(3)).getProductDimensions(anyInt());
    verify(apiServiceSpy, times(1)).getBoxes();
    verify(apiServiceSpy, times(1)).checkout(eq(checkoutObject1));
    verify(apiServiceSpy, times(1)).calculatePackagingForProducts(eq(300L), eq(Arrays.asList(4, 5, 6)));
  }
}