package Nilemarmvc.example.Nilemart.model;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @NotBlank(message = "Category name is required.")
    @Size(min = 2, max = 50, message = "Category name must be between 2 and 50 characters.")
    private String name;

    @NotBlank(message = "Category slug is required.")
    @Pattern(
            regexp = "^[a-z0-9]+(?:-[a-z0-9]+)*$",
            message = "Invalid category slug format."
    )
    private String slug;

    private String parentSlug;

    @Min(value = 0, message = "Display order must be zero or greater.")
    private int displayOrder;

    private boolean active = true;


}
