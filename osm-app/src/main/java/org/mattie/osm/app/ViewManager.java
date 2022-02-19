package org.mattie.osm.app;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author Matthew Tyler
 */
@RequiredArgsConstructor
public class ViewManager {

    private final Stage primaryStage;

    private View currentView;

    private Map<View, Object> controllerMap = new HashMap<>();

    public void setPrimaryTitle(String title) {
        primaryStage.setTitle(title);
    }

    public void showPrimary() {
        primaryStage.show();
    }

    public <T> T getController(View view) {
        return (T) controllerMap.get(view);
    }

    public <T> T switchView(View view) {

        try {
            FXMLLoader loader = new FXMLLoader(ViewManager.class.getResource(view.getFxmlResource()));
            loader.load();
            Parent root = loader.getRoot();
            T controller = loader.getController();

            controllerMap.put(view, controller);

            if (primaryStage.getScene() == null) {
                primaryStage.setScene(new Scene(root));
            } else {
                primaryStage.getScene().setRoot(root);
            }

            if (view == View.START) {
                primaryStage.sizeToScene();
                primaryStage.centerOnScreen();
            }

            if (currentView != null && currentView == View.START) {
                primaryStage.sizeToScene();
                primaryStage.centerOnScreen();
            }

            // Update current view
            this.currentView = view;

            return controller;
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
