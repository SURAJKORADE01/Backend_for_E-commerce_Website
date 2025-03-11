package com.project_ecommerce.ecommerce_project.controller;

import com.project_ecommerce.ecommerce_project.model.Product;
import com.project_ecommerce.ecommerce_project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class ProductController
{
    @Autowired
    private ProductService service;

    @GetMapping("/")
    public String greet()
    {
        return "Hello World...";
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts()
    {
        return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id)
    {
        Product product = service.getProductById(id);

        if(product != null)
        {
            return new ResponseEntity<>(product, HttpStatus.OK);
        }

        else
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/product", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> addProduct(@RequestParam("name") String name,
                                        @RequestParam("brand") String brand,
                                        @RequestParam("description") String description,
                                        @RequestParam(value = "price", required = false, defaultValue = "0.0") BigDecimal price,
                                        @RequestParam("category") String category,
                                        @RequestParam("stock_quantity") int stockQuantity,
                                        @RequestParam("date") @DateTimeFormat(pattern = "EEE MMM dd yyyy HH:mm:ss 'GMT'Z (z)") Date date,
                                        @RequestParam(value = "isAvailable", defaultValue = "false") Boolean isAvailable,
                                        @RequestPart MultipartFile imageFile)
    {
        try
        {
//            System.out.println("Received product data");
//            System.out.println("Name: " + name);
//            System.out.println("Brand: " + brand);
//            System.out.println("Description: " + description);
//            System.out.println("Price: " + price);
//            System.out.println("Category: " + category);
//            System.out.println("Stock Quantity: " + stockQuantity);
//            System.out.println("Date: " + date);
//            System.out.println("Is Available: " + isAvailable);
//            System.out.println("Image File Name: " + imageFile.getOriginalFilename());
//            System.out.println("Received file: " + imageFile.getOriginalFilename());

            Product product = new Product();

            product.setName(name);
            product.setBrand(brand);
            product.setDescription(description);
            product.setPrice(price);
            product.setCategory(category);
            product.setQuantity(stockQuantity);
            product.setReleaseDate(date);
            product.setAvailable(isAvailable);

            product.setImageName(imageFile.getOriginalFilename());
            product.setImageType(imageFile.getContentType());
            product.setImageData(imageFile.getBytes());

            Product saveProduct = service.addProduct(product, imageFile);
            return new ResponseEntity<>(saveProduct, HttpStatus.CREATED);
        }

        catch (Exception e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/product/{productId}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int productId)
    {
        Product product = service.getProductById(productId);

        byte[] imageFile = product.getImageData();

        return ResponseEntity.ok().
                contentType(MediaType.valueOf(product.getImageType()))
                .body(imageFile);
    }

    @PutMapping("/product/{productId}")
    public ResponseEntity<String> updateProduct(@PathVariable int productId,
                                                @RequestPart Product product,
                                                @RequestPart MultipartFile imageFile)
    {
        Product product1 = null;

        try
        {
            product1 = service.updateProduct(productId, product, imageFile);
        }

        catch(IOException e)
        {
            return new ResponseEntity<>("Failed to update", HttpStatus.BAD_REQUEST);
        }

        if(product1 != null)
        {
            return new ResponseEntity<>("Updated", HttpStatus.OK);
        }

        else
        {
            return new ResponseEntity<>("Failed to update", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<String> deleteData(@PathVariable int productId)
    {
        Product product = service.getProductById(productId);

        if(product != null)
        {
            service.deleteProduct(productId);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        }

        else
        {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProduct(String keyword)
    {
        List<Product> products = service.searchProduct(keyword);

        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
