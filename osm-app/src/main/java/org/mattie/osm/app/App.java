package org.mattie.osm.app;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.controlsfx.control.action.Action;
import org.controlsfx.control.action.ActionMap;
import org.mattie.osm.app.view.ScreenView;

/**
 * JavaFX App
 */
@Slf4j
public class App extends Application {

    @Getter
    private static ViewManager viewManager;

    @Getter
    private static Stage primaryStage;

    @Getter
    private static ShowManager showManager;

    private static Stage screenStage;

    private static ScreenView screenView;

    private static Map<KeyCode, String> keyCodeToActionIdMap = new HashMap<>();

    public static void toggleScreen() {
        if (screenStage != null) {
            if (screenStage.isShowing()) {
                screenStage.setFullScreen(false);
                screenStage.hide();
            } else {
                screenStage.show();
                screenStage.setFullScreen(true);
            }
        }
    }

    public static void toggleFullScreen() {
        primaryStage.setFullScreen(!primaryStage.isFullScreen());
    }

    @Override
    public void start(Stage stage) throws IOException {

        primaryStage = stage;

        viewManager = new ViewManager(stage);
        viewManager.setPrimaryTitle("Open Show Master");
        viewManager.switchView(View.START);
        viewManager.showPrimary();

        showManager = new ShowManager();

        primaryStage.setOnCloseRequest((evt) -> {
            if (screenStage != null) {
                screenStage.close();
            }
        });

        ObservableList<Screen> screens = Screen.getScreens();
        if (screens.size() > 1) {
            Screen screen2 = screens.get(1);

            FXMLLoader loader = new FXMLLoader(App.class.getResource("/org/mattie/osm/app/view/ScreenView.fxml"));
            loader.load();

            screenStage = new Stage(StageStyle.UNDECORATED);
            screenStage.setScene(new Scene(loader.getRoot()));
            screenView = loader.getController();

            screenStage.sizeToScene();
            screenStage.setX(screen2.getVisualBounds().getMinX());
            screenStage.setY(screen2.getVisualBounds().getMinY());
            screenStage.setAlwaysOnTop(true);
            screenStage.centerOnScreen();

        }

//        primaryStage = stage;
//
//        scene = new Scene(loadFXML("view/StartView"), 640, 480);
//        stage.setScene(scene);
//        stage.setTitle("Open Show Master");
//        stage.show();
//        ObservableList<Screen> screens = Screen.getScreens();
//        screens.stream()
//                .forEach(s -> log.info("screen {}", s));
//        if (screens.size() > 1) {
//            Screen screen2 = screens.get(1);
//            stage2 = new Stage();
//
//            stage2.setX(screen2.getVisualBounds().getMinX());
//            stage2.setY(screen2.getVisualBounds().getMinY());
//            stage2.setWidth(600);
//            stage2.setHeight(400);
//            stage2.setAlwaysOnTop(true);
//            stage2.sizeToScene();
//
//            stage2.show();
//
//        }
        // Configure KeyCode to actions
        keyCodeToActionIdMap.put(KeyCode.SPACE, "PLAY");
        keyCodeToActionIdMap.put(KeyCode.END, "STOP");

        stage.addEventFilter(KeyEvent.KEY_PRESSED, (evt) -> {
            log.info("key: {},{},{}", evt.getText(), evt.getCode().name(), evt.getCharacter());

            Action act = null;
            if (keyCodeToActionIdMap.containsKey(evt.getCode())) {
                act = ActionMap.action(keyCodeToActionIdMap.get(evt.getCode()));
            } else if (ActionMap.action(evt.getCode().name()) != null) {
                act = ActionMap.action(evt.getCode().name());
            }

            if (act != null) {
                act.handle(new ActionEvent());
            } else {
                getShowManager().playHotKey(evt.getText().toLowerCase());
            }

        });

//        Platform.runLater(() -> {
//            try {
//                final Show show = Utils.getObjectMapper()
//                        .readValue(new File("C:\\projects\\osm-demos\\target\\shows\\demo_04.yaml"), Show.class);
//                final ShowViewModel showViewModel = ShowViewModel.of(show);
//                showViewModel.play();
//
//
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        });
    }

    public static void main(String[] args) {
        launch();
    }

}
