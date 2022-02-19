package org.mattie.osm.app.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.mattie.osm.model.GroupCue;

/**
 *
 * @author Matthew Tyler
 */
@ToString(callSuper = true)
public abstract class GroupCueViewModel<C extends GroupCue> extends CueViewModel<C> {

    @Getter
    @Setter
    private List<CueViewModel> children = new ArrayList<>();

    @Override
    public void copyFrom(C cue) {
        super.copyFrom(cue);

        setChildren(cue.getCues().stream()
                .map(CueViewModel::createCueViewModel)
                .collect(Collectors.toList()));
    }

    @Override
    public void resetCue() {
        super.resetCue();
        getChildren().stream()
                .forEach(cvm -> cvm.resetCue());
    }

    // TODO: implement stop, pause, and play for children
}
