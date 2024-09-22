/**
 * 该文件存储常量值
 */
// 全局静态变量
const globalStaticVariable = {
  // 应用类型
  appTypes: [
    { value: 1, label: "个人" },
    { value: 2, label: "公司" },
    { value: 3, label: "内部" },
  ],
  // 服务类型
  serviceTypes: [
    { value: 0, label: "不限" },
    { value: 1, label: "内网" },
    { value: 2, label: "外网" },
  ],
  // 字段类型
  fieldTypes: [
    { value: "Number", label: "Number" },
    { value: "String", label: "String" },
    { value: "Object", label: "Object" },
    { value: "Array", label: "Array" },
    { value: "Boolean", label: "Boolean" },
  ],
  responseTypes: [
    { value: "double", label: "double" },
    { value: "map", label: "map" },
    { value: "string", label: "string" },
    { value: "long", label: "long" },
    { value: "list", label: "list" },
    { value: "boolean", label: "boolean" },
  ],
  // 付费
  restrictions: [
    { value: 0, label: "免费" },
    { value: 1, label: "付费" },
  ],
  // 安全级别
  safetyLevels: [
    { value: 1, label: "一级" },
    { value: 2, label: "二级" },
    { value: 3, label: "三级" },
  ],
  // 请求方式
  reqMethods: [
    { value: 1, label: "GET" },
    { value: 2, label: "POST" },
    { value: 3, label: "PUT" },
    { value: 4, label: "DELETE" },
  ],
  serviceStatus: [
    { value: 0, label: "正常" },
    { value: 1, label: "停用" },
    { value: 2, label: "暂停" },
  ],
  execTypes: [
    { value: "C", label: "新增" },
    { value: "R", label: "查询" },
    { value: "U", label: "修改" },
    { value: "D", label: "删除" },
  ],
  openHumps: [
    { value: 0, label: "不开启" },
    { value: 1, label: "开启" },
  ],
  openAuths: [
    { value: 0, label: "不开启" },
    { value: 1, label: "开启" },
  ],
  apiTypes: [
    { value: 1, label: "接口" },
    { value: 2, label: "接口组" },
  ],
  connectTypes: [
    { value: "join", label: "join" },
    { value: "union", label: "union" },
    { value: "chain", label: "chain" },
  ],
  orderStatusList: [
    { value: 1, label: "取消" },
    { value: 0, label: "关闭" },
  ],
};
export { globalStaticVariable };
