package org.mattie.osm.demo;

import java.time.Duration;
import static org.mattie.osm.demo.Cues.mediaPlaylistCue;
import static org.mattie.osm.demo.Cues.newSubIndex;
import static org.mattie.osm.demo.Cues.nextIndex;
import static org.mattie.osm.demo.Cues.noteCue;
import static org.mattie.osm.demo.Cues.parallelCue;
import static org.mattie.osm.demo.Cues.popSubIndex;
import org.mattie.osm.model.Cue;
import org.mattie.osm.model.ParallelCue;
import org.mattie.osm.model.Show;

/**
 *
 * @author Matt
 */
public class SpringRecital22 {

    public static Show springRecital2022() {
        Show show = new Show().setName("2022 Spring Recital");

        show.add(openHouseCue());

        return show;
    }

    private static Cue openHouseCue() {
        ParallelCue openHouseCue = parallelCue("OPEN HOUSE");

        newSubIndex();
        Cue cue = mediaPlaylistCue("OPEN HOUSE MUSIC", Duration.ZERO,
                Constants.resourceBrazilSamba(),
                Constants.resourceRunThroughTheJungle(),
                Constants.resourceStrandedInTheJungle(),
                Constants.resourceWelcomeToTheJungle());
        openHouseCue.add(cue);

        nextIndex();
        cue = noteCue("OPEN HOUSE BILLBOARDS", "Create billboard cue")
                .setDelay(Duration.ofSeconds(10));
        openHouseCue.add(cue);
        popSubIndex();

        return openHouseCue;
    }
}
