package kyle.pdfmanager.animations;

import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.util.Duration;
import lombok.Getter;

@Getter
public class PopUpAnimation {

    private static final Duration DURATION_SCALE_UP = Duration.seconds(0.2);
    private static final Duration DURATION_SCALE_DOWN = Duration.seconds(0.1);
    private static final double POPUP_FACTOR = 0.1;

    private final ScaleTransition scaleUpTransition;
    private final ScaleTransition scaleDownTransition;

    public PopUpAnimation(final Node node) {
        scaleUpTransition = new ScaleTransition();
        scaleDownTransition = new ScaleTransition();

        scaleUpTransition.setNode(node);
        scaleUpTransition.setDuration(DURATION_SCALE_UP);
        scaleUpTransition.setFromX(0);
        scaleUpTransition.setFromY(0);
        scaleUpTransition.setToX(node.getScaleX() * (1 + POPUP_FACTOR));
        scaleUpTransition.setToY(node.getScaleY() * (1 + POPUP_FACTOR));
        scaleUpTransition.setOnFinished(event -> scaleDownTransition.play());

        scaleDownTransition.setNode(node);
        scaleDownTransition.setDuration(DURATION_SCALE_DOWN);
        scaleDownTransition.setByX(-POPUP_FACTOR);
        scaleDownTransition.setByY(-POPUP_FACTOR);
    }

    /**
     * Used to start the animation.
     */
    public void play() {
        scaleUpTransition.play();
    }

    /**
     * Get both scaleTransition which were used.
     * @return all Transitions used in the animation.
     * @see SequentialPopUpAnimation
     */
    public ScaleTransition[] getTransitions() {
        return new ScaleTransition[]{scaleDownTransition, scaleUpTransition};
    }

}
