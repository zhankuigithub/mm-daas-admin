package com.mm.web.controller.report;

import com.mm.common.core.assembler.ResponseHelper;
import com.mm.common.core.assembler.ResponseMessage;
import com.mm.common.exception.ServiceException;
import com.mm.web.application.service.ApiBaseApplicationService;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@RestController
@RequestMapping("report/query")
public class ReportController {

    @Resource
    ApiBaseApplicationService apiBaseApplicationService;

    /**
     * <p> 通过api路径获取执行</p>
     *
     * @param params 动态参数（可选择传递的参数 curPagerNo“页码” pageSize“大小”）
     * @return api接口相应
     **/
    @CrossOrigin
    @RequestMapping("/**")
    @ApiModelProperty("查询报表")
    public ResponseMessage<Object> outExecutor(
            @RequestBody(required = false) Map<String, Object> params,
            HttpServletRequest request) throws Exception {
        String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String bestMatchPattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        AntPathMatcher matcher = new AntPathMatcher();
        String matchedPath = matcher.extractPathWithinPattern(bestMatchPattern, path);
        if (StringUtils.isBlank(matchedPath)) {
            throw new ServiceException("apiPath 不能为空!");
        }
        return ResponseHelper.success(apiBaseApplicationService.executeByPath(matchedPath, params));
    }
}
