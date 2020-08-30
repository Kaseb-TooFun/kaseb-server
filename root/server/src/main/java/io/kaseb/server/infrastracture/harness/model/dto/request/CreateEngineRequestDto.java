package io.kaseb.server.infrastracture.harness.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Data
public class CreateEngineRequestDto {
    private String engineId;
    private String engineFactory = "com.actionml.engines.ur.UREngine";
    private Dataset dataset = new Dataset();
    private SparkConfiguration sparkConf = new SparkConfiguration();
    private Algorithm algorithm = new Algorithm();

    public CreateEngineRequestDto(String engineId) {
        this.engineId = engineId;
    }

    @Data
    private class Dataset {
        private String ttl = "356 days";
    }

    @Data
    private class SparkConfiguration {
        private String master = "local";
        @JsonProperty("spark.serializer")
        private String sparkSerializer = "org.apache.spark.serializer.KryoSerializer";
        @JsonProperty("spark.kryo.registrator")
        private String sparkKryoRegistrator = "org.apache.mahout.sparkbindings.io.MahoutKryoRegistrator";
        @JsonProperty("spark.kryo.referenceTracking")
        private String sparkKryoReferenceTracking = "false";
        @JsonProperty("spark.kryoserializer.buffer")
        private String sparkKryoserializerBuffer = "300m";
        @JsonProperty("spark.executor.memory")
        private String sparkExecutorMemory = "3g";
        @JsonProperty("spark.driver.memory")
        private String sparkDriverMemory = "3g";
        @JsonProperty("spark.es.index.auto.create")
        private String sparkEsIndexAutoCreate = "true";
        @JsonProperty("spark.es.nodes")
        private String sparkEsNodes = "proxyelasticsearch";
        @JsonProperty("spark.es.nodes.wan.only")
        private String sparkEsNodesWanOnly = "true";

    }

    @Data
    private class Algorithm {
        private List<Indicator> indicators;

        public Algorithm() {
            this.indicators = Arrays.asList(
                    new Indicator("goal"),
                    new Indicator("banner_show"),
                    new Indicator("banner_close"),
                    new Indicator("banner_button_click"),
                    new Indicator("banner_preview_time"),
                    new Indicator("animation_run"),
                    new Indicator("animation_click_item"),
                    new Indicator("new_user_register"),
                    new Indicator("session_duration")
            );
        }

        @AllArgsConstructor
        @Data
        private class Indicator {
            private String name;
        }
    }
}
