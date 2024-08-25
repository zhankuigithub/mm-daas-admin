package com.mm.system.domain.dto;

import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class TokenDTO {

    /** 创建时间 */
    private Long timestamp;

    /** token */
    private String token;
}
