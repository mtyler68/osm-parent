package org.mattie.osm.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 *
 * @author Matthew Tyler
 */
@Data
@ToString
@Accessors(chain = true)
public class TriviaCue extends Cue {

    private String title;

    private String subTitle;

    private List<TriviaCard> cards = new ArrayList<>();
}
