import request from "@/utils/request";
import { daas } from "../configUrl";

// 分页
export const page = (data) => {
  return request({
    url: `/inner/doc/page`,
    method: "post",
    data: data,
  });
};

// 增
export const create = (data) => {
  return request({
    url: `/inner/doc/create`,
    method: "post",
    data: data,
  });
};

// 改
export const modify = (data) => {
  return request({
    url: `/inner/doc/modify`,
    method: "put",
    data: data,
  });
};

// 删
export const remove = (data) => {
  return request({
    url: `/inner/doc/delete`,
    method: "delete",
    data: data,
  });
};

// 临时list
export const apiList = (data) => {
  return request({
    url: `/inner/api/list`,
    method: "post",
    data: data,
  });
};

export const categoryList = () => {
  return request({
    url: `/inner/category/list`,
    method: "get",
  });
};

export const info = (id) => {
  return request({
    url: `/inner/doc/info?id=${id}`,
    method: "get",
  });
};
