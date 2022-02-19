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
        MONO_VALUE,
        MONO_CENTILE,
        DISCRETE,
        RGB_VALUE,
        RGB_CENTILE
    }

    private int universe = 1;

    private int address = 1;

    private Type type = Type.MONO_VALUE;
}
