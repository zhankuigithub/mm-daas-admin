<!--托运人托运信息统计表-->
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
      <el-table-column label="业务归属部门" align="center" prop="businessDepartment"/>
      <el-table-column label="托运人名称" align="center" prop="shipperName"/>
      <el-table-column label="线路" align="center" prop="route"/>
      <el-table-column label="常用车型" align="center" prop="commonVehicleType"/>
      <el-table-column label="托运货品名称" align="center" prop="cargoName"/>
      <el-table-column label="平均计量单位（吨、车）" align="center" prop="averageUnit"/>
      <el-table-column label="平均运距" align="center" prop="averageDistance"/>
      <el-table-column label="平均运价" align="center" prop="averageFreight"/>
      <el-table-column label="运输周期" align="center" prop="transportCycle"/>
      <el-table-column label="运输时间" align="center" prop="transportTime"/>
      <el-table-column label="货单起止时间" align="center" prop="cargoOrderPeriod"/>
      <el-table-column label="运单起止时间" align="center" prop="waybillPeriod"/>
      <el-table-column label="发布货单频次（月度）" align="center" prop="cargoOrderFrequencyMonthly"/>
      <el-table-column label="发布货单频次（季度）" align="center" prop="cargoOrderFrequencyQuarterly"/>
      <el-table-column label="发布货单频次（年度）" align="center" prop="cargoOrderFrequencyAnnually"/>
      <el-table-column label="应收账款金额" align="center" prop="receivablesAmount"/>
      <el-table-column label="应收账款账期" align="center" prop="receivablesPeriod"/>
      <el-table-column label="应收账款回款周期" align="center" prop="receivablesReturnCycle"/>
      <el-table-column label="结算时间（双签后多长时间结算）" align="center" prop="settlementTime"/>
      <el-table-column label="结算频率（月度）" align="center" prop="settlementFrequencyMonthly"/>
      <el-table-column label="开票时间（双签后多长时间开票）" align="center" prop="billingTime"/>
      <el-table-column label="开票频率（月度）" align="center" prop="billingFrequencyMonthly"/>
      <el-table-column label="开票频率（季度）" align="center" prop="billingFrequencyQuarterly"/>
      <el-table-column label="开票频率（年度）" align="center" prop="billingFrequencyAnnually"/>
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
