package org.mattie.osm.app.model;

import java.util.ServiceLoader;
import javafx.animation.Animation;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
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

    @Setter
    @Getter
    private Animation animation;

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

    public void resetCue() {
        setState(State.READY);
    }

    public void play() {
        log.info("{}: play(): {}", getName(), this);

        switch (getAnimation().getStatus()) {
            case STOPPED:
                setState(State.PLAYING);
                animation.playFromStart();
                break;

            case PAUSED:
                animation.play();
                setState(State.PLAYING);
                break;
        }
    }

    public void pause() {
        log.info("{}: pause(): {}", getName(), this);

        switch (getAnimation().getStatus()) {
            case PAUSED:
                // No need to pause again
                break;
            case STOPPED:
                // Why pause when stopped?
                break;
            case RUNNING:
                animation.pause();
                setState(State.PAUSED);
                break;
        }
    }

    public void stop() {
        log.info("{}: stop(): {}", getName(), this);

        switch (getAnimation().getStatus()) {
            case PAUSED:
            case RUNNING:
                getAnimation().stop();
                setState(State.STOPPED);
                break;
        }
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
}
