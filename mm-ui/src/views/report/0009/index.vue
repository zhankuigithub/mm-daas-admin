<!--开票明细表-->
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
      <el-form-item prop="settleTime1" label="时间">
        <el-date-picker
          v-model="queryParams.settleTime1"
          type="month"
          format="yyyy-MM"
          value-format="yyyy-MM"
          placeholder="开始"
        ></el-date-picker>
      </el-form-item>

      <el-form-item prop="settleTime2">
        <el-date-picker
          v-model="queryParams.settleTime2"
          type="month"
          format="yyyy-MM"
          value-format="yyyy-MM"
          placeholder="结束"
        ></el-date-picker>
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
      <el-table-column type="index" label="序号" align="center"/>
      <el-table-column label="合作编码" align="center" prop="cooperationCode"/>
      <el-table-column label="部门" align="center" prop="department"/>
      <el-table-column label="开票年月" align="center" prop="billingYearMonth"/>
      <el-table-column label="日" align="center" prop="day"/>
      <el-table-column label="客户名称" align="center" prop="customerName"/>
      <el-table-column label="开票批次号" align="center" prop="billingBatchNo"/>
      <el-table-column label="平台运费" align="center" prop="platformFreight"/>
      <el-table-column label="司机运费" align="center" prop="driverFreight"/>
      <el-table-column label="运费差" align="center" prop="freightDifference"/>
      <el-table-column label="对客报价" align="center" prop="quotedPrice"/>
      <el-table-column label="代理报价" align="center" prop="agencyQuote"/>
      <el-table-column label="平台留存" align="center" prop="platformRetention"/>
      <el-table-column label="服务费" align="center" prop="serviceFee"/>

      <el-table-column label="服务结算情况" align="center">
        <el-table-column label="结算日期" align="center" prop="taxIncludedIncome"></el-table-column>
        <el-table-column label="已结服务" align="center" prop="taxIncludedIncome"></el-table-column>
        <el-table-column label="未结服务" align="center" prop="taxIncludedIncome"></el-table-column>
      </el-table-column>
      <el-table-column label="结算单" align="center" prop="settlementBill"/>
      <el-table-column label="司机成本测1" align="center" prop="driverCostMeasurement1"/>
      <el-table-column label="司机成本测2" align="center" prop="driverCostMeasurement2"/>
      <el-table-column label="服务商名称" align="center" prop="serviceProviderName"/>
      <el-table-column label="确认开票" align="center" prop="confirmBilling"/>
      <el-table-column label="备注" align="center" prop="remarks"/>
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
        this.$api.report.reportInfo('nmjt/stat/0009', this.queryParams).then((response) => {
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
        this.handleQuery()
      },
    },
  };
</script>
