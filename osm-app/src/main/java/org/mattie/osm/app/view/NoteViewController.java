package org.mattie.osm.app.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.mattie.osm.app.viewmodel.NoteViewModel;

/**
 * FXML Controller class
 *
 * @author Matthew Tyler
 */
public class NoteViewController implements Initializable {

    public TableView noteTable;

    public TableColumn timeColumn;

    public TableColumn noteColumn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        timeColumn.setCellValueFactory(new PropertyValueFactory("time"));
        noteColumn.setCellValueFactory(new PropertyValueFactory("note"));
    }

    public void addNote(String time, String note) {
        noteTable.getItems().add(0, new NoteViewModel(time, note));
    }

    public void clear() {
        noteTable.getItems().clear();
    }
}
