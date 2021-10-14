package com.simpaisa.portal.repository.interfaces;

import com.simpaisa.portal.entity.mysql.product.Product;

public interface ProductRepo {
    public long insert(Product product, short environment);
    public Product findById(long id, short environment);
    public com.simpaisa.portal.entity.mongo.Product findByIdMongo(short environment);
    public com.simpaisa.portal.entity.mongo.Product save(com.simpaisa.portal.entity.mongo.Product product);
}
