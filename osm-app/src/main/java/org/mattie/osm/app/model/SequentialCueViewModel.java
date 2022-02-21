package org.mattie.osm.app.model;

import java.util.Arrays;
import javafx.animation.Animation;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.util.Duration;
import org.mattie.osm.model.SequentialCue;

/**
 *
 * @author Matthew Tyler
 */
public class SequentialCueViewModel extends GroupCueViewModel<SequentialCue> {

    public void buildAnimation() {
        SequentialTransition animation = new SequentialTransition();
        animation.setDelay(getDelay());

        getChildren().stream()
                .map(vm -> {
                    vm.buildAnimation();
                    Animation ani = (Animation) vm.getAnimation().get();
                    if (vm.getDelay().greaterThan(Duration.ZERO)) {
                        ani.setDelay(Duration.ZERO);
                        return Arrays.asList(new PauseTransition(vm.getDelay()), ani);
                    }
                    return Arrays.asList(ani);
                })
                .forEach(ani -> animation.getChildren().addAll(ani));
        animation.setOnFinished((evt) -> finished());

        setAnimation(animation);
    }

    @Override
    public String getSimpleType() {
        return "Sequential";
    }

    @Override
    public Class<SequentialCue> getCueClass() {
        return SequentialCue.class;
    }

    @Override
    protected CueViewModel newInstance() {
        return new SequentialCueViewModel();
    }
}
