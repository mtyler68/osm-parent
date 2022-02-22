package org.mattie.osm.demo;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import static org.mattie.osm.demo.Cues.cueName;
import static org.mattie.osm.demo.Cues.id;
import static org.mattie.osm.demo.Cues.mediaCue;
import static org.mattie.osm.demo.Cues.mediaPlaylistCue;
import static org.mattie.osm.demo.Cues.newSubIndex;
import static org.mattie.osm.demo.Cues.nextIndex;
import static org.mattie.osm.demo.Cues.noteCue;
import static org.mattie.osm.demo.Cues.popSubIndex;
import static org.mattie.osm.demo.Cues.richTextCue;
import org.mattie.osm.model.Cue;
import org.mattie.osm.model.DmxChannel;
import org.mattie.osm.model.MediaCue;
import org.mattie.osm.model.MediaPlaylistCue;
import org.mattie.osm.model.MediaResource;
import org.mattie.osm.model.NoteCue;
import org.mattie.osm.model.ParallelCue;
import org.mattie.osm.model.RichTextCue;
import org.mattie.osm.model.SceneCue;
import org.mattie.osm.model.SequentialCue;
import org.mattie.osm.model.Show;
import org.mattie.osm.model.SpriteDVCue;
import org.mattie.osm.model.SpriteDVResource;
import org.mattie.osm.model.TriggerType;
import org.mattie.osm.model.Utils;

/**
 *
 * @author Matt
 */
public class Main {

    public static void main(String[] args) throws IOException {

        // DEMO_01
//        generateDemo(demo01(), "demo_01");
//        generateDemo(demo02(), "demo_02");
//        generateDemo(demo03(), "demo_03");
        generateDemo(demo04(), "demo_04");
        generateDemo(oneNote(), "one_note");
        generateDemo(oneNoteWithDelay(), "one_note_with_delay");
        generateDemo(twoNotesManual(), "two_notes_manual");
        generateDemo(twoNotesWithDelayManual(), "two_notes_with_delay_manual");
        generateDemo(twoNotesWithDelayAuto(), "two_notes_with_delay_auto");
        generateDemo(oneSequentialTwoNotes(), "one_sequential_two_notes");
        generateDemo(twoParallelNotes(), "two_parallel_notes");
        generateDemo(oneMediaCue(), "one_media_cue");
        generateDemo(threeSongPlaylistNoCrossfade(), "three_song_playlist_no_crossfade");
        generateDemo(threeSongPlaylistWithCrossfade(), "three_song_playlist_with_crossfade");
        generateDemo(hotKeyMedia(), "hot_key_media");
        generateDemo(twoPageRichText(), "two_page_rich_text");
        generateDemo(oneVideoMediaCue(), "one_video_media_cue");
        generateDemo(springRecital2022(), "2022_spring_recital");
    }

    private static void generateDemo(Show show, String title) throws IOException {
        Cues.reset();
        System.out.println(title + ": " + show);
        save(title + ".yaml", show);
        Show demor = open(title + ".yaml");
        System.out.println(title + "r: " + demor);
    }

    private static void save(String filename, Show show) throws IOException {
        File dir = new File("shows");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, filename);
        Utils.getObjectMapper().writeValue(file, show);
    }

    private static Show open(String filename) throws IOException {
        File dir = new File("shows");
        File file = new File(dir, filename);
        return Utils.getObjectMapper().readValue(file, Show.class);
    }

