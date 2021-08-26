package {PACKAGE}.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class {DOMAIN}Command {

    @NotBlank(message = "Name must not be blank!")
    private String name;
}
