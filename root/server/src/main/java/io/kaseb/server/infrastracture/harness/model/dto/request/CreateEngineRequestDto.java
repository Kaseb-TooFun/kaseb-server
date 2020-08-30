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
    private static class Dataset {
        private String ttl = "356 days";
    }

    @Data
    private static class SparkConfiguration {
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
    private static class Algorithm {
        private List<Indicator> indicators;

        public Algorithm() {
            this.indicators = Arrays.asList(
                    new Indicator("GOAL"),
                    new Indicator("BANNER_SHOW"),
                    new Indicator("BANNER_CLOSE"),
                    new Indicator("BANNER_BUTTON_CLICK"),
                    new Indicator("BANNER_PREVIEW_TIME"),
                    new Indicator("ANIMATION_RUN"),
                    new Indicator("ANIMATION_CLICK_ITEM"),
                    new Indicator("NEW_USER_REGISTER"),
                    new Indicator("SESSION_DURATION")
            );
        }

        @AllArgsConstructor
        @Data
        private static class Indicator {
            private String name;
        }
    }
}
