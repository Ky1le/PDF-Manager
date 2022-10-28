package kyle.pdfmanager.components.buttons;

import javafx.scene.control.Button;
import kyle.pdfmanager.constants.StyleConstants;
import org.apache.commons.lang3.StringUtils;
import org.controlsfx.glyphfont.Glyph;
import org.springframework.stereotype.Component;

@Component
public abstract class ItemButton extends Button {

    private static final String STYLE_CLASS = "item-button";

    public ItemButton(final Glyph glyph) {
        super(StringUtils.EMPTY, glyph);
        applyStyle();
    }

    private void applyStyle() {
        getStyleClass().add(STYLE_CLASS);
        setFocusTraversable(false);
    }

    @Override
    public String getUserAgentStylesheet() {
        return StyleConstants.ITEM_STYLE_RESOURCE;
    }
}
