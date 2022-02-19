package org.mattie.osm.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.UUID;
import org.mattie.osm.AutoStartTrigger;
import org.mattie.osm.AutoStartTrigger.Timing;
import org.mattie.osm.ManualTrigger;
import org.mattie.osm.MediaResource;
import org.mattie.osm.PlaylistCue;
import org.mattie.osm.RichTextCue;
import org.mattie.osm.Show;
import org.mattie.osm.TimeTrigger;
import org.mattie.osm.Trigger;

/**
 *
 * @author Matt
 */
public class Main {

    private static int nextCueIndex = 1;

    private static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper(new YAMLFactory());
        objectMapper.findAndRegisterModules(); // Loads JSR 310
        objectMapper.registerSubtypes(new NamedType(PlaylistCue.class));
        objectMapper.registerSubtypes(new NamedType(RichTextCue.class));

        objectMapper.registerSubtypes(new NamedType(ManualTrigger.class));
        objectMapper.registerSubtypes(new NamedType(TimeTrigger.class));
        objectMapper.registerSubtypes(new NamedType(AutoStartTrigger.class));
    }

    private static String nextCueName() {
        return "Q" + (nextCueIndex++);
    }

    public static void main(String[] args) throws IOException {

        // DEMO_01
        Show demo01 = demo01();
        System.out.println("demo01: " + demo01);
        save("demo_01.yaml", demo01);
        Show demo01r = open("demo_01.yaml");
        System.out.println("demo01r: " + demo01r);
    }

    private static void save(String filename, Show show) throws IOException {
        File dir = new File("target/shows");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, filename);
        objectMapper.writeValue(file, show);
    }

    private static Show open(String filename) throws IOException {
        File dir = new File("target/shows");
        File file = new File(dir, filename);
        return objectMapper.readValue(file, Show.class);
    }

    private static Show demo01() {

        PlaylistCue ohPlaylist = playlist("OPEN HOUSE", 0.8, -1, timeTrigger(12, 30));
        ohPlaylist
                .add(mediaResource("Bungle in the Jungle", "bungle_in_jungle.mp3", 0.7, "PT4M36S"))
                .add(mediaResource("The Lion Sleeps Tonight", "bungle_in_jungle.mp3", 0.65, "PT3M7S"));

        PlaylistCue psPlaylist = playlist("PRE-SHOW", 1.0, 2, autoStartTrigger(5, Timing.AFTER_PREVIOUS));
        psPlaylist
                .add(mediaResource("I Saw Mommy Kissing Santa Claus", "06 I Saw Mommy Kissing Santa Claus.mp3", 0.7, "PT1M57S"))
                .add(mediaResource("Rudolph the Red-Nosed Reindeer", "02 Rudolph the Red-Nosed Reindeer.mp3", 0.5, "PT2M10S"));

        RichTextCue richTextCue1 = richTextCue("Moana ACT 1: Page 1",
                "<html><body><b>Daphanie:</b> Givne the trouble I've experienced, a compensation of 10 Quid should be sufficient<p/><b>Terrance:</b> You have got to be kidding me?</body></html>",
                manualTrigger());

        Show show = new Show()
                .setName("2022 Spring Recital")
                .add(ohPlaylist)
                .add(psPlaylist)
                .add(richTextCue1);

        return show;
    }

    private static String id() {
        return UUID.randomUUID().toString();
    }

    private static AutoStartTrigger autoStartTrigger(long delay, Timing timing) {
        return new AutoStartTrigger()
                .setDelay(Duration.ofSeconds(delay))
                .setTiming(timing);
    }

    private static PlaylistCue playlist(String desc, double volume, long crossfade, Trigger trigger) {
        PlaylistCue playlistCue = (PlaylistCue) new PlaylistCue()
                .setVolume(volume)
                .setCrossfade(Duration.ofSeconds(crossfade))
                .setDesc(desc)
                .setId(id())
                .setName(nextCueName())
                .setTrigger(trigger);
        return playlistCue;
    }

    private static RichTextCue richTextCue(String desc, String text, Trigger trigger) {
        RichTextCue richTextCue = (RichTextCue) new RichTextCue()
                .setText(text)
                .setId(id())
                .setName(nextCueName())
                .setTrigger(trigger);
        return richTextCue;
    }

    private static MediaResource mediaResource(String name, String file, double volume, String duration) {
        MediaResource resource = new MediaResource()
                .setName(name)
                .setFile(file)
                .setVolume(volume)
                .setDuration(Duration.parse(duration));
        return resource;
    }

    private static TimeTrigger timeTrigger(int hour, int sec) {
        TimeTrigger trigger = new TimeTrigger()
                .setTime(LocalTime.of(hour, sec));
        return trigger;
    }

    private static ManualTrigger manualTrigger() {
        return new ManualTrigger();
    }
}
