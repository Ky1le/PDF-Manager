package kyle.pdfmanager.holder;

import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@Getter
public class StageHolder {

    private Stage stage;

    public void setStage(final Stage stage) {
        styleStage(stage);
        this.stage = stage;
    }

    /**
     * Used to Style the current Stage
     */
    private void styleStage(final Stage stage) {
        stage.initStyle(StageStyle.UNDECORATED);
    }
}
