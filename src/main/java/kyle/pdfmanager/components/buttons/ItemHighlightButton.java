package kyle.pdfmanager.components.buttons;

import kyle.pdfmanager.constants.GlyphFontFamilyConstants;
import org.controlsfx.glyphfont.Glyph;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ItemHighlightButton extends ItemButton {

    public ItemHighlightButton() {
        super(new Glyph(GlyphFontFamilyConstants.FONT_AWESOME, '\ue1af'));
    }
}
