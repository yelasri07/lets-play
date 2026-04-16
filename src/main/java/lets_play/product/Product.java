package lets_play.product;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Builder;
import lombok.Data;

@Document("product")
@Data
@Builder
public class Product {
    
    @Id
    private String id;
    private String name;
    private String description;
    private Double price;
    @Field(name = "user_id")
    private String userId;

}
