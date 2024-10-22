<!--货主司机数据-->
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
      <el-table-column label="货源单号" align="center" prop="sourceOrderNo"/>
      <el-table-column label="运单号码" align="center" prop="waybillNo"/>
      <el-table-column label="开票批次号" align="center" prop="billingBatchNo"/>
      <el-table-column label="合作客户名称" align="center" prop="customerName"/>
      <el-table-column label="业务归属部门" align="center" prop="businessDepartment"/>
      <el-table-column label="是否已结算尾款" align="center" prop="isFinalPaymentSettled"/>
      <el-table-column label="司机姓名" align="center" prop="driverName"/>
      <el-table-column label="身份证号" align="center" prop="idCardNo"/>
      <el-table-column label="货物名称" align="center" prop="cargoName"/>
      <el-table-column label="车船吨位" align="center" prop="vehicleTonnage"/>
      <el-table-column label="车辆类型" align="center" prop="vehicleType"/>
      <el-table-column label="货物起送地" align="center" prop="originAddress"/>
      <el-table-column label="货物到达地" align="center" prop="destinationAddress"/>
      <el-table-column label="发票号码" align="center" prop="invoiceNo"/>
      <el-table-column label="司机运费" align="center" prop="driverFreight"/>
      <el-table-column label="不含税金额" align="center" prop="amountExclTax"/>
      <el-table-column label="开票税额" align="center" prop="taxAmount"/>
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
