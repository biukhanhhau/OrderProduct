package org.biukhanhhau.storedproduct;

import org.biukhanhhau.storedproduct.Model.Product;
import org.biukhanhhau.storedproduct.Service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import java.security.Provider;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173/")
public class HomeController {

    private final ProductService productService;
    public HomeController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping("products")
    public ResponseEntity<List<Product>> getAllProduct(){
        List<Product> temps = productService.getAllProduct();
        return ResponseEntity.ok(temps);
    }

    @GetMapping("product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id){
        Product product = productService.findProductById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping("product/{id}/image")
    public ResponseEntity<byte[]> image(@PathVariable int id){
        byte[] imageDa = productService.findProductById(id).getImageData();
        return ResponseEntity.ok(imageDa);
    }

    @PostMapping("product")
    public ResponseEntity<Product> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile){
        Product product1 = productService.addProduct(product, imageFile);
        return ResponseEntity.ok(product1);
    }

    @PutMapping("product/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable int id ,@RequestPart Product product, @RequestPart MultipartFile imageFile){
        Product productTemp = productService.findProductById(id);
        if (productTemp == null){return ResponseEntity.notFound().build();}
        productService.updateProduct(product, imageFile);
        return ResponseEntity.ok(productTemp);
    }

    @DeleteMapping("product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){
        Product delProduct = productService.findProductById(id);
        if (delProduct != null){
            productService.deleteProduct(id);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);}
        return ResponseEntity.notFound().build();
    }

    @GetMapping("products/search")
    public ResponseEntity<List<Product>> searchProduct(@RequestParam String keyword){
        List<Product> products = productService.findByKeyword(keyword);
        if (products == null){ResponseEntity.notFound().build();}
        return ResponseEntity.ok(products);
    }
}
