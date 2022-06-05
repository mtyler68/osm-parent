package org.mattie.osm.app.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Matthew Tyler
 */
public class ScreenDeviceViewModel {

    private StringProperty name = new SimpleStringProperty();

    @Getter
    @Setter
    private Stage stage;

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String newName) {
        name.set(newName);
    }

    public String getName() {
        return name.get();
    }

}
