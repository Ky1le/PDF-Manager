package kyle.pdfmanager.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("spring.application.ui.colors")
public class ColorPalette {

    private String primary;
    private String primaryLight;
    private String primaryDark;

    private String secondary;
    private String secondaryLight;
    private String secondaryDark;

    private String fontOnPrimary;
    private String fontOnSecondary;
}
