import request from "@/utils/request";
import { daas } from "../configUrl";

// 分页
export const pageQuery = (data) => {
  return request({
    url: `/inner/authorization/page`,
    method: "post",
    data: data,
  });
};

// 下拉列表
export const listQuery = () => {
  return request({
    url: `/inner/authorization/page`,
    method: "get",
  });
};

// 增
export const create = (data) => {
  return request({
    url: `/inner/authorization/create`,
    method: "post",
    data: data,
  });
};

// 改
export const modify = (data) => {
  return request({
    url: `/inner/authorization/modify`,
    method: "put",
    data: data,
  });
};

// 删
export const remove = (id) => {
  return request({
    url: `/inner/authorization/delete?id=${id}`,
    method: "delete",
  });
};

// 列表
export const list = () => {
  return request({
    url: `/inner/authorization/list`,
    method: "get",
  });
};
