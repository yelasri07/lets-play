package lets_play.product;

import java.util.List;
import java.util.Map;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import lets_play.exception.NotFoundException;
import lets_play.user.User;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDTO.ProductOutput> getProducts() {
        return this.productRepository.findProducts();
    }

    public ProductDTO.ProductOutput createProduct(ProductDTO.ProductInput productData, String userId) {
        Product product = Product.builder()
                .name(productData.name())
                .description(productData.description())
                .price(productData.price())
                .userId(userId)
                .build();

        Product savedProduct = this.productRepository.insert(product);

        return ProductDTO.ProductOutput.builder()
                .id(savedProduct.getId())
                .name(savedProduct.getName())
                .description(savedProduct.getDescription())
                .price(savedProduct.getPrice())
                .user_id(savedProduct.getUserId())
                .build();
    }

    public Map<String, String> deleteProduct(String productId, boolean isAdmin, User user) {
        Product product = this.productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Whoops! product not found"));

        if (!isAdmin && !product.getUserId().equals(user.getId())) {
            throw new AccessDeniedException("You can only delete your own products");
        }

        this.productRepository.delete(product);
        return Map.of(
                "id", product.getId(),
                "userId", product.getUserId(),
                "message", "Product deleted successfully");
    }

    public ProductDTO.ProductOutput updateProduct(String productId, ProductDTO.ProductInput productData,
            boolean isAdmin, User user) {
        Product product = this.productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Whoops! product not found"));

        if (!isAdmin && !product.getUserId().equals(user.getId())) {
            throw new AccessDeniedException("You can only update your own products");
        }

        product.setName(productData.name());
        product.setDescription(productData.description());
        product.setPrice(productData.price());

        Product updatedProduct = this.productRepository.save(product);

        return ProductDTO.ProductOutput.builder()
                .id(updatedProduct.getId())
                .name(updatedProduct.getName())
                .description(updatedProduct.getDescription())
                .price(updatedProduct.getPrice())
                .user_id(updatedProduct.getUserId())
                .build();
    }

}
