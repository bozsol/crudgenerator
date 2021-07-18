package {PACKAGE}.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class {DOMAIN}Info {

    @Schema(description = "Id of this {VARIABLE}", example = "1")
    private Long id;

    @Schema(description = "Name of this {VARIABLE}", example = "Name")
    private String title;
}