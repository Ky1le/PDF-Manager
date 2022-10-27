package kyle.pdfmanager.components.buttons;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import kyle.pdfmanager.constants.GlyphFontFamilyConstants;
import kyle.pdfmanager.constants.StyleConstants;
import kyle.pdfmanager.holder.StageHolder;
import org.apache.commons.lang3.StringUtils;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.Glyph;
import org.controlsfx.glyphfont.INamedCharacter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * A Button which is solely used to exit the Application.
 */
@Component
public class ExitButton extends WindowButton {

    private final StageHolder stageHolder;

    public ExitButton(@NonNull final StageHolder stageHolder) {
        super(new Glyph(GlyphFontFamilyConstants.FONT_AWESOME, '\uf00D'));
        this.stageHolder = stageHolder;
        clickable();
    }

    private void clickable() {
        setOnAction(actionEvent -> stageHolder.getStage().close());
    }
}
