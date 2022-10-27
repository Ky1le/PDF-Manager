package kyle.pdfmanager.components.buttons;

import javafx.scene.control.Button;
import kyle.pdfmanager.constants.StyleConstants;
import org.apache.commons.lang3.StringUtils;
import org.controlsfx.glyphfont.Glyph;

public abstract class WindowButton extends Button {

    private static final String STYLE_CLASS = "window-button";

    public WindowButton(final Glyph glyph) {
        super(StringUtils.EMPTY, glyph);
        style();
    }

    private void style() {
        getStyleClass().add(STYLE_CLASS);
        setFocusTraversable(false);
    }

    @Override
    public String getUserAgentStylesheet() {
        return StyleConstants.WINDOW_STYLE_CLASS;
    }
}
