package org.mattie.osm.app.viewmodel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.mattie.osm.model.MediaPlaylistCue;

/**
 *
 * @author Matthew Tyler
 */
@Slf4j
public class MediaPlaylistCueViewModel extends CueViewModel<MediaPlaylistCue> {

    @Getter
    @Setter
    private double volume;

    @Getter
    @Setter
    private Duration crossfade;

    @Getter
    @Setter
    private List<MediaCueViewModel> mediaViewModels = new ArrayList<>();

    @Override
    public void play() {
        log.info("{}: play(): {}", getName(), this);
        getMediaViewModels().stream()
                .filter(mvm -> mvm.getMediaPlayer().getStatus() == MediaPlayer.Status.PAUSED)
                .forEach(mvm -> mvm.getMediaPlayer().play());

        super.play();
    }

    @Override
    public void pause() {
        log.info("{}: pause(): {}", getName(), this);
        mediaViewModels.stream()
                .filter(mvm -> mvm.getMediaPlayer().getStatus() == MediaPlayer.Status.PLAYING)
                .forEach(mvm -> mvm.getMediaPlayer().pause());
        super.pause();
    }

    @Override
    public void stop() {
        log.info("{}: stop(): {}", getName(), this);
        getMediaViewModels().stream()
                .filter(mvm -> mvm.getMediaPlayer().getStatus() == MediaPlayer.Status.PLAYING
                || mvm.getMediaPlayer().getStatus() == MediaPlayer.Status.PAUSED)
                .forEach(mvm -> mvm.getMediaPlayer().stop());
        super.stop();
    }

    @Override
    public String getSimpleType() {
        return "Media Playlist";
    }

    @Override
    public void copyFrom(MediaPlaylistCue cue) {
        log.info("copyFrom(): cue={}", cue);
        super.copyFrom(cue);

        setVolume(cue.getVolume());
        setCrossfade(fromTimeDuration(cue.getCrossfade()));

        // Create MediaCueViewModel instances to hold this playlist's media resources
        mediaViewModels = cue.getResources().stream()
                .map(c -> {
                    MediaCueViewModel viewModel = new MediaCueViewModel();
                    viewModel.copyFrom(c);
                    viewModel.buildMediaPlayer();
                    return viewModel;
                })
                .collect(Collectors.toList());

        // TODO: Need to adjust volume by multiplying playlist volume by mediaresource volume
    }

    @Override
    public Class<MediaPlaylistCue> getCueClass() {
        return MediaPlaylistCue.class;
    }

    @Override
    public void buildAnimation() {
        Timeline timeline = new Timeline();
        timeline.setDelay(getDelay());

        Duration startPos = Duration.ZERO;
        MediaCueViewModel lastMediaCueViewModel = null;

        for (MediaCueViewModel mediaViewModel : getMediaViewModels()) {
            double volume = mediaViewModel.getVolume() * getVolume();
            double startVolume = getCrossfade().equals(Duration.ZERO) ? volume : 0d;

            // Start frame
            timeline.getKeyFrames().add(new KeyFrame(startPos, (evt) -> {
                log.info("{}: [start media]: {}", getName(), mediaViewModel.getSource());
                mediaViewModel.getMediaPlayer().play();
            }, new KeyValue(mediaViewModel.getMediaPlayer().volumeProperty(), startVolume)));

            if (getCrossfade().greaterThan(Duration.ZERO)) {
                timeline.getKeyFrames().add(
                        new KeyFrame(
                                startPos.add(getCrossfade()),
                                (evt) -> log.info("{}: [fade in finished]: {}", getName(), mediaViewModel.getSource()),
                                new KeyValue(mediaViewModel.getMediaPlayer().volumeProperty(), volume)));
                timeline.getKeyFrames().add(
                        new KeyFrame(
                                startPos.add(mediaViewModel.getMediaPlayer().getTotalDuration().subtract(getCrossfade())),
                                (evt) -> log.info("{}: [fade out start]: {}", getName(), mediaViewModel.getSource()),
                                new KeyValue(mediaViewModel.getMediaPlayer().volumeProperty(), volume)));
                timeline.getKeyFrames().add(
                        new KeyFrame(
                                startPos.add(mediaViewModel.getMediaPlayer().getTotalDuration()),
                                (evt) -> log.info("{}: [fade out finish]: {}", getName(), mediaViewModel.getSource()),
                                new KeyValue(mediaViewModel.getMediaPlayer().volumeProperty(), 0)));
            }

            // End frame
            mediaViewModel.getMediaPlayer().setOnEndOfMedia(()
                    -> log.info("{}: [end media]: {}", getName(), mediaViewModel.getSource()));

            startPos = startPos.add(mediaViewModel.getMediaPlayer().getTotalDuration());

            // End keyframe of media to get timeline length correct for last media
            timeline.getKeyFrames().add(new KeyFrame(startPos));

            if (getCrossfade().greaterThan(Duration.ZERO)) {
                startPos = startPos.subtract(getCrossfade());
            }

            lastMediaCueViewModel = mediaViewModel;
        }

        // Finish configuration of view model
        timeline.setOnFinished((evt) -> finished());
        timeline.currentTimeProperty().addListener((ov, oldVal, newVal) -> {
            java.time.Duration d = java.time.Duration.ofMillis((int) newVal.toMillis());
            String durString = String.format("%02d:%02d:%02d.%d", d.toHoursPart(), d.toMinutesPart(), d.toSecondsPart(), d.toMillisPart() / 100);
            setCurrentTime(durString);
        });

        setAnimation(timeline);;

    }

    @Override
    protected CueViewModel newInstance() {
        return new MediaPlaylistCueViewModel();
    }

}
