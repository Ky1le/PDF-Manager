package kyle.pdfmanager.components.buttons;

import javafx.scene.control.Button;
import kyle.pdfmanager.constants.GlyphFontFamilyConstants;
import kyle.pdfmanager.constants.StyleConstants;
import kyle.pdfmanager.services.PDFMergeService;
import org.apache.commons.lang3.StringUtils;
import org.controlsfx.glyphfont.Glyph;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class MergeButton extends Button {

    private static final String STYLE_CLASS = "merge-button";

    public MergeButton() {
        super(StringUtils.EMPTY, new Glyph(GlyphFontFamilyConstants.FONT_AWESOME, '\uf00D'));
        style();
    }

    private void style() {
        getStyleClass().add(STYLE_CLASS);
        setFocusTraversable(false);
    }

    @Override
    public String getUserAgentStylesheet() {
        return StyleConstants.PREVIEW_MERGE_STYLE_RESOURCE;
    }
}
