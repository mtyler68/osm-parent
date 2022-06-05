package org.mattie.osm.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 *
 * @author Matthew Tyler
 */
@Data
@Accessors(chain = true)
public class DmxChannel {

    public static enum Type {
        DIMMER_VALUE,
        DIMMER_CENTILE,
        DISCRETE,
        RGB_VALUE,
        RGB_CENTILE
    }

    private int universe = 1;

    private int address = 1;

    private Type type = Type.DIMMER_VALUE;

    /**
     *
     */
    private int[] addresses = new int[1];
}
