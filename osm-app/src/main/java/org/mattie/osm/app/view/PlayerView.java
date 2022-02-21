package org.mattie.osm.app.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.binding.StringExpression;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.controlsfx.control.action.Action;
import org.controlsfx.control.action.ActionMap;
import org.controlsfx.control.action.ActionProxy;
import org.controlsfx.control.action.ActionUtils;
import org.mattie.osm.app.App;
import org.mattie.osm.app.model.CueViewModel;
import org.mattie.osm.app.model.GroupCueViewModel;
import org.mattie.osm.app.model.ShowViewModel;

/**
 * FXML Controller class
 *
 * @author Matthew Tyler
 */
@Slf4j
public class PlayerView implements Initializable {

    public Label cueMessageField;

    public Label showStatusLabel;

    public TableView cueTable;

    public TableColumn cueTableColumn;

    public TableColumn descTableColumn;

    public TableColumn stateTableColumn;

    public TableColumn triggerTableColumn;

    public TableColumn typeTableColumn;

    public TableColumn delayTableColumn;

    public TableColumn lengthTableColumn;

    public TableColumn currentTimeTableColumn;

    public TableView hotKeyTable;

    public TableColumn hotKeyColumn;

    public TableColumn hotKeyDescColumn;

    public WebView richTextView;

    public Label richTextTitleLabel;

    public Pagination richTextPagination;

    public Button playButton;

    @Getter
    private WebEngine richTextWebEngine;

    @Getter
    private List<String> richTextPages = new ArrayList<>();

    /**
     * Initializes the controller class.
     */
    @Override

    public void initialize(URL url, ResourceBundle rb) {
        cueMessageField.setText("Press DELETE to acknowledge");

        cueTableColumn.setCellValueFactory(new PropertyValueFactory("name"));
        descTableColumn.setCellValueFactory(new PropertyValueFactory("desc"));
        stateTableColumn.setCellValueFactory(new PropertyValueFactory("state"));
        triggerTableColumn.setCellValueFactory(new PropertyValueFactory("trigger"));
        typeTableColumn.setCellValueFactory(new PropertyValueFactory("simpleType"));
        delayTableColumn.setCellValueFactory(new PropertyValueFactory("delay"));
        currentTimeTableColumn.setCellValueFactory(new PropertyValueFactory("currentTime"));

        hotKeyColumn.setCellValueFactory(new PropertyValueFactory("hotKey"));
        hotKeyDescColumn.setCellValueFactory(new PropertyValueFactory("desc"));

        richTextWebEngine = richTextView.getEngine();

        richTextPagination.currentPageIndexProperty().addListener((ov, t, t1) -> {
            displayRichTextPage(ov.getValue().intValue());
        });

        // Action configuration
        ActionMap.register(this);
        Action playAction = ActionMap.action("play");
        ActionUtils.configureButton(playAction, playButton);
    }

    @ActionProxy(id = "play", text = "Play")
    public void playButtonAction() {
        log.info("playButtonAction()");
        play();
    }

    public void loadShow() {
        ShowViewModel showViewModel = App.getShowManager().getShow();

        cueTable.getItems().clear();
        loadCueTable(showViewModel.getCueViewModels());

        showStatusLabel.setText(showViewModel.getState().name());
        showStatusLabel.textProperty().bind(
                StringExpression.stringExpression(showViewModel.stateProperty()));

        hotKeyTable.itemsProperty().bind(showViewModel.hotKeyCueViewModelsProperty());

    }

    private void loadCueTable(List<CueViewModel> viewModels) {
        viewModels.stream()
                .forEach(cueViewModel -> {
                    cueTable.getItems().add(cueViewModel);
                    if (cueViewModel instanceof GroupCueViewModel) {
                        loadCueTable(((GroupCueViewModel) cueViewModel).getChildren());
                    }
                });
    }

    public void clearDisplays() {
        cueMessageField.setText("");
        getRichTextWebEngine().loadContent("");
        richTextTitleLabel.setText("Script View");
        richTextPagination.setPageCount(1);
    }

    public void setCueNote(String text) {
        cueMessageField.setText(text);
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

    public void setRichTextPages(List<String> richTextPages) {
        this.richTextPages = richTextPages;
        getRichTextWebEngine().loadContent("");
        richTextTitleLabel.setText("Script View");

        richTextPagination.setPageCount(richTextPages.size() > 0 ? richTextPages.size() : 1);
        richTextPagination.setCurrentPageIndex(0);

        if (!richTextPages.isEmpty()) {
            displayRichTextPage(0);
        }
    }

    public void setRichTextPage(int index) {
        richTextPagination.setCurrentPageIndex(index);
    }

    private void displayRichTextPage(int index) {
        setRichTextContent(richTextPages.get(index));
    }

    public void setRichTextContent(String text) {
        getRichTextWebEngine().loadContent(text);
        getRichTextWebEngine().titleProperty().addListener((t) -> {
            log.info("setRichTextContent(): title={}: {}", getRichTextWebEngine().getTitle(), PlayerView.this);
            richTextTitleLabel.setText(getRichTextWebEngine().getTitle());
        });
    }

    public void nextRichTextPage() {
        int index = richTextPagination.getCurrentPageIndex() + 1;
        if (index < richTextPages.size()) {
            setRichTextPage(index);
        }
    }

    public void prevRichTextPage() {
        int index = richTextPagination.getCurrentPageIndex() - 1;
        if (index >= 0) {
            setRichTextPage(index);
        }
    }

}
