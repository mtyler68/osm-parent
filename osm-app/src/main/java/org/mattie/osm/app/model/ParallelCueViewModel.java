package org.mattie.osm.app.model;

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
                    return vm.getAnimation();
                })
                .forEach(ani -> animation.getChildren().add(ani));
        animation.setOnFinished((evt) -> finished());
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
