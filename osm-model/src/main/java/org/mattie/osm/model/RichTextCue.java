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
public class RichTextCue extends Cue {

    private List<String> pages = new ArrayList<>();

    public RichTextCue add(String... text) {
        pages.addAll(Arrays.asList(text));
        return this;
    }
}
