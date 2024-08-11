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
      <el-form-item label="接口名称" prop="interfaceId">
        <el-select clearable v-model="form.interfaceId" class="form-input">
          <el-option
            v-for="(item, index) of apis"
            :key="index"
            :label="item.apiName"
            :value="item.id"
            >{{ item.apiName }}</el-option
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
          >新增接口文档</el-button
        >
      </el-col>
    </el-row>

    <el-table ref="tables" v-loading="loading" :data="list">
      <el-table-column label="ID" align="center" prop="id" />
      <el-table-column label="接口名称" align="center" prop="interfaceId">
        <template slot-scope="scope">
          {{ apiMap.get(scope.row.interfaceId) }}
        </template>
      </el-table-column>
      <el-table-column label="接口类型" align="center" prop="interfaceId">
        <template slot-scope="scope">
          {{ categoryMap.get(apiCategoryIdMap.get(scope.row.interfaceId)) }}
        </template>
      </el-table-column>
      <el-table-column
        label="接口地址"
        align="center"
        prop="reqUrl"
        show-overflow-tooltip
      />
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
  </div>
</template>

<script>
import { handleCommonTrip } from "@/utils";

export default {
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
      },
      editRecord: {},
      // api
      apis: [],
      apiMap: {},
      apiCategoryIdMap: {},
      // 类型
      categories: [],
      categoryMap: {},
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
      this.$api.doc.page(this.queryParams).then((response) => {
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
      this.$router.push({ path: "/doc/edit" });
    },
    handleUpdate(row) {
      this.$router.push({ path: "/doc/edit", query: { id: row.id } });
    },
    reset() {
      this.resetForm("form");
    },
    handleDelete(row) {
      this.$modal
        .confirm('是否确认删除ID为"' + row.id + '"的数据项？')
        .then(() => {
          this.$api.doc.remove({ id: row.id }).then((res) => {
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
      this.$api.doc.apiList({}).then((res) => {
        if (res.code === 200) {
          this.apis = res.result;
          let map1 = new Map();
          let map2 = new Map();
          this.apis.forEach((a) => {
            map1.set(a.id, a.apiName);
            map2.set(a.id, a.categoryId);
          });
          this.apiMap = map1;
          this.apiCategoryIdMap = map2;
        }
      });

      this.$api.doc.categoryList({}).then((res) => {
        if (res.code === 200) {
          this.categories = res.result;
          let map = new Map();
          this.categories.forEach((a) => {
            map.set(a.id, a.categoryName);
          });
          this.categoryMap = map;
        }
      });
    },
  },
};
</script>
