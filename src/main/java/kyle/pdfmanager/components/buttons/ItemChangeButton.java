package kyle.pdfmanager.components.buttons;

import kyle.pdfmanager.components.itemlist.ItemInformationPane;
import kyle.pdfmanager.constants.GlyphFontFamilyConstants;
import kyle.pdfmanager.models.PDDocumentWrapper;
import kyle.pdfmanager.services.PDFLoaderService;
import org.controlsfx.glyphfont.Glyph;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ItemChangeButton extends ItemButton {

    private final PDFLoaderService pdfLoaderService;
    private ItemInformationPane itemInformationPane;

    public ItemChangeButton(@NonNull final PDFLoaderService pdfLoaderService) {
        super(new Glyph(GlyphFontFamilyConstants.FONT_AWESOME, '\uf021'));
        this.pdfLoaderService = pdfLoaderService;
        clickable();
    }

    public void setItemInformationPane(final ItemInformationPane itemInformationPane) {
        this.itemInformationPane = itemInformationPane;
    }

    private void clickable() {
        setOnAction(actionEvent -> {
            try {
                final PDDocumentWrapper wrapper = pdfLoaderService.load();
                if(wrapper != null) itemInformationPane.setInformationContent(wrapper);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
