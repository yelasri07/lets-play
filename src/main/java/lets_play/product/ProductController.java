package lets_play.product;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lets_play.user.User;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    @PermitAll
    public List<ProductDTO.ProductOutput> getAll() {
        return this.productService.getProducts();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ProductDTO.ProductOutput post(@Valid @RequestBody ProductDTO.ProductInput productData,
            @AuthenticationPrincipal User user) {
        return this.productService.createProduct(productData, user.getId());
    }

    @PutMapping("/{id}")
    public ProductDTO.ProductOutput update(@PathVariable("id") String productId,
            @Valid @RequestBody ProductDTO.ProductInput productData,
            @AuthenticationPrincipal User user) {
        boolean isAdmin = user.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
        return this.productService.updateProduct(productId, productData, isAdmin, user);
    }

    @DeleteMapping("/{id}")
    public Map<String, String> delete(@PathVariable("id") String productId, @AuthenticationPrincipal User user) {
        boolean isAdmin = user.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
        return this.productService.deleteProduct(productId, isAdmin, user);
    }

}
