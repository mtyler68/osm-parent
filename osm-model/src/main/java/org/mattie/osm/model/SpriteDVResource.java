package org.mattie.osm.model;

import java.time.Duration;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 *
 * @author Matthew Tyler
 */
@Data
@ToString(callSuper = true)
@Accessors(chain = true)
public class SpriteDVResource {

    private DmxChannel dmxChannel;

    private int videoNumber;

    private Duration length = Duration.ZERO;
}
