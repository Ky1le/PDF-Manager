package kyle.pdfmanager.boot;

import javafx.stage.FileChooser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanInitializer {

    /**
     * Registers a FileChooser as a bean.
     *
     * @return the registered FileChooser.
     */
    @Bean
    public FileChooser fileChooser() {
        final FileChooser fileChooser = new FileChooser();
        final FileChooser.ExtensionFilter pdfExtensionFilter =
                new FileChooser.ExtensionFilter("PDF(*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(pdfExtensionFilter);
        return fileChooser;
    }
}
