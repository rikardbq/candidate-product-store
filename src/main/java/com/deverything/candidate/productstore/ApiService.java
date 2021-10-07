package com.deverything.candidate.productstore;

import java.util.List;

public interface ApiService<ProductObject, ProductDimensionsObject, BoxListObject, CheckoutObject, CheckoutSummaryObject> {

    /**
     * Should get a json list of products from API
     */
    public List<ProductObject> getProducts();

    /**
     * Should get a json object back with width and heigh for a given productId
     */
    public ProductDimensionsObject getProductDimensions(int productId);

    /**
     * Should get a json object with a list of boxes from the API
     * @return
     */
    public BoxListObject getBoxes();

    /**
     * Performs the checkout
     * @param checkoutObject json object that contains the boxId and the list of products to checkout
     * @return the checkout summary object
     */
    public CheckoutSummaryObject checkout(CheckoutObject checkoutObject);


}
