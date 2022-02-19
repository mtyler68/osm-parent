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
public class AudioClipCue extends Cue {

    private MediaResource audio;
}
