package kyle.pdfmanager.components.buttons;

import kyle.pdfmanager.components.itemlist.ItemInformationPane;
import kyle.pdfmanager.constants.GlyphFontFamilyConstants;
import org.controlsfx.glyphfont.Glyph;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ItemDeleteButton extends ItemButton {

    private ItemInformationPane informationPane;

    public ItemDeleteButton() {
        super(new Glyph(GlyphFontFamilyConstants.FONT_AWESOME, '\uf1f8'));
        clickable();
    }

    public void setInformationPane(final ItemInformationPane informationPane) {
        this.informationPane = informationPane;
    }

    private void clickable() {
        setOnAction(actionEvent -> {
            informationPane.setInformationContent(null);
            informationPane.hide();
        });
    }
}
