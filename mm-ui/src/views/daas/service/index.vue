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
      <el-form-item label="服务名称" prop="serviceName">
        <el-input
          v-model="queryParams.serviceName"
          placeholder="请输入服务名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
          >新增</el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="serviceList"
      @selection-change="handleSelectionChange"
      size="mini"
      border
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="服务名称" align="center" prop="serviceName" />
      <el-table-column
        label="接口列表"
        align="center"
        prop="apiIds"
        show-overflow-tooltip
      >
        <template slot-scope="scope">
          {{ getNameByApiIds(scope.row.apiIds) }}
        </template>
      </el-table-column>
      <el-table-column label="服务基本信息" align="center" prop="serviceType">
        <template slot-scope="scope">
          {{ getGlobalDicLabel("serviceTypes", scope.row.serviceType) }} |
          {{ getGlobalDicLabel("safetyLevels", scope.row.safetyLevel) }} |
          {{ getGlobalDicLabel("restrictions", scope.row.restriction) }}
        </template>
      </el-table-column>
      <el-table-column label="服务描述" align="center" prop="serviceDesc" />
      <el-table-column label="服务状态" align="center" prop="serviceStatus">
        <template slot-scope="scope">
          {{ getGlobalDicLabel("serviceStatus", scope.row.serviceStatus) }}
        </template>
      </el-table-column>
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
      @close="closeDialog"
      @refreshTable="getList"
    ></ModifyDialog>
  </div>
</template>

<script>
import { listService, getService, delService } from "@/api/service/service";
import { globalStaticVariable } from "@/config";
import ModifyDialog from "./components/ModifyDialog";

export default {
  components: { ModifyDialog },
  name: "index",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      globalStaticVariable,
      // 平台服务表格数据
      serviceList: [],
      // 是否显示弹出层
      open: false,
      opType: "",
      editRecord: {},
      apis: [],
      apiMap: {},
      // 查询参数
      queryParams: {
        curPagerNo: 1,
        pageSize: 10,
        serviceName: null,
        serviceType: null,
        safetyLevel: null,
        serviceStatus: null,
        restriction: null,
        serviceDesc: null,
      },
    };
  },
  created() {
    this.initSelect();
    this.getList();
  },
  methods: {
    /** 查询平台服务列表 */
    getList() {
      this.loading = true;
      listService(this.queryParams).then((response) => {
        this.serviceList = response.result.page.list;
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
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    handleAdd() {
      this.open = true;
      this.opType = "ADD";
      this.editRecord = {};
    },
    handleUpdate(row) {
      this.open = true;
      this.opType = "EDIT";
      this.editRecord = row;
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal
        .confirm('是否确认删除平台服务编号为"' + ids + '"的数据项？')
        .then(function () {
          return delService(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        });
    },
    closeDialog() {
      this.open = false;
    },
    initSelect() {
      this.$api.doc.apiList({}).then((res) => {
        if (res.code === 200) {
          this.apis = res.result;
          let map = new Map();
          this.apis.forEach((a) => {
            map.set(a.id, a.apiName);
          });
          this.apiMap = map;
        }
      });
    },
    getNameByApiIds(apiIds) {
      let idStr = "";
      if (this.apiMap != null) {
        apiIds.forEach((item, index) => {
          let name = this.apiMap.get(parseInt(item));
          idStr += index > 0 ? "，" + name : name;
        });
      }
      return `【${idStr}】`;
    },
  },
};
</script>
