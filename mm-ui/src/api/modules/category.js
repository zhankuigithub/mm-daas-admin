import request from "@/utils/request";
import { daas } from "../configUrl";

// 查询接口类型列表
export function listCategory(query) {
  return request({
    url: `/inner/category/page`,
    method: "post",
    data: query,
  });
}
export function list() {
  return request({
    url: `/inner/category/list`,
    method: "get",
  });
}

// 查询接口类型详细
export function getCategory(id) {
  return request({
    url: `/inner/category/info?id=${id}`,
    method: "get",
  });
}

// 新增接口类型
export function addCategory(data) {
  return request({
    url: `/inner/category/create`,
    method: "post",
    data: data,
  });
}

// 修改接口类型
export function updateCategory(data) {
  return request({
    url: `/inner/category/modify`,
    method: "put",
    data: data,
  });
}

// 删除接口类型
export function delCategory(id) {
  return request({
    url: `/inner/category/delete?id=${id}`,
    method: "delete",
  });
}
