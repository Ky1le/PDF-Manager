package kyle.pdfmanager.components.preview;

import com.sun.javafx.css.PseudoClassState;
import javafx.css.PseudoClass;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import kyle.pdfmanager.constants.StyleConstants;
import kyle.pdfmanager.model.PDPreviewImage;
import lombok.Getter;

import java.util.UUID;

@Getter
public class PreviewImage extends StackPane {

    private static final String STYLE_CLASS = "preview-image";

    private static final PseudoClass HIGHLIGHT = PseudoClassState.getPseudoClass("highlight");

    private final UUID pdDocumentWrapperUUID;

    public PreviewImage(final PDPreviewImage image) {
        super();
        this.pdDocumentWrapperUUID = image.getPdDocumentWrapperUUID();
        final ImageView imageView = new ImageView(image);
        getChildren().add(imageView);
        getStyleClass().add(STYLE_CLASS);
        pseudoClassStateChanged(HIGHLIGHT, true);
    }

    @Override
    public String getUserAgentStylesheet() {
        return StyleConstants.PREVIEW_STYLE_RESOURCE;
    }

    /**
     * Changes the highlight state of the image.
     *
     * @param state the state which should be applied.
     */
    public void setHighlight(final boolean state) {
        pseudoClassStateChanged(HIGHLIGHT, state);
    }
}
