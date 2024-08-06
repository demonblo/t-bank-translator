package ru.tbank.translator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Translation {

    private long id;
    private String ipAddress;
    private String inputWords;
    private String outputWords;
}
