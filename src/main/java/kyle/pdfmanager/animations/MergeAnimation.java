package kyle.pdfmanager.animations;

import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.util.Duration;

import java.util.List;

public class MergeAnimation {

    private static final double INITIAL_DURATION = 0.7;

    private final ParallelTransition parallelTransition;
    private final Node target;
    private final SimpleDoubleProperty longestPath;

    public MergeAnimation(final Node target) {
        this.parallelTransition = new ParallelTransition();
        this.target = target;
        this.longestPath = new SimpleDoubleProperty(0);

        applyListeners();
    }

    private void applyListeners() {
        longestPath.addListener((observable, oldValue, newValue) -> this.
                parallelTransition.getChildren().stream().map(Transition.class::cast).forEach(transition -> {
            final double speed = longestPath.getValue() / INITIAL_DURATION;
            transition.setRate(0.5);    //TODO adjust rate
        }));
    }

    /**
     * Add a list of Nodes which should play the animation.
     *
     * @param nodes which should be added.
     */
    @SuppressWarnings("UnusedReturnValue")
    public MergeAnimation addNodes(final List<? extends Node> nodes) {
        nodes.forEach(this::addNode);

        return this;
    }


    /**
     * Adds a node to the animation.
     *
     * @param node which should be added.
     */
    @SuppressWarnings("UnusedReturnValue")
    public MergeAnimation addNode(final Node node) {
        final ScaleTransition scaleTransition = scaleTransitions(node);
        final TranslateTransition translateTransition = translateTransition(node);
        parallelTransition.getChildren().addAll(scaleTransition, translateTransition);

        return this;
    }

    public final void setOnFinished(EventHandler<ActionEvent> var1) {
        parallelTransition.setOnFinished(var1);
    }

    private ScaleTransition scaleTransitions(final Node node) {
        final ScaleTransition scaleUpTransition = new ScaleTransition();
        scaleUpTransition.setNode(node);
        scaleUpTransition.setByX(0.1);
        scaleUpTransition.setByY(0.1);
        scaleUpTransition.setDuration(Duration.seconds(0.2));

        final ScaleTransition scaleDownTransition = new ScaleTransition();
        scaleDownTransition.setNode(node);
        scaleDownTransition.setToX(0);
        scaleDownTransition.setToY(0);
        scaleUpTransition.setDuration(Duration.seconds(0.5));

        scaleUpTransition.setOnFinished(event ->scaleDownTransition.play());
        return scaleUpTransition;
    }

    private TranslateTransition translateTransition(final Node node) {
        Bounds targetBoundsInScene = target.localToScene(target.getBoundsInLocal());
        Bounds nodeBoundsInScene = node.localToScene(node.getBoundsInLocal());
        final double translateX = Math.abs(targetBoundsInScene.getCenterX() - nodeBoundsInScene.getCenterX());
        final double translateY = Math.abs(targetBoundsInScene.getCenterY() - nodeBoundsInScene.getCenterY());
        final double path = Math.max(translateX, translateY);
        if(longestPath.getValue() < path) longestPath.set(path);

        final TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setNode(node);
        translateTransition.setByX(translateX);
        translateTransition.setByY(translateY);
        translateTransition.setDuration(Duration.seconds(INITIAL_DURATION));

        return translateTransition;
    }

    public void play() {
        parallelTransition.play();
    }
}
