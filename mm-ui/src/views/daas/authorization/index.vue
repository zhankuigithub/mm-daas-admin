<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      size="mini"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="服务名称">
        <el-select clearable v-model="queryParams.serviceId">
          <el-option
            v-for="(item, index) of services"
            :key="index"
            :label="item.serviceName"
            :value="item.id"
            >{{ item.serviceName }}</el-option
          >
        </el-select>
      </el-form-item>

      <el-form-item label="应用名称">
        <el-select clearable v-model="queryParams.appId">
          <el-option
            v-for="(item, index) of apps"
            :key="index"
            :label="item.appName"
            :value="item.id"
            >{{ item.appName }}</el-option
          >
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索</el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
          >重置</el-button
        >
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          >新增服务授权</el-button
        >
      </el-col>
    </el-row>

    <el-table ref="tables" v-loading="loading" :data="list" size="mini"  border>
      <el-table-column label="ID" align="center" prop="id" />
      <el-table-column label="服务名称" align="center" prop="serviceId">
        <template slot-scope="scope">
          {{ serviceMap.get(scope.row.serviceId).serviceName }}
        </template>
      </el-table-column>

      <el-table-column label="服务类型" align="center" prop="serviceType">
        <template slot-scope="scope">
          {{ getGlobalDicLabel("serviceTypes", scope.row.serviceType) }}
        </template>
      </el-table-column>

      <el-table-column label="应用名称" align="center" prop="appId">
      <template slot-scope="scope">
        {{ appMap.get(scope.row.appId).appName }}
      </template>
    </el-table-column>

      <el-table-column label="应用类型" align="center" prop="appId">
      <template slot-scope="scope">
        {{ getGlobalDicLabel("appTypes",appMap.get(scope.row.appId).appType)  }}
      </template>
    </el-table-column>
      <el-table-column label="开始时间" align="center" prop="beginTime" />
      <el-table-column label="结束时间" align="center" prop="endTime" />
      <el-table-column label="调用限制" align="center" prop="limitTotal" />
      <el-table-column label="鉴权开关">
        <template slot-scope="scope">
          <span :class="scope.row.openAuth === 0 ? 'font-1' : 'font-2'">{{
            getGlobalDicLabel("openAuths", scope.row.openAuth)
          }}</span>
        </template>
      </el-table-column>

      <el-table-column label="授权key（自动生成）" show-overflow-tooltip align="center" prop="authorizationCode" />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.curPagerNo"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
    <ModifyDialog
      v-if="open"
      :open="open"
      :op-type="opType"
      :record="editRecord"
      :apps="apps"
      :services="services"
      :serviceMap="serviceMap"
      @close="closeDialog"
      @refreshTable="getList"
    ></ModifyDialog>
  </div>
</template>

<script>
import ModifyDialog from "./components/ModifyDialog";
import { handleCommonTrip } from "@/utils";

export default {
  components: { ModifyDialog },
  name: "index",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 表格数据
      list: [],
      // 是否显示弹出层
      title: "",
      open: false,
      opType: "",
      // 日期范围
      dateRange: [],
      // 表单参数
      form: {},
      // 查询参数
      queryParams: {
        curPagerNo: 1,
        pageSize: 10,
        serviceId: null,
        appId: null,
      },
      editRecord: {},
      // 应用列表
      apps: {},
      appMap: {},
      // 服务列表
      services: {},
      serviceMap: {},
    };
  },
  created() {
    this.initSelect();
    this.getList();
  },
  methods: {
    /** 查询登录日志 */
    getList() {
      this.loading = true;
      this.$api.authorization.pageQuery(this.queryParams).then((response) => {
        this.list = response.result.page.list;
        this.total = response.result.page.rowsCount;
        this.loading = false;
      });
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.curPagerNo = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.queryParams.curPagerNo = 1;
    },
    handleAdd() {
      this.reset();
      this.open = true;
      this.opType = "ADD";
      this.editRecord = {};
    },
    handleUpdate(row) {
      this.open = true;
      this.opType = "EDIT";
      this.editRecord = row;
    },
    reset() {
      this.resetForm("form");
    },
    handleDelete(row) {
      this.$modal
        .confirm('是否确认删除id为"' + row.id + '"的授权关系？')
        .then(() => {
          this.$api.authorization.remove(row.id).then((res) => {
            if (res.code === 200) {
              handleCommonTrip("success", res.message);
              this.getList();
            }
          });
        });
    },
    closeDialog() {
      this.open = false;
    },
    initSelect() {
      this.$api.application.list().then((res) => {
        if (res.code === 200) {
          this.apps = res.result;
          let map = new Map();
          this.apps.forEach((a) => {
            map.set(a.id, a);
          });
          this.appMap = map;
        }
      });
      this.$api.application.serviceList().then((res) => {
        if (res.code === 200) {
          this.services = res.result;

          let map = new Map();
          this.services.forEach((a) => {
            map.set(a.id, a);
          });
          this.serviceMap = map;
        }
      });
    },
  },
};
</script>
<style>
  .font-1 {
    color: #FA8072;
    font-weight: bold;
  }
  .font-2 {
    color: #4169E1;
    font-weight: bold;
  }
</style>
