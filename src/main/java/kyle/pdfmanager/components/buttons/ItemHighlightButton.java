package kyle.pdfmanager.components.buttons;

import kyle.pdfmanager.components.itemlist.ItemInformationPane;
import kyle.pdfmanager.components.preview.PreviewGrid;
import kyle.pdfmanager.constants.GlyphFontFamilyConstants;
import org.controlsfx.glyphfont.Glyph;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ItemHighlightButton extends ItemButton {

    private final ApplicationContext applicationContext;
    private ItemInformationPane informationPane;

    public ItemHighlightButton(@NonNull final ApplicationContext applicationContext) {
        super(new Glyph(GlyphFontFamilyConstants.FONT_AWESOME, '\uf06e'));
        this.applicationContext = applicationContext;

        clickable();
    }

    /**
     * Reference to its according InformationPane.
     * Gets called in the constructor right after its initialization.
     *
     * @param informationPane the informationPane where the button belongs to.
     */
    public void setInformationPane(@NonNull final ItemInformationPane informationPane) {
        this.informationPane = informationPane;
    }

    /**
     * Highlights the corresponding images of the PDF in the preview.
     * @see PreviewGrid#highlightPreviewItems(UUID) 
     */
    private void clickable() {
        setOnAction(actionEvent -> {
            final UUID uuid = informationPane.getPdDocumentWrapper().getUuid();
            applicationContext.getBean(PreviewGrid.class).highlightPreviewItems(uuid);
            informationPane.hide();
        });
    }


}
