package org.mattie.osm.app.viewmodel;

import java.io.File;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Track;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.mattie.osm.model.MediaCue;
import org.mattie.osm.model.MediaResource;

/**
 *
 * @author Matthew Tyler
 */
@Slf4j
@ToString(callSuper = true, of = {"volume", "startAt", "stopAt", "fadeIn", "fadeOut", "source"})
public class MediaCueViewModel extends CueViewModel<MediaCue> {

    @Getter
    @Setter
    private MediaPlayer mediaPlayer;

    @Getter
    @Setter
    private double volume;

    @Getter
    @Setter
    private Duration startAt;

    @Getter
    @Setter
    private Duration stopAt;

    @Getter
    @Setter
    private String source;

    @Getter
    @Setter
    private Duration fadeIn;

    @Getter
    @Setter
    private Duration fadeOut;

    private BooleanProperty videoDisplayed = new SimpleBooleanProperty(false);

    @Override
    public void play() {
        switch (mediaPlayer.getStatus()) {
            case PAUSED:
            case READY:
            case STOPPED:
                mediaPlayer.play();
                break;
        }
        super.play();
    }

    @Override
    public void pause() {
        log.info("{}: pause(): {}", getName(), this);
        switch (mediaPlayer.getStatus()) {
            case PLAYING:
                mediaPlayer.pause();
                break;
        }
        super.pause();
    }

    @Override
    public void stop() {
        log.info("{}: stop(): {}", getName(), this);
        switch (mediaPlayer.getStatus()) {
            case PAUSED:
            case PLAYING:

                mediaPlayer.stop();
                break;
        }
        super.stop();
    }

    @Override
    public String getSimpleType() {
        return "Media";
    }

    @Override
    public Class<MediaCue> getCueClass() {
        return MediaCue.class;
    }

    public void buildMediaPlayer() {
        File mediaFile = new File(getSource());
        Media media = new Media(mediaFile.toURI().toString());
        setMediaPlayer(new MediaPlayer(media));

        getMediaPlayer().setVolume(getVolume());
        getMediaPlayer().setStartTime(getStartAt());

        if (getStopAt() != null) {
            getMediaPlayer().setStopTime(getStopAt());
        }

        getMediaPlayer().setOnReady(() -> {
            ObservableList<Track> tracks = media.getTracks();
            for (Track track : tracks) {
                log.info("{}: buildMediaPlayer(): track={}: {}",
                        getName(), track, MediaCueViewModel.this);
            }
        });

        getMediaPlayer().currentTimeProperty().addListener((ov, oldVal, newVal) -> {
            java.time.Duration d = java.time.Duration.ofMillis((int) newVal.toMillis());
            String durString = String.format("%02d:%02d:%02d.%d", d.toHoursPart(), d.toMinutesPart(), d.toSecondsPart(), d.toMillisPart() / 100);
            //log.info("{}: currentTime={}: {}", getName(), durString, MediaCueViewModel.this);
            setCurrentTime(durString);
        });

    }

    @Override
    public void buildAnimation() {
        buildMediaPlayer();

        Timeline timeline = new Timeline();
        timeline.setDelay(getDelay());
        double startVolume = (getFadeIn().greaterThan(Duration.ZERO) ? 0 : getVolume());

        getMediaPlayer().setOnPlaying(() -> log.info("{}: (playing): {}", getName(), MediaCueViewModel.this));
        getMediaPlayer().setOnEndOfMedia(() -> {
            log.info("{}: (endOfMedia): mediaPlay.status={}: {}",
                    getName(), getMediaPlayer().getStatus(), MediaCueViewModel.this);
            getMediaPlayer().stop();
        });

        // Start
        timeline.getKeyFrames().add(new KeyFrame(Duration.ZERO,
                (evt) -> {
                    log.info("{}: [timeline play]: mediaPlayer.status={}: {}", getName(), getMediaPlayer().getStatus(), MediaCueViewModel.this);
                    getMediaPlayer().play();
                },
                new KeyValue(getMediaPlayer().volumeProperty(), startVolume)));

        if (getFadeIn().greaterThan(Duration.ZERO)) {
            timeline.getKeyFrames().add(new KeyFrame(getFadeIn(),
                    (evt) -> {
                        log.info("{}: [fadeIn complete]: {}", getName(), MediaCueViewModel.this);
                    },
                    new KeyValue(getMediaPlayer().volumeProperty(), getVolume())));
        }

        // End
        double endVolume = getVolume();
        if (getFadeOut().greaterThan(Duration.ZERO)) {
            endVolume = 0;
            timeline.getKeyFrames().add(new KeyFrame(getMediaPlayer().getTotalDuration().subtract(getFadeOut()),
                    (evt) -> {
                        log.info("{}: [fadeOut start]: {}", getName(), MediaCueViewModel.this);
                    },
                    new KeyValue(getMediaPlayer().volumeProperty(), getVolume())));
        }

        timeline.getKeyFrames().add(new KeyFrame(getMediaPlayer().getTotalDuration(),
                (evt) -> {
                    log.info("{}: [fadeOut complete]: {}", getName(), MediaCueViewModel.this);
                },
                new KeyValue(getMediaPlayer().volumeProperty(), endVolume)));

        timeline.setOnFinished((evt) -> {
            finished();
        });
        setAnimation(timeline);
    }

    @Override
    protected CueViewModel newInstance() {
        return new MediaCueViewModel();
    }

    @Override
    public void copyFrom(MediaCue cue) {
        log.info("copyFrom(): cue={}", cue);
        super.copyFrom(cue);
        copyFrom(cue.getMedia());
    }

    public void copyFrom(MediaResource resource) {
        log.info("copyFrom(): resource={}", resource);

        setSource(resource.getFile());
        setVolume(resource.getVolume());
        setStartAt(fromTimeDuration(resource.getStartAt()));

        if (resource.getStopAt() != null) {
            setStopAt(fromTimeDuration(resource.getStopAt()));
        }

        setFadeIn(fromTimeDuration(resource.getFadeIn()));
        setFadeOut(fromTimeDuration(resource.getFadeOut()));
    }

}
