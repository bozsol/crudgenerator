package hu.progmasters.crudgenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Template {
    String templateName;
    String resultPath;
    String resultFileName;
}
