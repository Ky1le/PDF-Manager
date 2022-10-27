package kyle.pdfmanager.boot;

import javafx.stage.FileChooser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanInitializer {

    /**
     * Registers a FileChooser as a bean.
     *
     * @return the registered FileChosser.
     */
    @Bean
    public FileChooser fileChooser() {
        return new FileChooser();
    }
}
