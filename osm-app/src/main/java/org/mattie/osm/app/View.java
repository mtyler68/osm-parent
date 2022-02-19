package org.mattie.osm.app;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author Matthew Tyler
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum View {

    EDITOR("/org/mattie/osm/app/view/EditorView.fxml"),
    PLAYER("/org/mattie/osm/app/view/PlayerView.fxml"),
    START("/org/mattie/osm/app/view/StartView.fxml");

    @Getter
    private final String fxmlResource;
}
