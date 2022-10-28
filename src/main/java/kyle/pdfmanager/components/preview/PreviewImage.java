package kyle.pdfmanager.components.preview;

import com.sun.javafx.css.PseudoClassState;
import javafx.css.PseudoClass;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import kyle.pdfmanager.constants.StyleConstants;
import kyle.pdfmanager.model.PDPreviewImage;
import org.controlsfx.control.InfoOverlay;

//@Component
//@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class PreviewImage extends StackPane {

    private static final String STYLE_CLASS = "preview-image";

    private final ImageView imageView;

    public PreviewImage(final PDPreviewImage image) {
        super();
        imageView = new ImageView(image);
        getChildren().add(imageView);
        getStyleClass().add(STYLE_CLASS);
    }

    @Override
    public String getUserAgentStylesheet() {
        return StyleConstants.PREVIEW_STYLE_RESOURCE;
    }
}
