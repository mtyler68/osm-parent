package org.mattie.osm.app.viewmodel;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.mattie.osm.app.App;
import org.mattie.osm.model.RichTextCue;

/**
 *
 * @author Matthew Tyler
 */
@Slf4j
public class RichTextCueViewModel extends CueViewModel<RichTextCue> {

    @Setter
    @Getter
    private List<String> pages = new ArrayList<>();

    public void displayPage(int index) {
        log.info("{}: displayPage(): index={}: {}", getName(), index, this);
        App.getShowManager().getPlayerView().setRichTextPage(index);
    }

    @Override
    public String getSimpleType() {
        return "Rich Text";
    }

    @Override
    public Class<RichTextCue> getCueClass() {
        return RichTextCue.class;
    }

    @Override
    public void buildAnimation() {
        Timeline timeline = new Timeline();
        timeline.setDelay(getDelay());

        timeline.getKeyFrames().add(new KeyFrame(Duration.ZERO, (evt) -> {
            log.info("{}: [display rich text]: {}", getName(), this);
            App.getShowManager().getPlayerView().setRichTextPages(pages);
            displayPage(0);
        }));

        // Padding
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(10), (evt) -> {
        }));

        timeline.setOnFinished((evt) -> finished());

        setAnimation(timeline);
    }

    @Override
    protected CueViewModel newInstance() {
        return new RichTextCueViewModel();
    }

    @Override
    public void copyFrom(RichTextCue cue) {

        getPages().addAll(cue.getPages());
        super.copyFrom(cue);
    }

}
