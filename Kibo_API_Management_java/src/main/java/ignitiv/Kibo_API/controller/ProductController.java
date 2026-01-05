package ignitiv.Kibo_API.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.kibocommerce.sdk.catalogadministration.models.CatalogAdminsProduct;
import com.kibocommerce.sdk.catalogadministration.models.CatalogAdminsProductCollection;
import com.kibocommerce.sdk.common.ApiException;

import ignitiv.Kibo_API.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService client;

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable String productId) {
        try {
            CatalogAdminsProduct product = client.getProductById(productId);
            return ResponseEntity.ok(product);
        } catch (ApiException e) {
            return buildErrorResponse(e);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        try {
            CatalogAdminsProductCollection products = client.getAllProducts();
            return ResponseEntity.ok(products);
        } catch (ApiException e) {
            return buildErrorResponse(e);
        }
    }

    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody CatalogAdminsProduct product) {
        try {
            CatalogAdminsProduct addedProduct = client.addProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedProduct);
        } catch (ApiException e) {
            return buildErrorResponse(e);
        }
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable String productId) {
        try {
            client.deleteProduct(productId);
            return ResponseEntity.ok("Product deleted");
        } catch (ApiException e) {
            return buildErrorResponse(e);
        }
    }

   
    private ResponseEntity<String> buildErrorResponse(ApiException e) {
        int code = e.getCode();

        HttpStatus status = (code >= 100 && code <= 599)
                ? HttpStatus.valueOf(code)
                : HttpStatus.INTERNAL_SERVER_ERROR;

        return ResponseEntity
                .status(status)
                .body(e.getMessage());
    }
}
