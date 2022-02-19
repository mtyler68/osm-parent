package org.mattie.osm.demo;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.UUID;
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
import org.mattie.osm.model.Utils;

/**
 *
 * @author Matt
 */
public class Main {

    private static int[] cueIndex = new int[]{1};

    private static void nextIndex() {
        cueIndex[cueIndex.length - 1]++;
    }

    private static void newSubIndex() {
        int[] temp = new int[cueIndex.length + 1];
        System.arraycopy(cueIndex, 0, temp, 0, cueIndex.length);
        cueIndex = temp;
        cueIndex[cueIndex.length - 1] = 1;
    }

    private static void popSubIndex() {
        int[] temp = new int[cueIndex.length - 1];
        System.arraycopy(cueIndex, 0, temp, 0, temp.length);
        cueIndex = temp;
    }

    private static String cueName() {
        String val = "Q" + Integer.toString(cueIndex[0]);
        for (int ndx = 1; ndx < cueIndex.length; ndx++) {
            val += "." + cueIndex[ndx];
        }
        return val;
    }

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
    }

    private static void generateDemo(Show show, String title) throws IOException {
        reset();
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

    private static MediaResource circusHornMediaResource() {
        return new MediaResource()
                .setFile("mixkit-clown-horn-at-circus-715.wav")
                .setName("Circus Horn")
                .setVolume(0.6)
                .setStopAt(Duration.parse("PT2.9S"));
    }

    private static MediaResource waterfallMediaResource() {
        return new MediaResource()
                .setFile("mixkit-flowing-waterfall-on-forest-2516.wav")
                .setName("Waterfall")
                .setVolume(0.4)
                .setStopAt(Duration.parse("PT8.9S"));
    }

    private static MediaResource resourceWelcomeToTheJungle() {
        return new MediaResource()
                .setFile("C:\\Users\\Matt\\Documents\\CAD\\2022 Spring Recital\\cue_media\\01 Welcome to the Jungle.mp3")
                .setName("Welcome to the Jungle")
                .setStopAt(Duration.parse("PT4M34S"));
    }

    private static MediaResource resourceBrazilSamba() {
        return new MediaResource()
                .setFile("C:\\Users\\Matt\\Documents\\CAD\\2022 Spring Recital\\cue_media\\bensound-brazilsamba.mp3")
                .setName("Brazil Samba")
                .setStopAt(Duration.parse("PT4M1S"));
    }

    private static MediaCue mediaCue(String desc, MediaResource resource) {
        return (MediaCue) new MediaCue()
                .setMedia(resource)
                .setId(id())
                .setDesc(desc)
                .setName(cueName());
    }

    private static RichTextCue richTextCue(String desc, String text, Cue.TriggerType triggerType) {
        RichTextCue richTextCue = (RichTextCue) new RichTextCue()
                .add(text)
                .setId(id())
                .setName(cueName())
                .setDesc(desc)
                .setTrigger(triggerType);
        return richTextCue;
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
                .setTrigger(Cue.TriggerType.AUTO_START)
                .setNextCueAction(Cue.NextCueAction.STOP);
        show.add(dv1Cue);

        nextIndex();
        MediaCue act1Cue = mediaCue("ACT 1: Kinder Combo Wednesday", kissingMediaResource());
        show.add(act1Cue);

        nextIndex();
        SceneCue act1SceneCue = (SceneCue) new SceneCue()
                .setId(id())
                .setName(cueName())
                .setDesc("ACT 1: Boogie Babies Friday Ballet")
                .setTrigger(Cue.TriggerType.MANUAL);

        newSubIndex();
        act1SceneCue.add(mediaCue("SONG: Jingle Bells", jingleBellsMediaResource()));
        nextIndex();
        act1SceneCue.add(richTextCue("Moana ACT 1: Page 1", "<html><body><b>Daphanie:</b> Givne the trouble I've experienced, a compensation of 10 Quid should be sufficient<p/><b>Terrance:</b> You have got to be kidding me?</body></html>", Cue.TriggerType.MANUAL));

        show.add(act1SceneCue);
        popSubIndex();

        nextIndex();
        Cue circusHornCue = mediaCue("HOT-KEY: Circus Horn", circusHornMediaResource())
                .setTrigger(Cue.TriggerType.HOT_KEY)
                .setTriggerProp("key", "H");
        show.add(circusHornCue);

        nextIndex();
        Cue waterfallCue = mediaCue("HOT-KEY: Waterfall", waterfallMediaResource())
                .setTrigger(Cue.TriggerType.HOT_KEY)
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
                .setTrigger(Cue.TriggerType.AUTO_START)
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

    private static String id() {
        return UUID.randomUUID().toString();
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
    private static void reset() {
        cueIndex = new int[]{1};
    }

    private static Show demo04() {
        Show show = new Show()
                .setName("2022 Spring Recital");

        NoteCue noteA = (NoteCue) new NoteCue()
                .setText("Start show note with short delay")
                .setDelay(Duration.ofSeconds(5))
                .setDesc("Start the show")
                .setId(id())
                .setName(cueName())
                .setTrigger(Cue.TriggerType.AUTO_START);
        show.add(noteA);

        nextIndex();
        SequentialCue sequentialCue = (SequentialCue) new SequentialCue()
                .setId(id())
                .setName(cueName())
                .setDesc("SequentialCue")
                .setTrigger(Cue.TriggerType.AUTO_START);
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
                .setTrigger(Cue.TriggerType.AUTO_START);
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
                .setTrigger(Cue.TriggerType.AUTO_START)
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
                .setTrigger(Cue.TriggerType.AUTO_START);
        show.add(noteCue);

        nextIndex();
        ParallelCue parallelCue = (ParallelCue) new ParallelCue()
                .setDesc("MOANA ACT 1")
                .setId(id())
                .setName(cueName())
                .setTrigger(Cue.TriggerType.AUTO_START);
        show.add(parallelCue);

        newSubIndex();
        NoteCue moanaAct1NoDelayNoteCue = (NoteCue) new NoteCue()
                .setText("No delay note")
                .setDesc("WITHOUT DELAY")
                .setId(id())
                .setName(cueName())
                .setTrigger(Cue.TriggerType.AUTO_START);
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
                .setTrigger(Cue.TriggerType.AUTO_START)
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
                .setTrigger(Cue.TriggerType.AUTO_START);
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
                .setTrigger(Cue.TriggerType.AUTO_START)
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
                .setTrigger(Cue.TriggerType.AUTO_START);
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
                .setTrigger(Cue.TriggerType.AUTO_START);
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
                .setTrigger(Cue.TriggerType.AUTO_START);
        show.add(note1);

        nextIndex();
        NoteCue note2 = (NoteCue) new NoteCue()
                .setText("Note cue for 'note two'")
                .setDesc("NOTE 2")
                .setId(id())
                .setName(cueName())
                .setTrigger(Cue.TriggerType.MANUAL);
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
                .setTrigger(Cue.TriggerType.AUTO_START);
        show.add(note1);

        nextIndex();
        NoteCue note2 = (NoteCue) new NoteCue()
                .setText("Note cue for 'note two'")
                .setDelay(Duration.ofSeconds(5))
                .setDesc("NOTE 2")
                .setId(id())
                .setName(cueName())
                .setTrigger(Cue.TriggerType.MANUAL);
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
                .setTrigger(Cue.TriggerType.AUTO_START);
        show.add(note1);

        nextIndex();
        NoteCue note2 = (NoteCue) new NoteCue()
                .setText("Note cue for 'note two'")
                .setDelay(Duration.ofSeconds(5))
                .setDesc("NOTE 2")
                .setId(id())
                .setName(cueName())
                .setTrigger(Cue.TriggerType.AUTO_START);
        show.add(note2);

        return show;
    }

    private static Show oneSequentialTwoNotes() {
        Show show = new Show()
                .setName("One Sequential Two Notes");

        SequentialCue sequentialCue = (SequentialCue) new SequentialCue()
                .setDesc("SEQUENCE")
                .setId(id())
                .setName(cueName());

        newSubIndex();
        NoteCue note1 = (NoteCue) new NoteCue()
                .setText("Note cue for 'note one'")
                .setDelay(Duration.ofSeconds(5))
                .setDesc("NOTE 1")
                .setId(id())
                .setName(cueName())
                .setTrigger(Cue.TriggerType.AUTO_START);
        sequentialCue.add(note1);

        nextIndex();
        NoteCue note2 = (NoteCue) new NoteCue()
                .setText("Note cue for 'note two'")
                .setDelay(Duration.ofSeconds(5))
                .setDesc("NOTE 2")
                .setId(id())
                .setName(cueName())
                .setTrigger(Cue.TriggerType.MANUAL);
        sequentialCue.add(note2);

        show.add(sequentialCue);
        return show;
    }

    private static Show twoParallelNotes() {
        Show show = new Show()
                .setName("Two Parallel Notes");

        ParallelCue parallelCue = (ParallelCue) new ParallelCue()
                .setDesc("PARALLEL")
                .setId(id())
                .setName(cueName());
        show.add(parallelCue);

        newSubIndex();
        NoteCue note1 = (NoteCue) new NoteCue()
                .setText("Note cue for 'note one'")
                .setDesc("NOTE 1")
                .setId(id())
                .setName(cueName());
        parallelCue.add(note1);

        nextIndex();
        NoteCue note2 = (NoteCue) new NoteCue()
                .setText("Note cue for 'note two'")
                .setDesc("NOTE 2")
                .setId(id())
                .setName(cueName());
        parallelCue.add(note2);

        return show;
    }

    private static Show oneMediaCue() {
        Show show = new Show()
                .setName("One Media Cue");

        MediaResource resource = resourceBrazilSamba()
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

        MediaResource song1 = new MediaResource()
                .setFile("C:\\Users\\Matt\\Documents\\CAD\\2022 Spring Recital\\cue_media\\01 Stranded In the Jungle.mp3")
                .setStopAt(Duration.ofSeconds(20))
                .setVolume(0.6);
        MediaResource song2 = new MediaResource()
                .setFile("C:\\Users\\Matt\\Documents\\CAD\\2022 Spring Recital\\cue_media\\13 Run Through the Jungle.mp3")
                .setStopAt(Duration.ofSeconds(20))
                .setVolume(0.4);
        MediaResource song3 = new MediaResource()
                .setFile("C:\\Users\\Matt\\Documents\\CAD\\2022 Spring Recital\\cue_media\\01 Welcome to the Jungle.mp3")
                .setStopAt(Duration.ofSeconds(20))
                .setVolume(0.5);
        MediaPlaylistCue playlistCue = (MediaPlaylistCue) new MediaPlaylistCue()
                .setVolume(1.0)
                .add(song1, song2, song3)
                .setDesc("PLAYLIST")
                .setName(cueName())
                .setId(id())
                .setTrigger(Cue.TriggerType.AUTO_START);
        show.add(playlistCue);

        return show;
    }

    private static Show threeSongPlaylistWithCrossfade() {
        Show show = new Show()
                .setName("Three Song Playlist With Crossfade");

        MediaResource song1 = new MediaResource()
                .setFile("C:\\Users\\Matt\\Documents\\CAD\\2022 Spring Recital\\cue_media\\01 Stranded In the Jungle.mp3")
                .setStopAt(Duration.ofSeconds(20))
                .setVolume(0.6);
        MediaResource song2 = new MediaResource()
                .setFile("C:\\Users\\Matt\\Documents\\CAD\\2022 Spring Recital\\cue_media\\13 Run Through the Jungle.mp3")
                .setStopAt(Duration.ofSeconds(20))
                .setVolume(0.4);
        MediaResource song3 = new MediaResource()
                .setFile("C:\\Users\\Matt\\Documents\\CAD\\2022 Spring Recital\\cue_media\\01 Welcome to the Jungle.mp3")
                .setStopAt(Duration.ofSeconds(20))
                .setVolume(0.5);
        MediaPlaylistCue playlistCue = (MediaPlaylistCue) new MediaPlaylistCue()
                .setVolume(1.0)
                .setCrossfade(Duration.ofSeconds(5))
                .add(song3, song1, song2)
                .setDesc("PLAYLIST")
                .setName(cueName())
                .setId(id())
                .setTrigger(Cue.TriggerType.AUTO_START);
        show.add(playlistCue);

        return show;
    }

    private static Show hotKeyMedia() {
        Show show = new Show()
                .setName("Hot Key Media");

        MediaResource resource1 = new MediaResource()
                .setFile("C:\\Users\\Matt\\Documents\\CAD\\2022 Spring Recital\\cue_media\\mixkit-clown-horn-at-circus-715.wav")
                .setName("Circus Horn")
                .setVolume(0.6)
                .setStopAt(Duration.parse("PT2.9S"));
        MediaCue mediaCue1 = (MediaCue) new MediaCue()
                .setMedia(resource1)
                .setDesc(resource1.getName())
                .setId(id())
                .setName(cueName())
                .setTrigger(Cue.TriggerType.HOT_KEY)
                .setTriggerProp("hotKey", "H");
        show.add(mediaCue1);

        nextIndex();
        MediaResource resource2 = new MediaResource()
                .setFile("C:\\Users\\Matt\\Documents\\CAD\\2022 Spring Recital\\cue_media\\mixkit-flowing-waterfall-on-forest-2516.wav")
                .setName("Waterfall")
                .setVolume(0.4)
                .setStopAt(Duration.parse("PT8.9S"));
        MediaCue mediaCue2 = (MediaCue) new MediaCue()
                .setMedia(resource2)
                .setDesc(resource2.getName())
                .setId(id())
                .setName(cueName())
                .setTrigger(Cue.TriggerType.HOT_KEY)
                .setTriggerProp("hotKey", "W");
        show.add(mediaCue2);

        return show;
    }

    private static Show twoPageRichText() {
        Show show = new Show()
                .setName("Two Page Rich Text");

        RichTextCue richText = (RichTextCue) new RichTextCue()
                .setDesc("RICH TEXT")
                .setId(id())
                .setName(cueName());
        richText.add("<!DOCTYPE html>\n"
                + "<html>\n"
                + "  <head>\n"
                + "    <title>Moana pg. 1</title>\n"
                + "    <meta charset=\"UTF-8\">\n"
                + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "  </head>\n"
                + "  <body>\n"
                + "    <h3 style=\"text-align: center;\">Scene 1-Village</h3>\n"
                + "    <p><span style=\"background-color: #ccffcc;\"><strong>Gramma Tala:</strong></span> In the beginning, there was only ocean. Until<br />&ldquo;Mother Island&rdquo; emerged.</p>\n"
                + "    <p><strong><span style=\"background-color: #ccffcc;\">All:</span></strong> TeFiti</p>\n"
                + "    <p><strong><span style=\"background-color: #ccffcc;\">Gramma Tala:</span></strong> Her heart had the greatest power ever known.</p>\n"
                + "    <p><strong><span style=\"background-color: #ccffcc;\">Villager 1:</span></strong> And TeFiti shared it with the world.</p>\n"
                + "    <p><strong><span style=\"background-color: #ccffcc;\">Villager 2:</span></strong> But in time, some began to seek TeFiti&rsquo;s heart.</p>\n"
                + "    <p><strong><span style=\"background-color: #ccffcc;\">Gramma Tala:</span></strong> The most daring was Maui- the Demi god of wind<br />and sea. He took the heart of TeFiti, and without her heart she<br />began to crumble.</p>\n"
                + "    <p><strong><span style=\"background-color: #ccffcc;\">Villager 3:</span></strong> Maui was confronted by TeKa- the demon of earth and<br />fire.</p>\n"
                + "    <p style=\"text-align: center;\"><strong><span style=\"background-color: #ff99cc;\">&nbsp;HOTKEY( 1 )&nbsp;</span></strong></p>\n"
                + "    <p><strong><span style=\"background-color: #ccffcc;\">Kids:</span></strong> Nooooooo! Fire monster! Fire monster noooo!</p>\n"
                + "    <p><strong><span style=\"background-color: #ccffcc;\">Villager 4:</span></strong> Maui was struck down, never to be seen again.</p>\n"
                + "  </body>\n"
                + "</html>");
        richText.add("<!DOCTYPE html>\n"
                + "<html>\n"
                + "  <head>\n"
                + "    <title>Moana pg 2</title>\n"
                + "    <meta charset=\"UTF-8\">\n"
                + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "  </head>\n"
                + "  <body>\n"
                + "    <p><span style=\"text-decoration: underline;\"><span style=\"background-color: #ccffcc;\"><strong>Gramma Tala:</strong></span></span> And TeFiti&rsquo;s heart was lost to the sea. One day, her<br />heart will be found by someone who is brave and would journey<br />beyond the reef, across the great ocean to find Maui and restore<br />the heart of TeFiti.</p>\n"
                + "    <p><br /><span style=\"text-decoration: underline;\"><span style=\"background-color: #ccffcc;\"><strong>All:</strong></span></span> And save us all.</p>\n"
                + "    <p><br /><span style=\"text-decoration: underline;\"><strong><span style=\"background-color: #ccffcc;\">Moana:</span></strong></span> Wow! Great story Gramma! I want to cross the ocean.....</p>\n"
                + "    <p><br /><span style=\"text-decoration: underline;\"><strong><span style=\"background-color: #ccffcc;\">Kids:</span></strong></span> Noooot. Monster. Monster there. Nooooooo</p>\n"
                + "    <p><br /><span style=\"text-decoration: underline; background-color: #ccffcc;\"><strong>Tui:</strong></span> Thank you mother, that&rsquo;s enough. No one goes beyond the<br />reef.... We&rsquo;re safe here. There&rsquo;s no monster.</p>\n"
                + "    <p><br /><span style=\"text-decoration: underline; background-color: #ccffcc;\"><strong>Moana:</strong></span> But Papa, I want to go beyond the reef....</p>\n"
                + "    <p><br /><span style=\"text-decoration: underline; background-color: #ccffcc;\"><strong>Tui:</strong></span> There is nothing beyond the reef but storm and rough seas.<br />Moana, you must learn where you&rsquo;re meant to be.</p>\n"
                + "    <h3 style=\"text-align: center;\"><br />SONG: Where You Are</h3>\n"
                + "    <h4 style=\"text-align: center;\"><span style=\"background-color: #ff99cc;\">&nbsp;START CUE 29&nbsp;</span></h4>\n"
                + "  </body>\n"
                + "</html>\n");
        show.add(richText);

        return show;
    }

    private static Show oneVideoMediaCue() {
        Show show = new Show()
                .setName("One Video Media Cue");

        MediaResource resource = new MediaResource()
                .setFile("C:\\Users\\Matt\\Documents\\CAD\\2022 Spring Recital\\cue_media\\Holiday 2021 - Contemporary.mp4")
                .setStopAt(Duration.ofSeconds(20))
                .setFadeIn(Duration.ofSeconds(5))
                .setFadeOut(Duration.ofSeconds(5))
                .setVolume(1.0);
        MediaCue cue = (MediaCue) new MediaCue()
                .setMedia(resource)
                .setDesc("Contemporary 2021")
                .setName(cueName())
                .setId(id());
        show.add(cue);

        return show;
    }
}
