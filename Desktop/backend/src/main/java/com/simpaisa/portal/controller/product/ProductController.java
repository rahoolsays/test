package com.simpaisa.portal.controller.product;


import com.simpaisa.portal.entity.mysql.operator.OperatorIdAndName;
import com.simpaisa.portal.entity.mysql.product.Product;
import com.simpaisa.portal.entity.mysql.product.ProductView;
import com.simpaisa.portal.enums.Responses;
import com.simpaisa.portal.service.product.ProductService;
import com.simpaisa.portal.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.*;

@RestController
@RequestMapping("product")
@CrossOrigin(origins = "*")
public class ProductController {
    @Autowired
    private ProductService productService;


    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody Product product){
        Product result = null;

        System.out.println(" product = " + product.toString());
        try{

            result = productService.save(product);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<Product>(result, HttpStatus.OK);
    }



    @GetMapping("")
    public ResponseEntity<?> findById(@RequestParam("id") long id){
        Product product = null;
        try{
            product = productService.findById(id);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<Product>(product, HttpStatus.OK);
    }



    @PostMapping("/findByMerchantId")
    public Page<Product> findByMerchantId(@RequestParam("merchantId") Long merchantId, @RequestParam("orderBy") String orderBy, @RequestParam("direction") String direction,
                                              @RequestParam("pageNo") int pageNo, @RequestParam("size") int size){
        Page<Product> products = null;
        try{
            products = productService.findByMerchantId(merchantId,orderBy,direction, pageNo, size);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return products;
    }

    @PostMapping("/findByOnLive")
    public Page<Product> findByOnLive( @RequestParam("onLive") int onLive, @RequestParam("orderBy") String orderBy, @RequestParam("direction") String direction,
                                          @RequestParam("pageNo") int pageNo, @RequestParam("size") int size){
        Page<Product> products = null;
        try{
            products = productService.findByOnLive(onLive,orderBy,direction, pageNo, size);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return products;
    }

    @PostMapping("/findAll")
    public Page<Product> findAll(@RequestParam("orderBy") String orderBy, @RequestParam("direction") String direction,
                                       @RequestParam("pageNo") int pageNo, @RequestParam("size") int size){
        Page<Product> products = null;
        try{
            products = productService.findAll(orderBy,direction, pageNo, size);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return products;
    }

    @GetMapping("getProductIds")
    public ResponseEntity getProductIds(@RequestParam("merchantId") Long merchantId){
        List<ProductView> productViews = null;
        try{
            productViews  = productService.getProductIds(merchantId);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<List<ProductView>>(productViews, HttpStatus.OK);
    }

    @GetMapping("/getAllProducts")
    public ResponseEntity getAllProducts(){
        List<Product> productViews = null;

        try{
            productViews = productService.getAllProducts();

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<List<Product>>(productViews, HttpStatus.OK);
    }



    @DeleteMapping("delete")
    public ResponseEntity<?> deleteById(@RequestParam("id") Long id ){
        try{

            productService.deleteById(id);
            return new ResponseEntity<LinkedHashMap<String, Object>>(Utility.getResponse(Responses.SUCCESS), HttpStatus.OK);

        }catch (Exception ex){
            ex.printStackTrace();
            return new ResponseEntity<LinkedHashMap<String, Object>>(Utility.getResponse(Responses.SYSTEM_ERROR), HttpStatus.OK);
        }
    }

    @PostMapping("/create/live")
    public ResponseEntity goLive(@RequestBody HashMap<String, String> data){
        Map<String , Object> response = null;
        try{
            System.out.println("Product Going Live");
            response = productService.goLive(data);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<HashMap<String, Object>>((HashMap<String, Object>)response , HttpStatus.OK);


    }
}
