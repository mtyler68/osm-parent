package org.mattie.osm.demo;

import java.time.Duration;
import java.util.UUID;
import org.mattie.osm.model.Cue;
import org.mattie.osm.model.MediaCue;
import org.mattie.osm.model.MediaPlaylistCue;
import org.mattie.osm.model.MediaResource;
import org.mattie.osm.model.NoteCue;
import org.mattie.osm.model.ParallelCue;
import org.mattie.osm.model.RichTextCue;
import org.mattie.osm.model.SequentialCue;

/**
 *
 * @author Matthew Tyler
 */
public final class Cues {

    private static int[] cueIndex = new int[]{1};

    public static void nextIndex() {
        cueIndex[cueIndex.length - 1]++;
    }

    public static void newSubIndex() {
        int[] temp = new int[cueIndex.length + 1];
        System.arraycopy(cueIndex, 0, temp, 0, cueIndex.length);
        cueIndex = temp;
        cueIndex[cueIndex.length - 1] = 1;
    }

    public static void popSubIndex() {
        int[] temp = new int[cueIndex.length - 1];
        System.arraycopy(cueIndex, 0, temp, 0, temp.length);
        cueIndex = temp;
    }

    public static String cueName() {
        String val = "Q" + Integer.toString(cueIndex[0]);
        for (int ndx = 1; ndx < cueIndex.length; ndx++) {
            val += "." + cueIndex[ndx];
        }
        return val;
    }

    public static String id() {
        return UUID.randomUUID().toString();
    }

    public static void reset() {
        cueIndex = new int[]{1};
    }

    public static MediaCue mediaCue(String desc, MediaResource resource) {
        return (MediaCue) new MediaCue()
                .setMedia(resource)
                .setId(id())
                .setDesc(desc)
                .setName(cueName());
    }

    public static NoteCue noteCue(String desc, String text) {
        return (NoteCue) new NoteCue()
                .setText(text)
                .setId(id())
                .setDesc(desc)
                .setName(cueName());
    }

    public static RichTextCue richTextCue(String desc, String... pages) {
        RichTextCue richTextCue = (RichTextCue) new RichTextCue()
                .add(pages)
                .setId(id())
                .setName(cueName())
                .setDesc(desc);
        return richTextCue;
    }

    public static MediaPlaylistCue mediaPlaylistCue(String desc, Duration crossfade, MediaResource... resources) {
        MediaPlaylistCue playlistCue = (MediaPlaylistCue) new MediaPlaylistCue()
                .setCrossfade(crossfade)
                .add(resources)
                .setId(id())
                .setDesc(desc)
                .setName(cueName());
        return playlistCue;
    }

    public static ParallelCue parallelCue(String desc, Cue... cues) {
        return (ParallelCue) new ParallelCue()
                .add(cues)
                .setId(id())
                .setDesc(desc)
                .setName(cueName());
    }

    public static SequentialCue sequentialCue(String desc, Cue... cues) {
        return (SequentialCue) new SequentialCue()
                .add(cues)
                .setId(id())
                .setDesc(desc)
                .setName(cueName());
    }
}
