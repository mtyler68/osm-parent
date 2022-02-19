package org.mattie.osm.model;

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
public class MediaCue extends Cue {

    private MediaResource media;
}
