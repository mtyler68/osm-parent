package org.mattie.osm.app.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.controlsfx.control.action.ActionMap;

/**
 *
 * @author Matthew Tyler
 */
@Slf4j
@ToString(of = {"name", "cueIndex", "state"})
public class ShowViewModel {

    public static enum State {

        /**
         * Indicates that the show has not been started or has been stopped. A
         * play request from this state will start from the beginning. Will only
         * transition to a PLAYING state from STOPPED.
         */
        STOPPED,
        /**
         * Indicates the show is playing. The show can transition to PLAYING,
         * PAUSED, or WAITING states from PLAYING.
         */
        PLAYING,
        /**
         * Indicates the show has been paused from a PLAYING state. Can
         * transition to either a PLAYING or STOPPED state from PAUSED.
         */
        PAUSED,
        /**
         * Indicates the show is waiting to continue on a manual cue by
         * triggering the play action. Can transition to PLAYING or STOPPED from
         * WAITING. *
         */
        WAITING
    }

    @Setter
    @Getter
    private String name;

    @Setter
    @Getter
    private List<CueViewModel> cueViewModels = new ArrayList<>();

    private ObjectProperty<ObservableList<CueViewModel>> hotKeyCueViewModels
            = new SimpleObjectProperty<>(FXCollections.observableArrayList());

    private AtomicInteger cueIndex = new AtomicInteger(0);

    private ObjectProperty<State> state = new SimpleObjectProperty<>(State.STOPPED);

    public ReadOnlyObjectProperty<State> stateProperty() {
        return state;
    }

    public void setState(State newState) {
        state.set(newState);
    }

    public State getState() {
        return state.get();
    }

    public ObjectProperty<ObservableList<CueViewModel>> hotKeyCueViewModelsProperty() {
        return hotKeyCueViewModels;
    }

    public ObservableList<CueViewModel> getHotKeyViewModels() {
        return hotKeyCueViewModels.get();
    }

    public void setHotKeyCueViewModels(ObservableList<CueViewModel> newHotKeyCueViewModels) {
        hotKeyCueViewModels.set(newHotKeyCueViewModels);
    }

    /**
     * Start play from the beginning of a show (because AUTO_START on the first
     * cue is ignored. Also, this will resume play from a paused position or
     * start play for a manually triggered cue.
     *
     * STOPPED -> PLAY: Plays from the beginning of the show PAUSED -> PLAY:
     * Resumes play from paused position and cue WAITING -> PLAY: Plays the
     * current cue
     *
     */
    public void play() {
        log.info("play(): {}", this);
        switch (getState()) {

            case STOPPED:
                cueIndex.set(0);
                resetCues();

            case PAUSED:
            case WAITING:
                startCurrent();
                break;
        }
    }

    private void startCurrent() {
        log.info("startCurrent(): {}", this);
        getCurrCueViewModel().ifPresentOrElse((cvm) -> {
            cvm.setOnFinished(this::playNext);
            setState(State.PLAYING); // TODO: Update the state of the action
            cvm.play();

            // TODO: Remove this in lieu of binding to state property
            // TODO: Makes constants to use for action ID rather than strings
            ActionMap.action("play").setDisabled(true);

        }, () -> {
            setState(State.STOPPED);
            ActionMap.action("play").setDisabled(false);
            log.info("startCurrent(): end of show: {}", ShowViewModel.this);
        });
    }

    public void nextCue() {
        log.info("stopCurrent(): {}", this);
        getCurrCueViewModel().ifPresentOrElse((cvm) -> {
            if (cvm.getState() != CueViewModel.State.STOPPED || cvm.getState() != CueViewModel.State.FINISHED) {
                cvm.stop();
                playNext();
            }
        }, () -> {
            log.info("stopCurrent(): show already stopped: {}", ShowViewModel.this);
        });
    }

    public void playNext() {
        log.info("playNext(): {}", this);
        cueIndex.incrementAndGet();

        getCurrCueViewModel().ifPresentOrElse((cvm) -> {
            switch (cvm.getTrigger()) {
                case AUTO_START:
                    startCurrent();
                    break;
                case MANUAL:
                    setState(State.WAITING);
                    ActionMap.action("play").setDisabled(false);
                    log.info("{}: -- WAITING FOR MANUAL CUE START --: {}", cvm.getName(), cvm);
                    break;
            }
        }, () -> {
            setState(State.STOPPED);
            ActionMap.action("play").setDisabled(false);
            log.info("playNext(): end of show: {}", ShowViewModel.this);
        });

    }

    public void pause() {
        log.info("pause(): {}", this);
        if (cueIndex.get() < cueViewModels.size()) {
            CueViewModel currentCueViewModel = cueViewModels.get(cueIndex.get());
            setState(State.PAUSED);
            ActionMap.action("play").setDisabled(false);
            currentCueViewModel.pause();
        } else {
            log.info("pause(): no cues left to pause");
        }
    }

    public void stop() {
        log.info("stop(): {}", this);

        switch (getState()) {
            case PAUSED:
            case PLAYING:
            case WAITING:
                getCurrCueViewModel().ifPresent(viewModel -> viewModel.stop());
                setState(State.STOPPED);
                ActionMap.action("play").setDisabled(false);
                break;
        }
    }

    private Optional<CueViewModel> getCurrCueViewModel() {
        return (cueIndex.get() < cueViewModels.size())
                ? Optional.of(cueViewModels.get(cueIndex.get()))
                : Optional.empty();
    }

    public void buildAnimation() {
        getCueViewModels().stream()
                .forEach(c -> c.buildAnimation());
    }

    private void resetCues() {
        getCueViewModels().stream()
                .forEach(cvm -> cvm.resetCue());
    }

    public void playHotKey(String key) {
        getHotKeyViewModels().stream()
                .filter(vm -> vm.getHotKey().equalsIgnoreCase(key))
                .findFirst().ifPresent(vm -> vm.play());
    }
}
