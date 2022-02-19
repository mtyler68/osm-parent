package org.mattie.osm.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 *
 * @author Matthew Tyler
 */
@Data
@Accessors(chain = true)
public class Show {

    private String name;

    private List<Cue> cues = new ArrayList<>();

    public Show add(Cue... cue) {
        cues.addAll(Arrays.asList(cue));
        return this;
    }
}
