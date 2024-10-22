<!--档案表-->
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
      <el-table-column label="企业名称" align="center" prop="enterpriseName"/>
      <el-table-column label="合作编号" align="center" prop="cooperationNo"/>
      <el-table-column label="合作时间" align="center" prop="cooperationTime"/>
      <el-table-column label="所各区域" align="center" prop="allAreas"/>
      <el-table-column label="代理区域" align="center" prop="agencyArea"/>
      <el-table-column label="对客报价" align="center" prop="quotedPrice">
        <template slot-scope="scope">
          <span>{{
            getThousandNum(scope.row.quotedPrice)
          }}</span>
        </template>
      </el-table-column>
      <el-table-column label="实际结算价" align="center" prop="settlementPrice"/>
      <el-table-column label="差" align="center" prop="difference"/>
      <el-table-column label="业务人员" align="center" prop="businessPerson"/>
      <el-table-column label="发票种类" align="center" prop="invoiceType"/>
      <el-table-column label="服务商名称" align="center" prop="serviceProviderName"/>
      <el-table-column label="二级服务商" align="center" prop="secondaryServiceProvider"/>
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
        this.$api.report.reportInfo('nmjt/stat/0008', this.queryParams).then((response) => {
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
