package kyle.pdfmanager.animations;

import javafx.animation.SequentialTransition;

public class SequentialPopUpAnimation {

    private final SequentialTransition sequentialTransition;

    public SequentialPopUpAnimation() {
        this.sequentialTransition = new SequentialTransition();
    }

    /**
     * Adds a new PopUpAnimation to the sequence.
     * Because only the PopUpAnimation out of the two Transitions were used, the animations have a little overlap
     * to each other.
     * @param pop the animation who should be added to the sequence.
     */
    public void add(final PopUpAnimation pop) {
        sequentialTransition.getChildren().addAll(pop.getScaleUpTransition());
    }

    public void play() {
        sequentialTransition.play();
    }
}
