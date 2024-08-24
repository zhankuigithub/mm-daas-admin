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

      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
        >搜索
        </el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
        >重置
        </el-button
        >
      </el-form-item>
    </el-form>


    <el-table ref="tables" v-loading="loading" :data="list" size="mini" border>
      <el-table-column label="序号" align="center" prop=""/>
      <el-table-column label="托运人名称" align="center" prop=""/>
      <el-table-column label="业务机构名称" align="center" prop=""/>
      <el-table-column label="货主运费总额" align="center">
        <el-table-column label="含税收入" align="center" prop=""/>
        <el-table-column label="未税收入" align="center" prop=""/>
        <el-table-column label="税" align="center" prop=""/>
      </el-table-column>
      <el-table-column label="合同负债" align="center"/>
      <el-table-column label="应收运费差额" align="center"/>
      <el-table-column label="备注" align="center"/>
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
      getList() {
        this.loading = true;
        this.$api.report.reportInfo('receipts/unbilled/income', this.queryParams).then((response) => {
          this.list = response.result.page.list;
          this.total = response.result.page.rowsCount;
          this.loading = false;
        });
      },
      handleQuery() {
        this.queryParams.curPagerNo = 1;
        this.getList();
      },
      resetQuery() {
        this.dateRange = [];
        this.resetForm("queryForm");
        this.queryParams.curPagerNo = 1;
      },
    },
  };
</script>
