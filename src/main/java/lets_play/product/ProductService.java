package lets_play.product;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDTO.ProductOutput> getProducts() {
        return null;
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
                .userId(savedProduct.getUserId())
                .build();
    }

}
