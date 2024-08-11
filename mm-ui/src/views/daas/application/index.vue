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
      <el-form-item label="应用名称" prop="appName">
        <el-input
          v-model="queryParams.appName"
          placeholder="请输入应用名称"
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="应用账号" prop="appAccount">
        <el-input
          v-model="queryParams.appAccount"
          placeholder="请输入应用账号"
          clearable
          style="width: 240px"
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
          >新增应用</el-button
        >
      </el-col>
    </el-row>

    <el-table ref="tables" v-loading="loading" :data="list" size="mini"  border>
      <el-table-column label="ID" align="center" prop="id" />
      <el-table-column label="应用名称" align="center" prop="appName" />
      <el-table-column label="应用账号" align="center" prop="appAccount" />
      <el-table-column label="应用类型" align="center" prop="appType">
        <template slot-scope="scope">
          {{ getGlobalDicLabel("appTypes", scope.row.appType) }}
        </template>
      </el-table-column>
      <el-table-column label="应用所有人" align="center" prop="appOwner" />
      <el-table-column label="邮箱账号" align="center" prop="appEmail" />
      <el-table-column label="创建时间" align="center" prop="createTime" />
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
      open: false,
      opType: "",
      // 日期范围
      dateRange: [],
      // 查询参数
      queryParams: {
        curPagerNo: 1,
        pageSize: 10,
        appName: null,
        appAccount: null,
      },
      editRecord: {},
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询登录日志 */
    getList() {
      this.loading = true;
      this.$api.application.pageQuery(this.queryParams).then((response) => {
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
      this.open = true;
      this.opType = "ADD";
      this.editRecord = {};
    },
    handleUpdate(row) {
      this.open = true;
      this.opType = "EDIT";
      this.editRecord = row;
    },
    handleDelete(row) {
      this.$modal
        .confirm('是否确认删除平台应用为"' + row.appName + '"的数据项？')
        .then(() => {
          this.$api.application.remove(row.id).then((res) => {
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
  },
};
</script>
