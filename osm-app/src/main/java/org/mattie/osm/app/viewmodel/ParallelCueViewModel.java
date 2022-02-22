package org.mattie.osm.app.viewmodel;

import javafx.animation.Animation;
import javafx.animation.ParallelTransition;
import org.mattie.osm.model.ParallelCue;

/**
 *
 * @author Matthew Tyler
 */
public class ParallelCueViewModel extends GroupCueViewModel<ParallelCue> {

    @Override
    public String getSimpleType() {
        return "Parallel";
    }

    public void buildAnimation() {
        ParallelTransition animation = new ParallelTransition();
        animation.setDelay(getDelay());

        getChildren().stream()
                .map(vm -> {
                    vm.buildAnimation();
                    return (Animation) vm.getAnimation().get();
                })
                .forEach(ani -> animation.getChildren().add(ani));
        animation.setOnFinished((evt) -> finished());
        animation.currentTimeProperty().addListener((ov, oldVal, newVal) -> setCurrentTime(formatDuration(newVal)));
        setDuration(formatDuration(animation.getTotalDuration()));

        setAnimation(animation);

    }

    @Override
    public Class<ParallelCue> getCueClass() {
        return ParallelCue.class;
    }

    @Override
    protected CueViewModel newInstance() {
        return new ParallelCueViewModel();
    }

}