//    private static Show demo01() {
//
//        PlaylistCue ohPlaylist = playlist("OPEN HOUSE PLAYLIST", 0.8, -1, timeTrigger(12, 30));
//        ohPlaylist
//                .add(mediaResource("Bungle in the Jungle", "bungle_in_jungle.mp3", 0.7, "PT4M36S"))
//                .add(mediaResource("The Lion Sleeps Tonight", "bungle_in_jungle.mp3", 0.65, "PT3M7S"));
//
//        SpriteDVPlaylistCue triviaCue = spriteDVPlaylist("PRE-SHOW TRIVIA", autoStartTrigger("PT0S", Timing.AFTER_PREVIOUS));
//
//        PlaylistCue psPlaylist = playlist("PRE-SHOW", 1.0, 2, autoStartTrigger("PT5S", Timing.AFTER_PREVIOUS));
//        psPlaylist
//                .add(mediaResource("I Saw Mommy Kissing Santa Claus", "06 I Saw Mommy Kissing Santa Claus.mp3", 0.7, "PT1M57S"))
//                .add(mediaResource("Rudolph the Red-Nosed Reindeer", "02 Rudolph the Red-Nosed Reindeer.mp3", 0.5, "PT2M10S"));
//
//        RichTextCue richTextCue1 = richTextCue("Moana ACT 1: Page 1",
//                "<html><body><b>Daphanie:</b> Givne the trouble I've experienced, a compensation of 10 Quid should be sufficient<p/><b>Terrance:</b> You have got to be kidding me?</body></html>",
//                manualTrigger());
//
//        Show show = new Show()
//                .setName("2022 Spring Recital")
//                .add(ohPlaylist)
//                .add(triviaCue)
//                .add(psPlaylist)
//                .add(richTextCue1);
//
//        return show;
//    }
//
//    private static Show demo02() {
//
//        MediaCue media = media("BUMPER MUSIC",
//                "Rudolph the Red-Nosed Reindeer",
//                "02 Rudolph the Red-Nosed Reindeer.mp3",
//                0.5, "PT2M10S",
//                autoStartTrigger("PT20S", Timing.AFTER_PREVIOUS));
//
//        Show show = new Show()
//                .setName("Bumper Cues")
//                .add(media);
//
//        return show;
//    }
    private static MediaResource jingleBellsMediaResource() {
        return new MediaResource()
                .setFile("0020 Jingle Bells.mp3")
                .setName("Jingle Bells")
                .setVolume(0.7)
                .setStopAt(Duration.parse("PT2M20S"));
    }

    private static MediaResource kissingMediaResource() {
        return new MediaResource()
                .setFile("06 I Saw Mommy Kissing Santa Claus.mp3")
                .setName("I Saw Mommy Kissing Santa Claus")
                .setVolume(0.6)
                .setStopAt(Duration.parse("PT1M58S"));
    }

    private static MediaResource rudolphMediaResource() {
        return new MediaResource()
                .setFile("02_Rudolph_the_Red-Nosed_Reindeer.mp3.mp3")
                .setName("Rudolph the Red-Nosed Reindeer")
                .setVolume(0.5)
                .setStopAt(Duration.parse("PT2M11S"));
    }

    private static Show demo03() {
        Show show = new Show()
                .setName("Demo 03");

        SpriteDVCue dv1Cue = (SpriteDVCue) new SpriteDVCue()
                .setResource(new SpriteDVResource()
                        .setVideoNumber(1)
                        .setDmxChannel(new DmxChannel()
                                .setUniverse(1)
                                .setAddress(33)
                                .setType(DmxChannel.Type.MONO_VALUE))
                        .setLength(Duration.parse("PT15S")))
                .setId(id())
                .setName(cueName())
                .setDesc("ACT 1 Pre-Video")
                .setTrigger(TriggerType.AUTO_START);
        show.add(dv1Cue);

        Cues.nextIndex();
        MediaCue act1Cue = Cues.mediaCue("ACT 1: Kinder Combo Wednesday", kissingMediaResource());
        show.add(act1Cue);

        nextIndex();
        SceneCue act1SceneCue = (SceneCue) new SceneCue()
                .setId(id())
                .setName(cueName())
                .setDesc("ACT 1: Boogie Babies Friday Ballet")
                .setTrigger(TriggerType.MANUAL);

        newSubIndex();
        act1SceneCue.add(mediaCue("SONG: Jingle Bells", jingleBellsMediaResource()));
        nextIndex();
        act1SceneCue.add(Cues.richTextCue("Moana ACT 1: Page 1",
                "<html><body><b>Daphanie:</b> Givne the trouble I've experienced, a compensation of 10 Quid should be sufficient<p/><b>Terrance:</b> You have got to be kidding me?</body></html>")
                .setTrigger(TriggerType.MANUAL));

        show.add(act1SceneCue);
        popSubIndex();

        nextIndex();
        Cue circusHornCue = mediaCue("HOT-KEY: Circus Horn", Constants.resourceCircusHorn())
                .setTrigger(TriggerType.HOT_KEY)
                .setTriggerProp("key", "H");
        show.add(circusHornCue);

        nextIndex();
        Cue waterfallCue = mediaCue("HOT-KEY: Waterfall", Constants.resourceWaterfall())
                .setTrigger(TriggerType.HOT_KEY)
                .setTriggerProp("key", "W");
        show.add(waterfallCue);

        // PLAYLIST
        nextIndex();
        MediaPlaylistCue openHousePlaylist = (MediaPlaylistCue) new MediaPlaylistCue()
                .setCrossfade(Duration.ZERO)
                .setVolume(1.0)
                .setId(id())
                .setName(cueName())
                .setDesc("OPEN HOUSE PLAYLIST")
                .setTrigger(TriggerType.AUTO_START)
                .setDelay(Duration.ofSeconds(5));

        MediaResource jingleBellsResource = new MediaResource()
                .setStartAt(Duration.ofSeconds(20))
                .setStopAt(Duration.ofSeconds(50))
                .setFile("0020 Jingle Bells.mp3")
                .setName("Jingle Bells")
                .setVolume(0.6);

        MediaResource kissingResource = new MediaResource()
                .setStartAt(Duration.ofSeconds(40))
                .setStopAt(Duration.ofSeconds(80))
                .setFile("06 I Saw Mommy Kissing Santa Claus.mp3")
                .setName("I Saw Mommy Kissing Santa Claus")
                .setVolume(0.7);

        MediaResource rudolphResource = new MediaResource()
                .setStartAt(Duration.ofSeconds(30))
                .setStopAt(Duration.ofSeconds(50))
                .setFile("02_Rudolph_the_Red-Nosed_Reindeer.mp3")
                .setName("Rudolph the Red-Nosed Reindeer")
                .setVolume(0.5);

        openHousePlaylist.add(jingleBellsResource, kissingResource, rudolphResource);

        return show;
    }

