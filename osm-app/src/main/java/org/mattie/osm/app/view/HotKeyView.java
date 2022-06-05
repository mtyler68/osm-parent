package org.mattie.osm.app.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.RequiredArgsConstructor;
import org.mattie.osm.app.viewmodel.ShowViewModel;

/**
 *
 * @author Matthew Tyler
 */
@RequiredArgsConstructor
public class HotKeyView implements Initializable {

    private final TableView hotKeyTable;

    private final TableColumn hotKeyColumn;

    private final TableColumn hotKeyDescColumn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        hotKeyColumn.setCellValueFactory(new PropertyValueFactory("hotKey"));
        hotKeyDescColumn.setCellValueFactory(new PropertyValueFactory("desc"));
    }

    public void loadShow(ShowViewModel showViewModel) {
        hotKeyTable.itemsProperty().bind(showViewModel.hotKeyCueViewModelsProperty());
    }

}
