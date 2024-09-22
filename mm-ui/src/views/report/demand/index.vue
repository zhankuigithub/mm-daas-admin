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
      <el-table-column label="部门" align="center" prop="officeName"/>
      <el-table-column label="司机名称" align="center" prop="name"/>
      <el-table-column label="身份证后六位" align="center" prop="idcardNo"/>
      <el-table-column label="时间(年度月份）" align="center" prop="settleTime"/>
      <el-table-column label="系统成立前运费金额" align="center" prop="carrier4payed4amt">
        <template slot-scope="scope">
          <span>{{
            getThousandNum(scope.row.carrier4payed4amt)
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
          appName: null,
          appAccount: null,
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
        this.$api.report.reportInfo('demand/cost/statement', this.queryParams).then((response) => {
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
        this.queryParams.curPagerNo = 1;
      },
    },
  };
</script>
