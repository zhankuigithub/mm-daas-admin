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
      <el-form-item prop="depositNumber" label="运单号">
        <el-input
          v-model.trim="queryParams.depositNumber"
          placeholder="请输入运单号"
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
        <el-button
          type="success"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-show="false"
        >导出
        </el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
        >重置
        </el-button
        >
      </el-form-item>
    </el-form>


    <el-table ref="tables" v-loading="loading" :data="list" size="mini" border>
      <el-table-column type="index" label="序号" align="center"/>
      <el-table-column label="司机姓名" align="center" prop="carrierName"/>
      <el-table-column label="二级户名称" align="center" prop="accountName" width="230"/>
      <el-table-column label="出金时间" align="center" prop="withdrawalTime" width="180"/>
      <el-table-column label="出金金额" align="center" prop="withdrawalAmount">
        <template slot-scope="scope">
          <span>{{
            getThousandNum(scope.row.withdrawalAmount)
          }}</span>
        </template>
      </el-table-column>
      <el-table-column label="出金账户" align="center" prop="withdrawalAccount" width="280"/>
      <el-table-column label="出金金额汇总" align="center" prop="withdrawalAmountTotal" width="180">
        <template slot-scope="scope">
          <span>{{
            getThousandNum(scope.row.withdrawalAmountTotal)
          }}</span>
        </template>
      </el-table-column>
      <el-table-column label="入金时间" align="center" prop="depositTime" width="180"/>
      <el-table-column label="入金金额" align="center" prop="depositAmount">
        <template slot-scope="scope">
          <span>{{
            getThousandNum(scope.row.depositAmount)
          }}</span>
        </template>
      </el-table-column>
      <el-table-column label="入金账户" align="center" prop="depositAccount" width="280"/>
      <el-table-column label="入金运单号" align="center" prop="depositNumber" width="230"/>
      <el-table-column label="入金金额汇总" align="center" prop="depositAmountTotal" width="180">
        <template slot-scope="scope">
          <span>{{
            getThousandNum(scope.row.depositAmountTotal)
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
  import {getThousandNum} from '@/utils'

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
        this.$api.report.reportInfo('driver/secondary/information', this.queryParams).then((response) => {
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
        this.resetForm("queryForm");
        this.handleQuery();
      },
      handleExport() {
        this.downloadJson('/report/excel/driver/secondary/information', {
          ...this.queryParams
        }, `${new Date().getTime()}.xlsx`)
      },
    },
  };
</script>
