package org.mattie.osm.app.view;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.BackingStoreException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextInputDialog;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import org.mattie.osm.app.App;

/**
 * FXML Controller class
 *
 * @author Matthew Tyler
 */
public class StartView implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public void fullscreen() {
        //App.getPrimaryStage().setFullScreen(!App.getPrimaryStage().isFullScreen());
        //App.getStage2().setFullScreen(!App.getStage2().isFullScreen());
    }

    @FXML
    public void openShow() throws BackingStoreException {
        App.getShowManager().showOpenShowDialog()
                .ifPresent(showFile
                        -> Platform.runLater(() -> App.getShowManager().openShow(showFile)));

    }

    @FXML
    public void newShow() {
        TextInputDialog dlg = new TextInputDialog("");
        dlg.setTitle("New Show Name");
        String optionalMasthead = "New Show";
        dlg.getDialogPane().setContentText("Show Name");
        configureSampleDialog(dlg, optionalMasthead);
        showDialog(dlg);
    }

    private void configureSampleDialog(Dialog<?> dlg, String header) {
        Window owner = App.getPrimaryStage();
        if (header != null) {
            dlg.getDialogPane().setHeaderText(header);
        }

        dlg.initStyle(StageStyle.DECORATED);
        dlg.initOwner(owner);
    }

    private void showDialog(Dialog<?> dlg) {
        Window owner = App.getPrimaryStage();
        dlg.initOwner(owner);

        dlg.showAndWait().ifPresent(result -> System.out.println("Result is " + result));

    }

}
