<!--18.服务商信息统计表-->
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
      <el-table-column type="index" label="序号" align="center" />
      <el-table-column label="业务归属部门" align="center" prop="businessDepartment"/>
      <el-table-column label="托运人名称" align="center" prop="shipperName"/>
      <el-table-column label="业务机构名称" align="center" prop="businessAgencyName"/>
      <el-table-column label="所服务客户月均营业收入" align="center" prop="averageMonthlyRevenue"/>
      <el-table-column label="开票总额" align="center" prop="totalBillingAmount"/>
      <el-table-column label="应结服务费" align="center" prop="payableServiceFee"/>
      <el-table-column label="已结服务费" align="center" prop="settledServiceFee"/>
      <el-table-column label="待结服务费" align="center" prop="unsettledServiceFee"/>
      <el-table-column label="服务商结算频率" align="center" prop="serviceProviderSettlementFrequency"/>
      <el-table-column label="所服务客户应收账款金额" align="center" prop="customerReceivablesAmount"/>
      <el-table-column label="所服务客户应收账款账期" align="center" prop="customerReceivablesPeriod"/>
      <el-table-column label="所服务客户回款情况" align="center" prop="customerPaymentStatus"/>
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
