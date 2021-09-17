package com.deverything.candidate.productstore;

import java.util.List;

public interface ApiService<ProductObject, ProductDimensionsObject, BoxObject, CheckoutSummeryObject> {

    /**
     * Should get a json list of products from API
     */
    public List<ProductObject> getProducts();

    /**
     * Should get a json object back with width and heigh for a given productId
     */
    public ProductDimensionsObject getProductDimensions(int productId);

    /**
     * Should find all products with a price higher then 300
     */
    public List<ProductObject> listOfProductsWithPriceHigherThen300(List<ProductObject> products);

    /**
     * Should post a request to API to create a box with required dimensions to fit selected products
     */
    public BoxObject postBoxSize(List<ProductDimensionsObject> productDimensionsList);

    /**
     * Places an order for the box and products against the API
     */
    public CheckoutSummeryObject checkout(BoxObject box);


}
