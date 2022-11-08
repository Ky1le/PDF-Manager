package kyle.pdfmanager.components.itemlist;

import com.sun.javafx.css.PseudoClassState;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.PseudoClass;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import kyle.pdfmanager.constants.GlyphFontFamilyConstants;
import kyle.pdfmanager.constants.StyleConstants;
import kyle.pdfmanager.models.PDDocumentWrapper;
import kyle.pdfmanager.properties.ColorPalette;
import kyle.pdfmanager.services.PDFLoaderService;
import org.controlsfx.glyphfont.Glyph;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@EnableConfigurationProperties(ColorPalette.class)
public class Item extends HBox {

    private static final String STYLE_CLASS = "item";
    private static final String EMPTY_ITEM_TEXT = "Click here to pick a pdf";

    private final PDFLoaderService pdfLoaderService;
    private final ColorPalette colorPalette;

    private final Label label;
    private final Glyph glyph;

    /**
     * State which indicates if the Item has a PDF applied.
     * @see #clickable
     * @see StyleConstants#ITEM_STYLE_RESOURCE
     */
    private static final PseudoClass PICKED = PseudoClassState.getPseudoClass("picked");

    /**
     * The PDF which is applied to the item slot.
     */
    private final SimpleObjectProperty<PDDocumentWrapper> pdDocumentWrapper;
    /**
     * Pane which provides the functionalities for the PDF.
     */
    private final ItemInformationPane itemInformationPane;
    /**
     * Property which can be listened to to react on changes on the page numbers.
     * @see ItemInformationPane
     * @see kyle.pdfmanager.components.preview.PreviewGrid
     */
    private final SimpleBooleanProperty hasPreviewChangesProperty;

    public Item(@NonNull final PDFLoaderService pdfLoaderService,
                @NonNull final ItemInformationPane itemInformationPane,
                @NonNull final ColorPalette colorPalette) {
        super();
        this.pdfLoaderService = pdfLoaderService;
        this.colorPalette = colorPalette;
        this.pdDocumentWrapper = new SimpleObjectProperty<>();
        this.label = new Label();
        this.glyph = new Glyph(GlyphFontFamilyConstants.FONT_AWESOME, '\uf1c1').sizeFactor(4);
        this.itemInformationPane = itemInformationPane;
        this.itemInformationPane.setItem(this);
        this.hasPreviewChangesProperty = new SimpleBooleanProperty(false);
        createItems();
        applyProperties();
        applyListener();
        style();

        clickable();
    }

    public void setPdDocumentWrapper(@Nullable final PDDocumentWrapper pdDocumentWrapper) {
        if(pdDocumentWrapper == null) pseudoClassStateChanged(PICKED, false);
        this.pdDocumentWrapper.setValue(pdDocumentWrapper);
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
        label.setMaxWidth(Double.MAX_VALUE);
        getChildren().addAll(label, glyph);
        setHgrow(label, Priority.ALWAYS);
        setAlignment(Pos.CENTER_RIGHT);

//        glyph.setTranslateX(10);
//        glyph.setTranslateY(-20);
    }

    private void style() {
        glyph.setColor(Color.RED);
        glyph.sizeFactor(2);
        getStyleClass().add(STYLE_CLASS);
        setFocusTraversable(false);
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
    public SimpleBooleanProperty getHasPreviewChangesProperty() {
           return hasPreviewChangesProperty;
    }

    @NonNull
    public SimpleObjectProperty<PDDocumentWrapper> getPDDocumentWrapperProperty() {
        return pdDocumentWrapper;
    }

}