//    private static PlaylistCue playlist(String desc, double volume, long crossfade, Trigger trigger) {
//        PlaylistCue playlistCue = (PlaylistCue) new PlaylistCue()
//                .setVolume(volume)
//                .setCrossfade(Duration.ofSeconds(crossfade))
//                .setDesc(desc)
//                .setId(id())
//                .setName(nextCueName())
//                .setTrigger(trigger);
//        return playlistCue;
//    }
//
//    private static SpriteDVPlaylistCue spriteDVPlaylist(String desc, Trigger trigger) {
//        SpriteDVPlaylistCue playlist = (SpriteDVPlaylistCue) new SpriteDVPlaylistCue()
//                .setDesc(desc)
//                .setId(id())
//                .setName(nextCueName())
//                .setTrigger(trigger);
//        return playlist;
//    }
//    private static MediaCue media(String desc, String mediaName, String file, double volume, String duration, Trigger trigger) {
//        MediaCue cue = (MediaCue) new MediaCue()
//                .setMedia(mediaResource(mediaName, file, volume, duration))
//                .setId(id())
//                .setName(nextCueName())
//                .setDesc(desc)
//                .setTrigger(trigger);
//        return cue;
//    }
    private static Show demo04() {
        Show show = new Show()
                .setName("2022 Spring Recital");

        NoteCue noteA = (NoteCue) new NoteCue()
                .setText("Start show note with short delay")
                .setDelay(Duration.ofSeconds(5))
                .setDesc("Start the show")
                .setId(id())
                .setName(cueName())
                .setTrigger(TriggerType.AUTO_START);
        show.add(noteA);

        nextIndex();
        SequentialCue sequentialCue = (SequentialCue) new SequentialCue()
                .setId(id())
                .setName(cueName())
                .setDesc("SequentialCue")
                .setTrigger(TriggerType.AUTO_START);
        newSubIndex();
        NoteCue sNote1 = (NoteCue) new NoteCue()
                .setText("sNote1")
                .setId(id())
                .setName(cueName())
                .setDesc("sNote1");
        nextIndex();
        NoteCue sNote2 = (NoteCue) new NoteCue()
                .setText("sNote2")
                .setDelay(Duration.ofSeconds(3))
                .setId(id())
                .setName(cueName())
                .setDesc("sNote2");
        nextIndex();
        NoteCue sNote3 = (NoteCue) new NoteCue()
                .setText("sNote3")
                .setDelay(Duration.ofSeconds(3))
                .setId(id())
                .setName(cueName())
                .setDesc("sNote3");
        sequentialCue.add(sNote1, sNote2, sNote3);
        show.add(sequentialCue);

        popSubIndex();

        nextIndex();
        MediaResource songA = new MediaResource()
                .setFile("C:\\Users\\Matt\\Documents\\CAD\\2022 Spring Recital\\cue_media\\01 Stranded In the Jungle.mp3")
                .setStopAt(Duration.ofSeconds(20))
                .setVolume(0.6);
        MediaResource songB = new MediaResource()
                .setFile("C:\\Users\\Matt\\Documents\\CAD\\2022 Spring Recital\\cue_media\\13 Run Through the Jungle.mp3")
                .setStopAt(Duration.ofSeconds(20))
                .setVolume(0.4);
        MediaResource songC = new MediaResource()
                .setFile("C:\\Users\\Matt\\Documents\\CAD\\2022 Spring Recital\\cue_media\\01 Welcome to the Jungle.mp3")
                .setStopAt(Duration.ofSeconds(20))
                .setVolume(0.5);
        MediaPlaylistCue playlistCue = (MediaPlaylistCue) new MediaPlaylistCue()
                .setVolume(1.0)
                .add(songA, songB, songC)
                .setDesc("OPEN HOUSE PLAYLIST")
                .setName(cueName())
                .setId(id())
                .setTrigger(TriggerType.AUTO_START);
        show.add(playlistCue);

        nextIndex();
        MediaResource song01 = new MediaResource()
                .setFile("C:\\Users\\Matt\\Documents\\CAD\\2022 Spring Recital\\cue_media\\bensound-brazilsamba.mp3")
                .setStopAt(Duration.ofSeconds(20))
                .setFadeIn(Duration.ofSeconds(5))
                .setFadeOut(Duration.ofSeconds(5))
                .setVolume(1.0);
        Cue song01Cue = new MediaCue()
                .setMedia(song01)
                .setDesc("Brazil Samba")
                .setName(cueName())
                .setDelay(Duration.ofSeconds(5))
                .setId(id());
        show.add(song01Cue);

        nextIndex();
        MediaResource song02 = new MediaResource()
                .setFile("C:\\Users\\Matt\\Documents\\CAD\\2022 Spring Recital\\cue_media\\06 Bungle In the Jungle.mp3")
                .setStopAt(Duration.ofSeconds(20))
                //.setFadeIn(Duration.ofSeconds(5))
                .setFadeOut(Duration.ofSeconds(5))
                .setVolume(1.0);
        Cue song02Cue = new MediaCue()
                .setMedia(song02)
                .setTrigger(TriggerType.AUTO_START)
                .setDesc("Bungle In the Jungle")
                .setName(cueName())
                //.setDelay(Duration.ofSeconds(5))
                .setId(id());
        show.add(song02Cue);

        nextIndex();
        NoteCue noteCue = (NoteCue) new NoteCue()
                .setText("Manually DIM house lights")
                .setDelay(Duration.ofSeconds(7))
                .setDesc("DIM HOUSE LIGHTS")
                .setId(id())
                .setName(cueName())
                .setTrigger(TriggerType.AUTO_START);
        show.add(noteCue);

        nextIndex();
        ParallelCue parallelCue = (ParallelCue) new ParallelCue()
                .setDesc("MOANA ACT 1")
                .setId(id())
                .setName(cueName())
                .setTrigger(TriggerType.AUTO_START);
        show.add(parallelCue);

        newSubIndex();
        NoteCue moanaAct1NoDelayNoteCue = (NoteCue) new NoteCue()
                .setText("No delay note")
                .setDesc("WITHOUT DELAY")
                .setId(id())
                .setName(cueName())
                .setTrigger(TriggerType.AUTO_START);
        parallelCue.add(moanaAct1NoDelayNoteCue);

        nextIndex();
        MediaResource song03 = new MediaResource()
                .setFile("C:\\Users\\Matt\\Documents\\CAD\\2022 Spring Recital\\cue_media\\01 The Lion Sleeps Tonight (Re-Recor.mp3")
                .setStopAt(Duration.ofSeconds(20))
                //.setFadeIn(Duration.ofSeconds(5))
                .setFadeOut(Duration.ofSeconds(5))
                .setVolume(1.0);
        Cue song03Cue = new MediaCue()
                .setMedia(song03)
                .setTrigger(TriggerType.AUTO_START)
                .setDesc("The Lion Sleeps Tonight")
                .setName(cueName())
                .setDelay(Duration.ofSeconds(5))
                .setId(id());
        parallelCue.add(song03Cue);

        nextIndex();
        NoteCue moanaAct1NoteCue = (NoteCue) new NoteCue()
                .setText("Turn on wireless microphones")
                .setDelay(Duration.ofSeconds(10))
                .setDesc("WIRELESS MICS - ON")
                .setId(id())
                .setName(cueName())
                .setTrigger(TriggerType.AUTO_START);
        parallelCue.add(moanaAct1NoteCue);

        popSubIndex();

        nextIndex();
        MediaResource song04 = new MediaResource()
                .setFile("C:\\Users\\Matt\\Documents\\CAD\\2022 Spring Recital\\cue_media\\01 Welcome to the Jungle.mp3")
                .setStopAt(Duration.ofSeconds(30))
                //.setFadeIn(Duration.ofSeconds(5))
                .setFadeOut(Duration.ofSeconds(5))
                .setVolume(1.0);
        Cue song04Cue = new MediaCue()
                .setMedia(song04)
                .setTrigger(TriggerType.AUTO_START)
                .setDesc("Welcome to the Jungle")
                .setName(cueName())
                //.setDelay(Duration.ofSeconds(5))
                .setId(id());
        show.add(song04Cue);

        return show;
    }

    private static Show oneNote() {
        Show show = new Show()
                .setName("One Note");

        NoteCue noteCue = (NoteCue) new NoteCue()
                .setText("Note cue for 'one note'")
                .setDesc("NOTE")
                .setId(id())
                .setName(cueName())
                .setTrigger(TriggerType.AUTO_START);
        show.add(noteCue);
        return show;
    }

    private static Show oneNoteWithDelay() {
        Show show = new Show()
                .setName("One Note with Delay");

        NoteCue noteCue = (NoteCue) new NoteCue()
                .setText("Note cue for 'one note with delay'")
                .setDelay(Duration.ofSeconds(5))
                .setDesc("DELAYED NOTE")
                .setId(id())
                .setName(cueName())
                .setTrigger(TriggerType.AUTO_START);
        show.add(noteCue);
        return show;
    }

    private static Show twoNotesManual() {
        Show show = new Show()
                .setName("Two Notes Manual");

        NoteCue note1 = (NoteCue) new NoteCue()
                .setText("Note cue for 'note one'")
                .setDesc("NOTE 1")
                .setId(id())
                .setName(cueName())
                .setTrigger(TriggerType.AUTO_START);
        show.add(note1);

        nextIndex();
        NoteCue note2 = (NoteCue) new NoteCue()
                .setText("Note cue for 'note two'")
                .setDesc("NOTE 2")
                .setId(id())
                .setName(cueName())
                .setTrigger(TriggerType.MANUAL);
        show.add(note2);

        return show;
    }

    private static Show twoNotesWithDelayManual() {
        Show show = new Show()
                .setName("Two Notes With Delay Manual");

        NoteCue note1 = (NoteCue) new NoteCue()
                .setText("Note cue for 'note one'")
                .setDelay(Duration.ofSeconds(5))
                .setDesc("NOTE 1")
                .setId(id())
                .setName(cueName())
                .setTrigger(TriggerType.AUTO_START);
        show.add(note1);

        nextIndex();
        NoteCue note2 = (NoteCue) new NoteCue()
                .setText("Note cue for 'note two'")
                .setDelay(Duration.ofSeconds(5))
                .setDesc("NOTE 2")
                .setId(id())
                .setName(cueName())
                .setTrigger(TriggerType.MANUAL);
        show.add(note2);

        return show;
    }

    private static Show twoNotesWithDelayAuto() {
        Show show = new Show()
                .setName("Two Notes With Delay Auto");

        NoteCue note1 = (NoteCue) new NoteCue()
                .setText("Note cue for 'note one'")
                .setDelay(Duration.ofSeconds(5))
                .setDesc("NOTE 1")
                .setId(id())
                .setName(cueName())
                .setTrigger(TriggerType.AUTO_START);
        show.add(note1);

        nextIndex();
        NoteCue note2 = (NoteCue) new NoteCue()
                .setText("Note cue for 'note two'")
                .setDelay(Duration.ofSeconds(5))
                .setDesc("NOTE 2")
                .setId(id())
                .setName(cueName())
                .setTrigger(TriggerType.AUTO_START);
        show.add(note2);

        return show;
    }

    private static Show oneSequentialTwoNotes() {
        Show show = new Show()
                .setName("One Sequential Two Notes");

        SequentialCue sequentialCue = Cues.sequentialCue("SEQUENCE");

        newSubIndex();
        Cue note1 = noteCue("NOTE 1", "Note cue for 'note one'")
                .setDelay(Duration.ofSeconds(5))
                .setTrigger(TriggerType.AUTO_START);
        sequentialCue.add(note1);

        nextIndex();
        Cue note2 = noteCue("NOTE 2", "Note cue for 'note two'")
                .setDelay(Duration.ofSeconds(5));
        sequentialCue.add(note2);

        show.add(sequentialCue);
        return show;
    }

    private static Show twoParallelNotes() {
        Show show = new Show()
                .setName("Two Parallel Notes");

        ParallelCue parallelCue = Cues.parallelCue("PARALLEL");
        show.add(parallelCue);

        Cues.newSubIndex();
        NoteCue note1 = noteCue("NOTE 1", "Note cue for 'note one'");
        parallelCue.add(note1);

        Cues.nextIndex();
        NoteCue note2 = noteCue("NOTE 2", "Note cue for 'note two'");
        parallelCue.add(note2);

        return show;
    }

    private static Show oneMediaCue() {
        Show show = new Show()
                .setName("One Media Cue");

        MediaResource resource = Constants.resourceBrazilSamba()
                .setStopAt(Duration.ofSeconds(20))
                .setFadeIn(Duration.ofSeconds(5))
                .setFadeOut(Duration.ofSeconds(5))
                .setVolume(0.3);
        MediaCue cue = mediaCue("Brazil Samba", resource);
        show.add(cue);

        return show;
    }

    private static Show threeSongPlaylistNoCrossfade() {
        Show show = new Show()
                .setName("Three Song Playlist No Crossfade");

        MediaResource song1 = Constants.resourceStrandedInTheJungle()
                .setStopAt(Duration.ofSeconds(20))
                .setVolume(0.6);
        MediaResource song2 = Constants.resourceRunThroughTheJungle()
                .setStopAt(Duration.ofSeconds(20))
                .setVolume(0.4);
        MediaResource song3 = Constants.resourceWelcomeToTheJungle()
                .setStopAt(Duration.ofSeconds(20))
                .setVolume(0.5);
        MediaPlaylistCue playlistCue = (MediaPlaylistCue) mediaPlaylistCue("PLAYLIST",
                Duration.ZERO, song1, song2, song3)
                .setVolume(1.0)
                .setTrigger(TriggerType.AUTO_START);
        show.add(playlistCue);

        return show;
    }

    private static Show threeSongPlaylistWithCrossfade() {
        Show show = new Show()
                .setName("Three Song Playlist With Crossfade");

        MediaResource song1 = Constants.resourceStrandedInTheJungle()
                .setStopAt(Duration.ofSeconds(20))
                .setVolume(0.6);
        MediaResource song2 = Constants.resourceRunThroughTheJungle()
                .setStopAt(Duration.ofSeconds(20))
                .setVolume(0.4);
        MediaResource song3 = Constants.resourceWelcomeToTheJungle()
                .setStopAt(Duration.ofSeconds(20))
                .setVolume(0.5);
        MediaPlaylistCue playlistCue = (MediaPlaylistCue) mediaPlaylistCue("PLAYLIST",
                Duration.ofSeconds(5), song1, song2, song3)
                .setVolume(1.0)
                .setTrigger(TriggerType.AUTO_START);
        show.add(playlistCue);

        return show;
    }

    private static Show hotKeyMedia() {
        Show show = new Show()
                .setName("Hot Key Media");

        MediaResource resource1 = Constants.resourceCircusHorn().setVolume(0.6);
        Cue mediaCue1 = mediaCue(resource1.getName(), resource1)
                .setTrigger(TriggerType.HOT_KEY)
                .setTriggerProp("hotKey", "H");
        show.add(mediaCue1);

        Cues.nextIndex();
        MediaResource resource2 = Constants.resourceWaterfall().setVolume(0.4);
        Cue mediaCue2 = mediaCue(resource2.getName(), resource2)
                .setTrigger(TriggerType.HOT_KEY)
                .setTriggerProp("hotKey", "W");
        show.add(mediaCue2);

        return show;
    }

    private static Show twoPageRichText() {
        Show show = new Show()
                .setName("Two Page Rich Text");

        RichTextCue richTextCue = richTextCue("Moana Script",
                Constants.MOANA_PG1, Constants.MOANA_PG2);
        show.add(richTextCue);

        return show;
    }

    private static Show oneVideoMediaCue() {
        Show show = new Show()
                .setName("One Video Media Cue");

        MediaResource resource = new MediaResource()
                .setFile("C:\\Users\\Matt\\Documents\\CAD\\2022 Spring Recital\\cue_media\\Holiday 2021 - Contemporary.mp4")
                .setShowVideo(true)
                .setType(MediaResource.MediaType.VIDEO_ONLY)
                .setStopAt(Duration.ofSeconds(20))
                .setFadeIn(Duration.ofSeconds(5))
                .setFadeOut(Duration.ofSeconds(5))
                .setVolume(1.0);
        MediaCue cue = mediaCue("Contemporary 2021", resource);
        show.add(cue);

        return show;
    }

    private static Show springRecital2022() {
        Show show = new Show().setName("2022 Spring Recital");
        Cue cue = null;

        ParallelCue openHouseCue = Cues.parallelCue("OPEN HOUSE");
        show.add(openHouseCue);

        newSubIndex();
        cue = mediaPlaylistCue("OPEN HOUSE MUSIC", Duration.ZERO,
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

        return show;
    }

}
