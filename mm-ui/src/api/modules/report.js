import request from "@/utils/request";

/**
 * 报表查询接口
 * @param reportName 报表名称
 * @param params 报表SQL语句中动态参数
 */
export function reportInfo(reportName, data) {
    return request({
        url: `/report/query/${reportName}`,
        method: 'post',
        data: data
    })
}

export function exportExcel(reportName, data) {
  return request({
    url: `/report/excel/${reportName}`,
    method: 'post',
    data: data
  })
}
