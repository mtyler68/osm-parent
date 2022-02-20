package org.mattie.osm.app;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import java.util.stream.Collectors;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.stage.FileChooser;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.mattie.osm.app.model.CueViewModel;
import org.mattie.osm.app.model.ShowViewModel;
import org.mattie.osm.app.view.PlayerView;
import org.mattie.osm.model.Show;
import org.mattie.osm.model.TriggerType;
import org.mattie.osm.model.Utils;

/**
 *
 * @author Matthew Tyler
 */
@Slf4j
public class ShowManager {

    private ObjectProperty<ShowViewModel> show = new SimpleObjectProperty<>();

    private ReadOnlyObjectProperty<ShowViewModel> roShow = new ReadOnlyObjectWrapper<>(show, "showProperty");

    @Getter
    private PlayerView playerView;

    public ReadOnlyObjectProperty<ShowViewModel> showProperty() {
        return roShow;
    }

    public void play() {
        log.info("play(): {}", show.get());
        if (show.get() != null) {
            show.get().play();
        }
    }

    public void pause() {
        log.info("pause(): {}", show.get());
        if (getShow() != null) {
            getShow().pause();
        }
    }

    public void stop() {
        log.info("stop(): {}", show.get());
        if (getShow() != null) {
            getShow().stop();
        }
    }

    public void nextCue() {
        log.info("stopCurrent(): {}", getShow());
        if (getShow() != null) {
            getShow().nextCue();
        }
    }

    public void setShow(ShowViewModel show) {
        this.show.set(show);
    }

    public ShowViewModel getShow() {
        return show.get();
    }

    public void openShow(File showFile) {
        log.info("openShow(): showFile={}", showFile);
        try {
            Show show = Utils.getObjectMapper().readValue(showFile, Show.class);
            ShowViewModel showViewModel = transform(show);
            setShow(showViewModel);

            this.playerView = App.getViewManager().switchView(View.PLAYER);
            App.getViewManager().setPrimaryTitle(show.getName() + " - Open Show Master");

            getPlayerView().loadShow();

            //showViewModel.play();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private ShowViewModel transform(Show show) {
        ShowViewModel showViewModel = new ShowViewModel();
        showViewModel.setName(show.getName());

        // Timeline cues
        showViewModel.setCueViewModels(show.getCues().stream()
                .filter(cue -> cue.getTrigger() == TriggerType.AUTO_START
                || cue.getTrigger() == TriggerType.MANUAL)
                .map(cue -> {
                    log.info("transform(): cue={}", cue);
                    return CueViewModel.createCueViewModel(cue);
                })
                .collect(Collectors.toList())
        );

        showViewModel.getCueViewModels().stream()
                .forEach(vm -> vm.buildAnimation());

        // TODO: Put hot key cues in the main cue list and have show view model skip hot key and time type cues
        // Hot Key cues
        List<CueViewModel> hotKeyCueViewModels = show.getCues().stream()
                .filter(cue -> cue.getTrigger() == TriggerType.HOT_KEY)
                .map(cue -> CueViewModel.createCueViewModel(cue))
                .collect(Collectors.toList());
        hotKeyCueViewModels.stream()
                .forEach(vm -> vm.buildAnimation());
        showViewModel.setHotKeyCueViewModels(FXCollections.observableArrayList(hotKeyCueViewModels));

        return showViewModel;
    }

    public Optional<File> showOpenShowDialog() {
        File recentDir = null;

        Preferences prefs = Preferences.userNodeForPackage(org.mattie.osm.app.App.class);
        recentDir = new File(prefs.get("recentDir", null));

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Show");
        if (recentDir != null && recentDir.exists()) {
            fileChooser.setInitialDirectory(recentDir);
        }
        final File showFile = fileChooser.showOpenDialog(App.getPrimaryStage());

        if (showFile != null) {

            prefs.put("recentDir", showFile.getParentFile().getAbsolutePath());
            try {
                prefs.flush();
            } catch (BackingStoreException ex) {
                log.warn("showOpenShowDialog(): exception saving preferences", ex);
            }
        }

        return Optional.ofNullable(showFile);
    }

    public void playHotKey(String key) {
        getShow().playHotKey(key);
    }
}
