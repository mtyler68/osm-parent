package org.mattie.osm.demo;

import java.time.Duration;
import org.mattie.osm.model.MediaResource;

/**
 *
 * @author Matthew Tyler
 */
public final class Constants {

    public static String MOANA_PG1 = "<!DOCTYPE html>\n"
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
            + "</html>";

    public static String MOANA_PG2 = "<!DOCTYPE html>\n"
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
            + "</html>\n";

    public static MediaResource resourceWelcomeToTheJungle() {
        return new MediaResource()
                .setFile("C:\\Users\\Matt\\Documents\\CAD\\2022 Spring Recital\\cue_media\\01 Welcome to the Jungle.mp3")
                .setName("Welcome to the Jungle")
                .setStopAt(Duration.parse("PT4M34S"));
    }

    public static MediaResource resourceStrandedInTheJungle() {
        return new MediaResource()
                .setFile("C:\\Users\\Matt\\Documents\\CAD\\2022 Spring Recital\\cue_media\\01 Stranded In the Jungle.mp3")
                .setName("Stranded In the Jungle")
                .setStopAt(Duration.parse("PT3M5S"));
    }

    public static MediaResource resourceRunThroughTheJungle() {
        return new MediaResource()
                .setFile("C:\\Users\\Matt\\Documents\\CAD\\2022 Spring Recital\\cue_media\\13 Run Through the Jungle.mp3")
                .setName("Run Through the Jungle")
                .setStopAt(Duration.parse("PT3M5S"));
    }

    public static MediaResource resourceBrazilSamba() {
        return new MediaResource()
                .setFile("C:\\Users\\Matt\\Documents\\CAD\\2022 Spring Recital\\cue_media\\bensound-brazilsamba.mp3")
                .setName("Brazil Samba")
                .setStopAt(Duration.parse("PT4M1S"));
    }

    public static MediaResource resourceCircusHorn() {
        return new MediaResource()
                .setFile("C:\\Users\\Matt\\Documents\\CAD\\2022 Spring Recital\\cue_media\\mixkit-clown-horn-at-circus-715.wav")
                .setName("Circus Horn")
                .setStopAt(Duration.parse("PT2.9S"));
    }

    public static MediaResource resourceWaterfall() {
        return new MediaResource()
                .setFile("C:\\Users\\Matt\\Documents\\CAD\\2022 Spring Recital\\cue_media\\mixkit-flowing-waterfall-on-forest-2516.wav")
                .setName("Waterfall")
                .setStopAt(Duration.parse("PT8.9S"));
    }
}
