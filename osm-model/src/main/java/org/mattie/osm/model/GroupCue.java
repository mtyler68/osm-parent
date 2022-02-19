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
@Accessors(chain = true)
@ToString(callSuper = true)
public class GroupCue extends Cue {

    private List<Cue> cues = new ArrayList<>();

    public GroupCue add(Cue... cue) {
        cues.addAll(Arrays.asList(cue));
        return this;
    }
}
