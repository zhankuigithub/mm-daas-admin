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
      <el-table-column label="业务归属部门" align="center" prop="internalDept"width="220"/>
      <el-table-column label="托运人名称" align="center" prop="enterpriseName" width="220"/>
      <el-table-column label="运单号" align="center" prop="orderNo" width="220"/>
      <el-table-column label="运单双签时间" align="center" prop="signTime" width="180"/>
      <el-table-column label="运单金额" align="center" prop="orderAmount">
        <template slot-scope="scope">
          <span>{{
            getThousandNum(scope.row.orderAmount)
          }}</span>
        </template>
      </el-table-column>
      <el-table-column label="承运人姓名" align="center" prop="carrierName" width="120"/>
      <el-table-column label="承运人身份证号" align="center" prop="carrierIdcard" width="180"/>
      <el-table-column label="支付承运人金额" align="center" width="180">
        <template slot-scope="scope">
          <span>{{
            getThousandNum(scope.row.payCarrierAmount)
          }}</span>
        </template>
      </el-table-column>
      <el-table-column label="支付承运人时间" align="center" prop="payCarrierTime" width="220"/>
      <el-table-column label="运单状态（取消/关闭）" align="center" width="200">
        <template slot-scope="scope">
          <span>{{
            getGlobalDicLabel("orderStatusList", scope.row.orderStatus)
          }}</span>
        </template>
      </el-table-column>

      <el-table-column label="运单退款金额" align="center" width="120">
        <template slot-scope="scope">
          <span>{{
            getThousandNum(scope.row.orderRefundAmount)
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
      };
    },
    created() {
      this.getList();
    },
    methods: {
      getThousandNum,
      getList() {
        this.loading = true;
        this.$api.report.reportInfo('order/cancel/statistics', this.queryParams).then((response) => {
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
