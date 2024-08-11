import request from "@/utils/request";
import { daas } from "../configUrl";

// 查询接口信息列表
export function listApi(query) {
  return request({
    url: `/inner/api/page`,
    method: "post",
    data: query,
  });
}

// 查询接口信息详细
export function getApi(id) {
  return request({
    url: `/inner/api/info?id=${id}`,
    method: "get",
  });
}

// 新增接口信息
export function addApi(data) {
  return request({
    url: `/inner/api/create`,
    method: "post",
    data: data,
  });
}

// 修改接口信息
export function updateApi(data) {
  return request({
    url: `/inner/api/modify`,
    method: "put",
    data: data,
  });
}

// 删除接口信息
export function delApi(id) {
  return request({
    url: `/inner/api/delete?id=${id}`,
    method: "delete",
  });
}
