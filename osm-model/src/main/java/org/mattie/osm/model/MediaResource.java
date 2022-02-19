package org.mattie.osm.model;

import java.time.Duration;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 *
 * @author Matthew Tyler
 */
@Data
@Accessors(chain = true)
public class MediaResource {

    public static enum MediaType {
        AUDIO_ONLY,
        AUDIO_VIDEO,
        VIDEO_ONLY
    };

    private String name;

    private String file;

    private double volume = 1;

    private Duration fadeIn = Duration.ZERO;

    /**
     * Fade out begins this duration from the stopAt duration.
     */
    private Duration fadeOut = Duration.ZERO;

    private Duration startAt = Duration.ZERO;

    private Duration stopAt = null;

    private MediaType type = MediaType.AUDIO_ONLY;

    private boolean showVideo = false;
}
