import request from "@/utils/request";
import { daas } from "../configUrl";

// 查询数据源配置列表
export function listInfo(query) {
  return request({
    url: `/inner/datasource/page`,
    method: "post",
    data: query,
  });
}
// 查询数据源配置列表不分页
export function list(query) {
  return request({
    url: `/inner/datasource/list`,
    method: "get",
    data: query,
  });
}

// 查询数据源配置详细
export function getInfo(id) {
  return request({
    url: `/inner/datasource/info?id=${id}`,
    method: "get",
  });
}

// 新增数据源配置
export function addInfo(data) {
  return request({
    url: `/inner/datasource/create`,
    method: "post",
    data: data,
  });
}

// 修改数据源配置
export function updateInfo(data) {
  return request({
    url: `/inner/datasource/modify`,
    method: "put",
    data: data,
  });
}

// 删除数据源配置
export function delInfo(id) {
  return request({
    url: `/inner/datasource/delete?id=${id}`,
    method: "delete",
  });
}

//测试数据库链接
export function test(data) {
  return request({
    url: `/inner/datasource/test`,
    method: "post",
    data: data,
  });
}
//获取数据库表
export function tables(data) {
  return request({
    url: `/inner/datasource/tables`,
    method: "post",
    data: data,
  });
}
//获取表字段
export function columns(data) {
  return request({
    url: `/inner/datasource/columns`,
    method: "post",
    data: data,
  });
}

//获取数据源类型
export function dataTypes() {
  return request({
    url: `/inner/datasource/types`,
    method: "get",
  });
}
