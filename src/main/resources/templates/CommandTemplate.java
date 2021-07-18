package {PACKAGE}.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class {DOMAIN}Command {

    @Schema(description = "Name of this {VARIABLE]", example = "Name")
    @NotBlank(message = "Name must not be blank!")
    private String name;
}