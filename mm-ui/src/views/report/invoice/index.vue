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
      <el-form-item prop="orderNo" label="运单号">
        <el-input
          v-model.trim="queryParams.orderNo"
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
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
        >重置
        </el-button
        >
      </el-form-item>
    </el-form>


    <el-table ref="tables" v-loading="loading" :data="list" size="mini" border>
      <el-table-column type="index" label="序号" align="center" />
      <el-table-column label="业务归属部门" align="center" prop="officeName"  width="100"/>
      <el-table-column label="服务商名称" align="center" prop="serviceProvider"  width="200"/>
      <el-table-column label="托运人名称" align="center" prop="enterpriseName" width="230"/>
      <el-table-column label="开票批次号" align="center" prop="receiptBatchNo" width="230"/>
      <el-table-column label="发票号码" align="center" prop="invoiceNo" width="230"/>
      <el-table-column label="开票日期" align="center" prop="receiptConfirmDate" width="230"/>
      <el-table-column label="开票结算金额" align="center" prop="sumShipper4ticket4amt"  width="100">
        <template slot-scope="scope">
          <span>{{
            getThousandNum(scope.row.sumShipper4ticket4amt)
          }}</span>
        </template>
      </el-table-column>
      <el-table-column label="运单号码" align="center" prop="orderNo" width="230"/>
      <el-table-column label="运单双签时间" align="center" prop="signTime" width="230"/>
      <el-table-column label="运单结算金额" align="center" prop="shipper4payed4amt"  width="100">
        <template slot-scope="scope">
          <span>{{
            getThousandNum(scope.row.shipper4payed4amt)
          }}</span>
        </template>
      </el-table-column>
      <el-table-column label="承运人姓名" align="center" prop="name"  width="100"/>
      <el-table-column label="承运人身份证号" align="center" prop="idcardNo" width="230"/>
      <el-table-column label="支付司机金额" align="center" prop="carrier4payed4amt"  width="100">
        <template slot-scope="scope">
          <span>{{
            getThousandNum(scope.row.carrier4payed4amt)
          }}</span>
        </template>
      </el-table-column>

      <el-table-column label="运单双签时间月份" align="center" prop="signMonth" width="140"/>
      <el-table-column label="开票金额双签月份汇总" align="center" prop="sumShipper4ticket4amtTotal" width="220">
        <template slot-scope="scope">
          <span>{{
            getThousandNum(scope.row.sumShipper4ticket4amtTotal)
          }}</span>
        </template>
      </el-table-column>
      <el-table-column label="运单结算金额双签汇总" align="center" prop="shipper4payed4amtTotal" width="220">
        <template slot-scope="scope">
          <span>{{
            getThousandNum(scope.row.shipper4payed4amtTotal)
          }}</span>
        </template>
      </el-table-column>
      <el-table-column label="支付司机金额双签月份汇总" align="center" prop="carrier4payed4amtTotal" width="220">
        <template slot-scope="scope">
          <span>{{
            getThousandNum(scope.row.carrier4payed4amtTotal)
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
        this.$api.report.reportInfo('invoice/state/statistics', this.queryParams).then((response) => {
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
