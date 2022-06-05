package org.mattie.osm.app.viewmodel;

import java.util.Optional;
import java.util.ServiceLoader;
import javafx.animation.Animation;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.mattie.osm.model.Cue;
import org.mattie.osm.model.TriggerType;

/**
 *
 * @author Matthew Tyler
 */
@Slf4j
@ToString(of = {"name", "state", "trigger", "delay"})
public abstract class CueViewModel<C extends Cue> {

    public static enum State {
        READY,
        PLAYING,
        PAUSED,
        STOPPED,
        FINISHED
    }

    private static final ServiceLoader<CueViewModel> loaders = ServiceLoader.load(CueViewModel.class);

    private ObjectProperty<Runnable> onFinished = new SimpleObjectProperty<>();

    @Getter
    @Setter
    private TriggerType trigger = TriggerType.MANUAL;

    @Setter
    @Getter
    private Duration delay;

    @Setter
    @Getter
    private String name;

    @Setter
    @Getter
    private String desc;

    @Setter
    @Getter
    private String id;

    private ObjectProperty<State> state = new SimpleObjectProperty<>(State.READY);

    private ObjectProperty<Animation> animation = new SimpleObjectProperty<>();

    private StringProperty currentTime = new SimpleStringProperty("00:00:00.0");

    private StringProperty duration = new SimpleStringProperty("00:00:00.0");

    @Setter
    @Getter
    private String hotKey;

    public abstract String getSimpleType();

    public abstract Class<C> getCueClass();

    public ReadOnlyObjectProperty<State> stateProperty() {
        return state;
    }

    public void setState(State newState) {
        state.set(newState);
    }

    public State getState() {
        return state.get();
    }

    public ReadOnlyObjectProperty<Runnable> onFinishedProperty() {
        return onFinished;
    }

    public void setOnFinished(Runnable onFinished) {
        this.onFinished.set(onFinished);
    }

    public Runnable getOnFinished() {
        return onFinished.get();
    }

    public ReadOnlyObjectProperty<Animation> animationProperty() {
        return animation;
    }

    public void setAnimation(Animation newAnimation) {
        animation.set(newAnimation);
    }

    public Optional<Animation> getAnimation() {
        return Optional.ofNullable(animation.get());
    }

    public ReadOnlyStringProperty durationProperty() {
        return duration;
    }

    public void setDuration(String newDuration) {
        duration.set(newDuration);
    }

    public String getDuration() {
        return duration.get();
    }

    public ReadOnlyStringProperty currentTimeProperty() {
        return currentTime;
    }

    public void setCurrentTime(String newCurrentTime) {
        this.currentTime.set(newCurrentTime);
    }

    public String getCurrentTime() {
        return currentTime.get();
    }

    public void resetCue() {
        setState(State.READY);
    }

    public void play() {
        log.info("{}: play(): {}", getName(), this);

        getAnimation().ifPresent(ani -> {
            switch (ani.getStatus()) {
                case STOPPED:
                    setState(State.PLAYING);
                    ani.playFromStart();
                    break;

                case PAUSED:
                    ani.play();
                    setState(State.PLAYING);
                    break;
            }
        });

    }

    public void pause() {
        log.info("{}: pause(): {}", getName(), this);

        getAnimation().ifPresent(ani -> {
            switch (ani.getStatus()) {
                case PAUSED:
                    // No need to pause again
                    break;
                case STOPPED:
                    // Why pause when stopped?
                    break;
                case RUNNING:
                    ani.pause();
                    setState(State.PAUSED);
                    break;
            }
        });

    }

    /**
     * Called by GroupCueViewModel on children.
     */
    public void embeddedPause() {
        log.info("{}: embeddedPause(): {}", getName(), this);
    }

    public void embeddedPlay() {
        log.info("{}: embeddedPlay(): {}", getName(), this);
    }

    public void embeddedStop() {
        log.info("{}: embeddedStop(): {}", getName(), this);
    }

    public void stop() {
        log.info("{}: stop(): {}", getName(), this);

        getAnimation().ifPresent(ani -> {
            switch (ani.getStatus()) {
                case PAUSED:
                case RUNNING:
                    ani.stop();
                    setState(State.STOPPED);
                    break;
            }
        });

    }

    /**
     * Implementations use calls to this method to build the internal animation
     * as well as other asynchronous and animated instances.
     */
    public abstract void buildAnimation();

    /**
     * Cues should call the finished method when done with animation or
     * execution.
     */
    protected void finished() {
        log.info("{}: finished(): {}", getName(), this);
        setState(State.FINISHED);

        Runnable _onFinished = getOnFinished();
        if (_onFinished != null) {
            log.info("{}: (running onFinished): {}", getName(), this);
            _onFinished.run();
        }
    }

    /**
     * If a cue type can be muted, the implementation will be provided.
     */
    public void mute() {
        // Do nothing by default.
    }

    protected Duration fromTimeDuration(java.time.Duration source) {
        return Duration.millis(source.toMillis());
    }

    protected java.time.Duration toTimeDuration(Duration source) {
        return java.time.Duration.ofMillis((long) source.toMillis());
    }

    protected String formatDuration(Duration source) {
        java.time.Duration d = java.time.Duration.ofMillis((int) source.toMillis());
        return String.format("%02d:%02d:%02d.%d", d.toHoursPart(), d.toMinutesPart(), d.toSecondsPart(), d.toMillisPart() / 100);
    }

    public void copyFrom(C cue) {
        setDesc(cue.getDesc());
        setId(cue.getId());
        setName(cue.getName());
        setDelay(fromTimeDuration(cue.getDelay()));
        setTrigger(cue.getTrigger());

        if (cue.getTrigger() == TriggerType.HOT_KEY) {
            setHotKey((String) cue.getTriggerProps().get("hotKey"));
        }
    }

    protected abstract CueViewModel newInstance();

    public static CueViewModel createCueViewModel(Cue cue) {
        log.info("createCueViewModel(): cue={}", cue);
        return loaders.stream()
                .filter(p -> {
                    log.info("createCueViewModel(): p.getCueClass={}, cue.class={}", p.get().getCueClass(), cue.getClass());
                    return p.get().getCueClass().equals(cue.getClass());
                })
                .map(p -> {
                    log.info("createCueViewModel(): p.newInstance={}", p.get().newInstance());
                    return p.get().newInstance();
                })
                .map(vm -> {
                    log.info("createCueViewModel(): vm={}, cue={}", vm, cue);
                    vm.copyFrom(cue);
                    return vm;
                })
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No CueViewModel registered for cue class: " + cue.getClass()));
    }

    /**
     * Can this cue view be faded?
     *
     * @return
     */
    public boolean isFadeable() {
        return false;
    }

    /**
     * If this cue can be faded, start fading with the given duration. At the
     * end of the fade, this cue will execute its finished tasks.
     *
     * If the duration results in a time greater than the remaining runtime, the
     * duration will be shortened to the remaining runtime.
     *
     * @param dur
     */
    public void fade(Duration dur) {
        throw new UnsupportedOperationException("This cue cannot be faded");
    }
}
