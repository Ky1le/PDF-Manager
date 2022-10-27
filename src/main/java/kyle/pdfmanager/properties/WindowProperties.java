package kyle.pdfmanager.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("spring.application.ui.window")
@Data
public class WindowProperties {

    private String title;

    private int width;

    private int height;
}
