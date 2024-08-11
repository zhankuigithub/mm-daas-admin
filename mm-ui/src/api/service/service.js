import request from "@/utils/request";
import { daas } from "../configUrl";

// 查询平台服务列表
export function listService(query) {
  return request({
    url: `/inner/service/page`,
    method: "post",
    data: query,
  });
}

// 查询平台服务详细
export function getService(id) {
  return request({
    url: `/inner/service/info?id=${id}`,
    method: "get",
  });
}

// 新增平台服务
export function addService(data) {
  return request({
    url: `/inner/service/create`,
    method: "post",
    data: data,
  });
}

// 修改平台服务
export function updateService(data) {
  return request({
    url: `/inner/service/modify`,
    method: "put",
    data: data,
  });
}

// 删除平台服务
export function delService(id) {
  return request({
    url: `/inner/service/delete?id=${id}`,
    method: "delete",
  });
}
