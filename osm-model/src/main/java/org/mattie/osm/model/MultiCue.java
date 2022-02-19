package org.mattie.osm.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 *
 * @author Matthew Tyler
 */
@Data
@ToString(callSuper = true)
@Accessors(chain = true)
public class MultiCue extends Cue {

    private List<Cue> cues = new ArrayList<>();

    public MultiCue add(Cue... cue) {
        cues.addAll(Arrays.asList(cue));
        return this;
    }
}
