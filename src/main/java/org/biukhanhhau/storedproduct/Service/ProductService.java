package org.biukhanhhau.storedproduct.Service;

import org.biukhanhhau.storedproduct.Model.Product;
import org.biukhanhhau.storedproduct.Repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepo repo;

    public List<Product> getAllProduct() {
        return repo.findAll();
    }

    public Product findProductById(int id) {
        return repo.findById(id).orElse(null);
    }

    public Product addProduct(Product product, MultipartFile imageFile){
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        try {
            product.setImageData(imageFile.getBytes());
        }catch (IOException e){product.setImageData(null);}
        return repo.save(product);
    }

    public void updateProduct(Product product, MultipartFile imageFile) {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        try{
            product.setImageData(imageFile.getBytes());
        } catch (IOException e) {
            System.out.println("Something went wrong");
        }
        repo.save(product);
    }

    public void deleteProduct(int id) {
        repo.deleteById(id);
    }
    public List<Product> findByKeyword(String keyword){
        return repo.searchProducts(keyword);
    }
}
