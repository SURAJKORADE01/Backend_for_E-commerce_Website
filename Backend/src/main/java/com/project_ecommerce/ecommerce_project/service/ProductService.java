package com.project_ecommerce.ecommerce_project.service;

import com.project_ecommerce.ecommerce_project.model.Product;
import com.project_ecommerce.ecommerce_project.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService
{
    @Autowired
    private ProductRepo repo;

    public List<Product> getAllProducts()
    {
        return repo.findAll();
    }

    public Product getProductById(int id)
    {
        return repo.findById(id).orElse(null);          // orElse(new Product())
    }

    public Product addProduct(Product product, MultipartFile imageFile) throws IOException
    {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());

        return repo.save(product);
    }

    public Product updateProduct(int productId, Product product, MultipartFile imageFile) throws IOException
    {
//        If id is not present, this will not create it
//        if(0 < repo.findById(productId))
//        {
//
//        }

        product.setImageData(imageFile.getBytes());
        product.setImageName(imageFile.getName());
        product.setImageType(imageFile.getContentType());
        return repo.save(product);
    }

    public void deleteProduct(int productId)
    {
        repo.deleteById(productId);
    }

    public List<Product> searchProduct(String keyword)
    {
        return repo.searchProduct(keyword);
    }
}
