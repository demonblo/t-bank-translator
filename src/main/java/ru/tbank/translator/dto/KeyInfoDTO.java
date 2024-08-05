package ru.tbank.translator.dto;

import lombok.Data;

@Data
public class KeyInfoDTO {
    public String id;
    public String service_account_id;
    public String private_key;

}
