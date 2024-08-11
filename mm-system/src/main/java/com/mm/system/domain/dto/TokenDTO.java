package com.mm.system.domain.dto;

import lombok.Builder;
import lombok.Data;

/**
 * <p>
 * token信息
 * </p>
 *
 * @author
 * @date 2023-04-24
 */

@Builder
@Data
public class TokenDTO {

    /** 创建时间 */
    private Long timestamp;

    /** token */
    private String token;
}
