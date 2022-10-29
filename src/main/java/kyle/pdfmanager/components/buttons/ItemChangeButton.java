package kyle.pdfmanager.components.buttons;

import kyle.pdfmanager.constants.GlyphFontFamilyConstants;
import org.controlsfx.glyphfont.Glyph;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ItemChangeButton extends ItemButton {

    public ItemChangeButton() {
        super(new Glyph(GlyphFontFamilyConstants.FONT_AWESOME, '\uf021'));
    }
}
