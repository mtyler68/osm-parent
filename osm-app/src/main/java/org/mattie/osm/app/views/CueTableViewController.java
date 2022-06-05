package org.mattie.osm.app.views;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.mattie.osm.app.viewmodel.CueViewModel;
import org.mattie.osm.app.viewmodel.GroupCueViewModel;

/**
 * FXML Controller class
 *
 * @author Matthew Tyler
 */
public class CueTableViewController implements Initializable {

    public TableView cueTable;

    public TableColumn cueTableColumn;

    public TableColumn descTableColumn;

    public TableColumn stateTableColumn;

    public TableColumn triggerTableColumn;

    public TableColumn typeTableColumn;

    public TableColumn delayTableColumn;

    public TableColumn lengthTableColumn;

    public TableColumn currentTimeTableColumn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cueTableColumn.setCellValueFactory(new PropertyValueFactory("name"));
        descTableColumn.setCellValueFactory(new PropertyValueFactory("desc"));
        stateTableColumn.setCellValueFactory(new PropertyValueFactory("state"));
        triggerTableColumn.setCellValueFactory(new PropertyValueFactory("trigger"));
        typeTableColumn.setCellValueFactory(new PropertyValueFactory("simpleType"));
        delayTableColumn.setCellValueFactory(new PropertyValueFactory("delay"));
        currentTimeTableColumn.setCellValueFactory(new PropertyValueFactory("currentTime"));
        lengthTableColumn.setCellValueFactory(new PropertyValueFactory("duration"));
    }

    public void clear() {
        cueTable.getItems().clear();
    }

    public void loadCueTable(List<CueViewModel> viewModels) {
        viewModels.stream()
                .forEach(cueViewModel -> {
                    cueTable.getItems().add(cueViewModel);
                    if (cueViewModel instanceof GroupCueViewModel) {
                        loadCueTable(((GroupCueViewModel) cueViewModel).getChildren());
                    }
                });
    }

}
