package org.mattie.osm.model;

import java.time.Duration;
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
public class MediaPlaylistCue extends Cue {

    private double volume = 1;

    /**
     * Defines the amount of time a crossfade occurs between the end of one song
     * and the beginning of the next song. Songs are faded over the indicated
     * amount of time.
     *
     * When the crossfade is a negative number, then it represents a delay
     * between the completion of one song and the start of another song.
     */
    private Duration crossfade = Duration.ZERO;

    private List<MediaResource> resources = new ArrayList<>();

    public MediaPlaylistCue add(MediaResource... mr) {
        resources.addAll(Arrays.asList(mr));
        return this;
    }

}
