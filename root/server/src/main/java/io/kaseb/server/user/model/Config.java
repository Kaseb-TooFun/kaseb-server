package io.kaseb.server.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@Data
public class Config {
    private UUID id;
    private String value;

    public Config(String value) {
        this.id = UUID.randomUUID();
        this.value = value;
    }
}
