package org.mattie.osm.model;

import java.time.Duration;
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
public class SpriteDVCue extends Cue {

    private SpriteDVResource resource;

    /**
     * Amount of time the DMX value is emitted before it returns to zero.
     */
    private Duration emitDuration = Duration.ofSeconds(1);

    private int loopCount = 1;

    /**
     * Allows time for the video to finish and the SpriteDV player to return to
     * the ambient video.
     */
    private Duration loopDelay = Duration.ofSeconds(3);
}
