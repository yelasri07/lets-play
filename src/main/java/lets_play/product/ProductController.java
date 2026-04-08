package lets_play.product;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lets_play.user.User;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductDTO.ProductOutput> getAll() {
        return this.productService.getProducts();
    }

    @PostMapping
    public ProductDTO.ProductOutput post(@Valid @RequestBody ProductDTO.ProductInput productData,
            @AuthenticationPrincipal User user) {
        return this.productService.createProduct(productData, user.getId());
    }

}
