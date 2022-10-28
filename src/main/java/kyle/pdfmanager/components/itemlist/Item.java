package kyle.pdfmanager.components.itemlist;

import com.sun.javafx.css.PseudoClassState;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.PseudoClass;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import kyle.pdfmanager.constants.GlyphFontFamilyConstants;
import kyle.pdfmanager.constants.StyleConstants;
import kyle.pdfmanager.models.PDDocumentWrapper;
import kyle.pdfmanager.services.PDFLoaderService;
import org.controlsfx.glyphfont.Glyph;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Item extends HBox {

    private static final String STYLE_CLASS = "item";
    private static final String EMPTY_ITEM_TEXT = "Click here to pick a pdf";

    private static final PseudoClass PICKED = PseudoClassState.getPseudoClass("picked");
    private final PDFLoaderService pdfLoaderService;

    private final Label label;
    private final Glyph glyph;
    private final SimpleObjectProperty<PDDocumentWrapper> pdDocumentWrapper;
    private final ItemInformationPane itemInformationPane;

    public Item(@NonNull final PDFLoaderService pdfLoaderService,
                @NonNull final ItemInformationPane itemInformationPane) {
        super();
        this.pdfLoaderService = pdfLoaderService;
        this.pdDocumentWrapper = new SimpleObjectProperty<>();
        this.label = new Label();
        this.glyph = new Glyph(GlyphFontFamilyConstants.FONT_AWESOME, '\uf1c1').sizeFactor(4);
        this.itemInformationPane = itemInformationPane;
        createItems();
        applyProperties();
        applyListener();
        style();

        clickable();
    }

    private void applyProperties() {
        setSpacing(10);
    }

    /**
     * Apply all listeners for the item
     */
    private void applyListener() {
        pdDocumentWrapper.addListener((observable, oldValue, newValue) -> {
            if (newValue != null) label.setText(newValue.getFileName());
            else label.setText(EMPTY_ITEM_TEXT);
        });
    }

    /**
     * Create the Components for the item.
     * An exception is label, as it needs to be instantiated in the constructor to remain final.
     */
    private void createItems() {
        label.setText(EMPTY_ITEM_TEXT);
        getChildren().addAll(label, glyph);
    }

    private void style() {
        glyph.color(Color.RED);
        glyph.sizeFactor(2);    //needs to be removed
        getStyleClass().add(STYLE_CLASS);
        setFocusTraversable(false); //Will be deleted later on
    }

    /**
     * Opens an FileChooser when no Pdf was selected for this item.
     * Otherwise opens an informationPanel which provides data and new functionality for the item.
     */
    private void clickable() {
        setOnMousePressed(event -> {
            if(getPseudoClassStates().contains(PICKED)) {
                itemInformationPane.show(this);
                return;
            }

            try {
                final PDDocumentWrapper wrapper = pdfLoaderService.load();
                if (wrapper != null) {
                    pdDocumentWrapper.setValue(wrapper);
                    pseudoClassStateChanged(PICKED, true);
                    itemInformationPane.setInformationContent(wrapper);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public String getUserAgentStylesheet() {
        return StyleConstants.ITEM_STYLE_RESOURCE;
    }

    @Nullable
    public PDDocumentWrapper getPDDocumentWrapper() {
        return pdDocumentWrapper.getValue();
    }

    @NonNull
    public SimpleObjectProperty<PDDocumentWrapper> getPDDocumentWrapperProperty() {
        return pdDocumentWrapper;
    }

}
