<!--3.客户二级户信息统计表-->
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
      <el-table-column type="index" label="序号" align="center" />
      <el-table-column label="所属业务机构" align="center" prop="businessAgency"/>
      <el-table-column label="二级户名称" align="center" prop="secondaryAccountName"/>
      <el-table-column label="二级户账号" align="center" prop="secondaryAccountNo"/>
      <el-table-column label="入金金额" align="center" prop="depositAmount"/>
      <el-table-column label="出金金额" align="center" prop="withdrawalAmount"/>
      <el-table-column label="账户余额" align="center" prop="accountBalance"/>
      <el-table-column label="退款金额" align="center" prop="refundAmount"/>
      <el-table-column label="运单数" align="center" prop="waybillCount"/>
      <el-table-column label="托运结算金额" align="center" prop="shipperSettlementAmount"/>
      <el-table-column label="承运结算金额" align="center" prop="carrierSettlementAmount"/>
      <el-table-column label="退款类型" align="center" prop="refundType"/>
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
