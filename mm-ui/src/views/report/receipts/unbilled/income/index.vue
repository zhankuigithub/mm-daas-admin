<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      size="mini"
      :inline="true"
      v-show="showSearch"
      label-width="100px"
    >
      <el-form-item prop="enterpriseName" label="托运人名称">
        <el-input
          v-model.trim="queryParams.enterpriseName"
          placeholder="请输入托运人名称"
          clearable
          size="small"
        />
      </el-form-item>
      <el-form-item prop="officeName" label="业务机构名称">
        <el-input
          v-model.trim="queryParams.officeName"
          placeholder="请输入业务机构名称"
          clearable
          size="small"
        />
      </el-form-item>

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
      <el-table-column type="index" label="序号" align="center" />
      <el-table-column label="托运人名称" align="center" prop="enterpriseName"/>
      <el-table-column label="业务机构名称" align="center" prop="officeName"/>
      <el-table-column label="货主运费总额" align="center">
        <el-table-column label="含税收入" align="center" prop="taxIncludedIncome">
          <template slot-scope="scope">
          <span>{{
            getThousandNum(scope.row.taxIncludedIncome)
          }}</span>
          </template>
        </el-table-column>
        <el-table-column label="未税收入" align="center" prop="taxExcludedIncome">
          <template slot-scope="scope">
          <span>{{
            getThousandNum(scope.row.taxExcludedIncome)
          }}</span>
          </template>
        </el-table-column>
        <el-table-column label="税" align="center" prop="tax">
          <template slot-scope="scope">
          <span>{{
            getThousandNum(scope.row.tax)
          }}</span>
          </template>
        </el-table-column>
      </el-table-column>
      <el-table-column label="合同负债" align="center" prop="contractLiabilities">
        <template slot-scope="scope">
          <span>{{
            getThousandNum(scope.row.contractLiabilities)
          }}</span>
        </template>
      </el-table-column>
      <el-table-column label="应收运费差额" align="center">
        <template slot-scope="scope">
          <span>{{
            getThousandNum(scope.row.receivableFreightDiff)
          }}</span>
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
  import { getThousandNum } from '@/utils'

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
        },
        editRecord: {},
      };
    },
    created() {
      this.getList();
    },
    methods: {
      getThousandNum,
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
        this.handleQuery();
      },
    },
  };
</script>
