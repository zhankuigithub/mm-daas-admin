import request from "@/utils/request";
import { daas } from "../configUrl";

// 列表
export const list = (apiId) => {
  return request({
    url: `/inner/api/log/list?apiId=${apiId}`,
    method: "get",
  });
};
// page
export const page = (query) => {
  return request({
    url: `/inner/api/log/page`,
    method: "post",
    data: query,
  });
};
