package org.mattie.osm.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * A cue that bundles multiple cues intended to trigger simultaneously.
 *
 * @author Matthew Tyler
 */
@Data
@ToString(callSuper = true)
@Accessors(chain = true)
public class SceneCue extends Cue {

    private List<Cue> cues = new ArrayList<>();

    public SceneCue add(Cue... cue) {
        this.cues.addAll(Arrays.asList(cue));
        return this;
    }
}
