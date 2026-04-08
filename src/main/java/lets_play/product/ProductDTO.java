package lets_play.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

public class ProductDTO {

    public static record ProductInput(
            @NotBlank(message = "Product name cannot be empty")
            @Size(min = 1, max = 30, message = "Product name must be between 1 and 30 character")
            String name,
            @NotBlank(message = "Product description cannot be empty")
            @Size(min = 5, max = 1500, message = "Product description must be between 5 and 1500 character")
            String description,
            @NotNull(message = "Product price cannot be empty")
            Double price) {

        public ProductInput {
            if (name != null && description != null) {
                name = name.trim();
                description = description.trim();
            }
        }
    }

    @Builder
    public static record ProductOutput(
        String id,
        String name,
        String description,
        Double price,
        String userId
    ) {}

}
