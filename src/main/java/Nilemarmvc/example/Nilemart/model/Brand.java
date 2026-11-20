package Nilemarmvc.example.Nilemart.model;
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
public class Brand {

    @NotBlank(message="Brand name is required.")
    @Size(min = 2, max = 50, message = "Brand name must be between 2 and 50 characters.")
    private String name;

    @NotBlank(message = "Brand slug is required.")
    @Pattern(
            regexp = "^[a-z0-9]+(?:-[a-z0-9]+)*$",
            message = "Invalid brand slug format."
    )
    private String slug;

    private boolean active = true;
    public boolean isActive() {
        return active;
    }
}
