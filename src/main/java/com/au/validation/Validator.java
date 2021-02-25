package com.au.validation;

import com.au.model.Product;
import com.au.model.Products;

import java.util.List;

public class Validator {

    public boolean isValidQty(Products products){
        int qtysum = products.getProducts().stream().mapToInt(product -> product.getQty()).sum();

        return products.getTransmissionsummary().getQtysum() == qtysum;
    }

    public boolean isValidRecordCount(Products products){
        return products.getProducts().size() == products.getTransmissionsummary().getRecordcount();
    }
}
