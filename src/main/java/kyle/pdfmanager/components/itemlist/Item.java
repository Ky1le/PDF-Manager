package kyle.pdfmanager.components.itemlist;

import com.sun.javafx.css.PseudoClassState;
import javafx.css.PseudoClass;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import kyle.pdfmanager.constants.GlyphFontFamilyConstants;
import kyle.pdfmanager.constants.StyleConstants;
import kyle.pdfmanager.model.PDDocumentWrapper;
import org.controlsfx.glyphfont.Glyph;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.zip.Inflater;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Item extends HBox {

    private static final String STYLE_CLASS = "item";
    private static final String EMPTY_ITEM_TEXT = "Click here to pick a pdf";

    private static final PseudoClass PICKED = PseudoClassState.getPseudoClass("picked");

    private final FileChooser fileChooser;

    private PDDocumentWrapper pdDocumentWrapper;

    public Item(@NonNull final FileChooser fileChooser) {
        super();
        this.fileChooser = fileChooser;
        createItems();
        applyProperties();
        style();

        clickable();
    }

    private void applyProperties() {
        setSpacing(10);
    }

    private void createItems() {
        final Label label = new Label();
        label.setText(EMPTY_ITEM_TEXT);
        final Glyph glyph = new Glyph(GlyphFontFamilyConstants.FONT_AWESOME, '\uf00D').sizeFactor(4);

        getChildren().addAll(label, glyph);
    }

    private void style() {
        getStyleClass().add(STYLE_CLASS);
        setFocusTraversable(false); //Will be deleted later on
    }

    /**
     * Opens an FileChooser when the item was clicked.
     */
    private void clickable() {
        setOnMousePressed(event -> {
            if(getPseudoClassStates().contains(PICKED)) {
                final ItemInformationPane itemInformationPane = new ItemInformationPane();
                itemInformationPane.show(this);
                return;
            }

            pseudoClassStateChanged(PICKED, true);
            final File file = fileChooser.showOpenDialog(new Stage());
        });
    }

    @Override
    public String getUserAgentStylesheet() {
        return StyleConstants.ITEM_STYLE_RESOURCE;
    }

}
