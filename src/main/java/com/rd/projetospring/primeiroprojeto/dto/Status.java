package com.rd.projetospring.primeiroprojeto.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigInteger;

@Data
@NoArgsConstructor
public class Status {

    private BigInteger id;
    private String status;
}
