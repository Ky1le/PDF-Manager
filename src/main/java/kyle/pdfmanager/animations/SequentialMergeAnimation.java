package kyle.pdfmanager.animations;

import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.util.Duration;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class SequentialMergeAnimation {

    private final ParallelTransition parallelTransition;
    private final Node target;
    private final List<MergeAnimation> animations;

    private double longestPath;

    public SequentialMergeAnimation(final Node target) {
        this.parallelTransition = new ParallelTransition();
        this.target = target;
        this.animations = new ArrayList<>();
        this.longestPath = 0;
    }

    /**
     * Add a list of Nodes which should play the animation.
     *
     * @param nodes which should be added.
     */
    @SuppressWarnings("UnusedReturnValue")
    public SequentialMergeAnimation addNodes(final List<? extends Node> nodes) {
        nodes.forEach(this::addNode);
        return this;
    }


    /**
     * Adds a node to the animation.
     *
     * @param node which should be added.
     */
    @SuppressWarnings("UnusedReturnValue")
    public SequentialMergeAnimation addNode(final Node node) {
        final MergeAnimation animation = new MergeAnimation(node, target);
        animations.add(animation);
        return this;
    }

    public void setOnFinished(EventHandler<ActionEvent> var1) {
        parallelTransition.setOnFinished(var1);
    }

    public void play() {
        parallelTransition.getChildren().clear();
        animations.forEach(mergeAnimation ->
                parallelTransition.getChildren().addAll(
                        mergeAnimation.getScaleUpTransition(), mergeAnimation.getTranslateTransition()));
        parallelTransition.play();
    }

    @Getter
    private class MergeAnimation {

        private static final double INITIAL_DURATION = 0.7;

        private final ScaleTransition scaleUpTransition;
        private final ScaleTransition scaleDownTransition;
        private final TranslateTransition translateTransition;
        private final double longestPath;

        public MergeAnimation(final Node item, final Node target) {
            final Duration duration = Duration.seconds(INITIAL_DURATION);

            scaleUpTransition = new ScaleTransition();
            scaleUpTransition.setNode(item);
            scaleUpTransition.setByX(0.1);
            scaleUpTransition.setByY(0.1);
            scaleUpTransition.setDuration(duration.multiply(0.25));

            scaleDownTransition = new ScaleTransition();
            scaleDownTransition.setNode(item);
            scaleDownTransition.setToX(0);
            scaleDownTransition.setToY(0);
            scaleDownTransition.setDuration(duration.multiply(0.65));

            scaleUpTransition.setOnFinished(event -> scaleDownTransition.play());

            translateTransition = new TranslateTransition();
            translateTransition.setNode(item);
            final Bounds targetBoundsInScene = target.localToScene(target.getBoundsInLocal());
            final Bounds nodeBoundsInScene = item.localToScene(item.getBoundsInLocal());
            final double translateX = Math.abs(targetBoundsInScene.getCenterX() - nodeBoundsInScene.getCenterX());
            final double translateY = Math.abs(targetBoundsInScene.getCenterY() - nodeBoundsInScene.getCenterY());
            translateTransition.setByX(translateX);
            translateTransition.setByY(translateY);
            translateTransition.setDelay(duration.multiply(0.25));
            translateTransition.setDuration(duration.multiply(0.75));

            longestPath = Math.max(translateX, translateY);
        }

    }
}
