package kyle.pdfmanager.components.buttons;


import kyle.pdfmanager.constants.GlyphFontFamilyConstants;
import kyle.pdfmanager.holder.StageHolder;
import org.controlsfx.glyphfont.Glyph;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * A Button which is solely used to minimize the Application.
 */
@Component
public class MinimizeButton extends WindowButton {

    private final StageHolder stageHolder;

    public MinimizeButton(@NonNull final StageHolder stageHolder) {
        super(new Glyph(GlyphFontFamilyConstants.FONT_AWESOME, '\uf2d1'));
        this.stageHolder = stageHolder;

        minimizable();
    }

    private void minimizable() {
        setOnAction(actionEvent -> stageHolder.getStage().setIconified(true));
    }
}
