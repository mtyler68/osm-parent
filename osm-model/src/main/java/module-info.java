
module OsmModel {
    requires com.fasterxml.jackson.dataformat.yaml;
    requires org.yaml.snakeyaml;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires lombok;

    exports org.mattie.osm.model;
}
