package org.mattie.osm.app.viewmodel;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author Matthew Tyler
 */
@Getter
@RequiredArgsConstructor
public class NoteViewModel {

    private final String time;

    private final String note;
}
