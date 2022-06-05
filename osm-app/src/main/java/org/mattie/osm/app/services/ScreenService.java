package org.mattie.osm.app.services;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.mattie.osm.app.viewmodel.ScreenDeviceViewModel;

/**
 *
 * @author Matthew Tyler
 */
public class ScreenService {

    private ObjectProperty<ObservableList<ScreenDeviceViewModel>> screenDeviceViewModels
            = new SimpleObjectProperty<>(FXCollections.observableArrayList());

    public ObjectProperty<ObservableList<ScreenDeviceViewModel>> screenDeviceViewModelsProperty() {
        return screenDeviceViewModels;
    }

    public ObservableList<ScreenDeviceViewModel> getScreenDeviceViewModels() {
        return screenDeviceViewModels.get();
    }

    public void setScreenDeviceViewModels(ObservableList<ScreenDeviceViewModel> newScreenDeviceViewModels) {
        screenDeviceViewModels.set(newScreenDeviceViewModels);
    }

    public void closeNonPrimaryStages() {
        getScreenDeviceViewModels().stream()
                .filter(vm -> !"primary".equals(vm.getName()))
                .forEach(vm -> vm.getStage().close());
    }
}
