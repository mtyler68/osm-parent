package org.mattie.osm.app.model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.mattie.osm.app.App;
import org.mattie.osm.model.NoteCue;

/**
 *
 * @author Matthew Tyler
 */
@Slf4j
@ToString(callSuper = true, of = {"text"})
public class NoteCueViewModel extends CueViewModel<NoteCue> {

    @Setter
    @Getter
    private String text;

    @Override
    protected CueViewModel newInstance() {
        return new NoteCueViewModel();
    }

    @Override
    public void copyFrom(NoteCue cue) {
        super.copyFrom(cue);
        setText(cue.getText());
    }

    public void displayNoteText() {
        log.info("{}: displayNoteText(): {}", getName(), this);
        App.getShowManager().getPlayerView().setCueNote(getText());
    }

    @Override
    public void buildAnimation() {
        Timeline timeline = new Timeline();
        timeline.setDelay(getDelay());

        timeline.getKeyFrames().add(new KeyFrame(Duration.ZERO, (evt) -> {
            log.info("{}: [display note]: {}", getName(), this);
            displayNoteText();
        }));

        // Padding
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(10), (evt) -> {
        }));

        timeline.setOnFinished((evt) -> finished());

        setAnimation(timeline);
    }

    @Override
    public String getSimpleType() {
        return "Note";
    }

    @Override
    public Class<NoteCue> getCueClass() {
        return NoteCue.class;
    }

}
