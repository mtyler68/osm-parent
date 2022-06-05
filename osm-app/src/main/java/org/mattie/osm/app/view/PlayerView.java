package org.mattie.osm.app.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.binding.StringExpression;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.controlsfx.control.action.Action;
import org.controlsfx.control.action.ActionMap;
import org.controlsfx.control.action.ActionProxy;
import org.controlsfx.control.action.ActionUtils;
import org.mattie.osm.app.ActionId;
import org.mattie.osm.app.App;
import org.mattie.osm.app.viewmodel.ShowViewModel;
import org.mattie.osm.app.views.CueTableViewController;

/**
 * FXML Controller class
 *
 * @author Matthew Tyler
 */
@Slf4j
public class PlayerView implements Initializable {

    @FXML
    private CueTableViewController cueTableViewController;

    @Getter
    @FXML
    private RichTextViewController richTextViewController;

    @Getter
    @FXML
    private NoteViewController noteViewController;

    public Label showStatusLabel;

    // Hot Key controls
    public TableView hotKeyTable;

    public TableColumn hotKeyColumn;

    public TableColumn hotKeyDescColumn;

    // Show Control controls
    public Button playButton;

    public Button stopButton;

    public Button pauseButton;

    @Getter
    private HotKeyView hotKeyView;

    /**
     * Initializes the controller class.
     */
    @Override

    public void initialize(URL url, ResourceBundle rb) {

        // Hot Key View
        hotKeyView = new HotKeyView(hotKeyTable, hotKeyColumn, hotKeyDescColumn);
        hotKeyView.initialize(url, rb);

        // Action configuration
        ActionMap.register(this);
        Action playAction = ActionMap.action(ActionId.PLAY);
        ActionUtils.configureButton(playAction, playButton);

        Action stopAction = ActionMap.action(ActionId.STOP);
        ActionUtils.configureButton(stopAction, stopButton);

        Action pauseAction = ActionMap.action(ActionId.PAUSE);
        ActionUtils.configureButton(pauseAction, pauseButton);

    }

    @ActionProxy(id = ActionId.PLAY, text = "Play")
    public void playButtonAction() {
        log.info("playButtonAction()");
        play();
    }

    @ActionProxy(id = ActionId.STOP, text = "Stop")
    public void stopButtonAction() {
        log.info("stopButtonAction()");
        stop();
    }

    @ActionProxy(id = ActionId.PAUSE, text = "Pause")
    public void pauseButtonAction() {
        log.info("pauseButtonAction()");
        pause();
    }

    public void loadShow() {
        ShowViewModel showViewModel = App.getShowManager().getShow();

        cueTableViewController.clear();
        cueTableViewController.loadCueTable(showViewModel.getCueViewModels());

        showStatusLabel.setText(showViewModel.getState().name());
        showStatusLabel.textProperty().bind(
                StringExpression.stringExpression(showViewModel.stateProperty()));

        hotKeyView.loadShow(showViewModel);
    }

    public void clearDisplays() {
        getNoteViewController().clear();

        getRichTextViewController().clear();
    }

    public void setCueNote(String text) {
        getNoteViewController().addNote("00:00", text);
    }

    @FXML
    public void play() {
        log.info("play()");

        if (App.getShowManager().getShow().getState() == ShowViewModel.State.STOPPED) {
            clearDisplays();
        }

        App.getShowManager().play();
    }

    @FXML
    public void pause() {
        log.info("pause()");

        App.getShowManager().pause();
    }

    @FXML
    public void stop() {
        log.info("stop()");

        App.getShowManager().stop();
    }

    @FXML
    public void open() {
        log.info("open()");

        App.getShowManager().showOpenShowDialog()
                .ifPresent(showFile
                        -> Platform.runLater(() -> App.getShowManager().openShow(showFile)));
    }

    @FXML
    public void nextCue() {
        log.info("nextCue()");
        App.getShowManager().nextCue();
    }

    @FXML
    public void toggleScreenView() {
        App.toggleScreen();
    }

    @FXML
    public void toggleFullScreen() {
        App.toggleFullScreen();
    }
}
