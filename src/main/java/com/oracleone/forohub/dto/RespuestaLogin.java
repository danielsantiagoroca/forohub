package com.oracleone.forohub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespuestaLogin {

    private String token;
    private String type = "Bearer";
}

