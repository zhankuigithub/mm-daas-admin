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
      <el-form-item label="接口名称" prop="apiName">
        <el-input
          v-model.trim="queryParams.apiName"
          placeholder="请输入接口名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
<!--      <el-form-item label="业务类型" prop="apiPath">-->
<!--        <el-select-->
<!--          clearable-->
<!--          v-model="queryParams.categoryId"-->
<!--          class="form-input"-->
<!--          placeholder="请选择业务类型"-->
<!--        >-->
<!--          <el-option-->
<!--            v-for="(item, index) of categoryList"-->
<!--            :key="item.id"-->
<!--            :label="item.categoryName"-->
<!--            :value="item.id"-->
<!--            >{{ item.categoryName }}-->
<!--          </el-option>-->
<!--        </el-select>-->
<!--      </el-form-item>-->
<!--      <el-form-item label="请求方法" prop="reqMethod">-->
<!--        <el-select-->
<!--          clearable-->
<!--          v-model="queryParams.reqMethod"-->
<!--          class="form-input"-->
<!--          placeholder="请输入请求方法"-->
<!--        >-->
<!--          <el-option-->
<!--            v-for="(item, index) of reqMethods"-->
<!--            :key="index"-->
<!--            :label="item.label"-->
<!--            :value="item.value"-->
<!--            >{{ item.label }}-->
<!--          </el-option>-->
<!--        </el-select>-->
<!--      </el-form-item>-->

<!--      <el-form-item label="类型" prop="reqMethod">-->
<!--        <el-select-->
<!--          clearable-->
<!--          v-model="queryParams.apiType"-->
<!--          class="form-input"-->
<!--          placeholder="请输入请求方法"-->
<!--        >-->
<!--          <el-option-->
<!--            v-for="(item, index) of apiTypes"-->
<!--            :key="index"-->
<!--            :label="item.label"-->
<!--            :value="item.value"-->
<!--            >{{ item.label }}-->
<!--          </el-option>-->
<!--        </el-select>-->
<!--      </el-form-item>-->

<!--      <el-form-item label="数据服务" prop="reqMethod">-->
<!--        <el-select-->
<!--          clearable-->
<!--          v-model="queryParams.relServiceId"-->
<!--          class="form-input"-->
<!--          placeholder="请选择数据服务"-->
<!--        >-->
<!--          <el-option-->
<!--            v-for="(item, index) of relServices"-->
<!--            :key="index"-->
<!--            :label="item.serviceName"-->
<!--            :value="item.id"-->
<!--            >{{ item.serviceName }}</el-option-->
<!--          >-->
<!--        </el-select>-->
<!--      </el-form-item>-->
      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索
        </el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
          >重置
        </el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          >新增
        </el-button>
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="baseList"
      @selection-change="handleSelectionChange"
      size="mini"
    >
<!--      <el-table-column type="expand" label="">-->
<!--        <template slot-scope="props">-->
<!--          <el-form label-position="left" class="demo-table-expand">-->
<!--            <el-form-item label="请求方法">-->
<!--              <span>{{-->
<!--                getGlobalDicLabel("reqMethods", props.row.reqMethod)-->
<!--              }}</span>-->
<!--            </el-form-item>-->

<!--            <el-form-item label="描述">-->
<!--              <span>{{ props.row.apiDescription }}</span>-->
<!--            </el-form-item>-->

<!--            <el-form-item label="主表名称">-->
<!--              <span>{{ props.row.mainTableName }}</span>-->
<!--            </el-form-item>-->

<!--            <el-form-item label="操作类型">-->
<!--              {{ getGlobalDicLabel("execTypes", props.row.execType) }}-->
<!--            </el-form-item>-->
<!--          </el-form>-->
<!--        </template>-->
<!--      </el-table-column>-->

      <el-table-column
        label="ID"
        align="left"
        prop="id"
        show-overflow-tooltip
        width="50"
      />

      <el-table-column
        label="接口名称"
        align="left"
        prop="apiName"
        show-overflow-tooltip
        width="220"
      />
      <el-table-column
        label="接口路径"
        align="left"
        prop="apiPath"
        show-overflow-tooltip
        width="330"
      >
        <template slot-scope="scope">
          {{ "/" + scope.row.apiPath }}
        </template>
      </el-table-column>

      <el-table-column
        label="返回类型"
        align="center"
        prop="respType"
        show-overflow-tooltip
      />

<!--      <el-table-column label="类型">-->
<!--        <template slot-scope="scope">-->
<!--          <span :class="scope.row.apiType === 1 ? 'font-3' : 'font-4'">{{-->
<!--            getGlobalDicLabel("apiTypes", scope.row.apiType)-->
<!--          }}</span>-->
<!--        </template>-->
<!--      </el-table-column>-->

      <el-table-column label="驼峰返回">
        <template slot-scope="scope">
          <span :class="scope.row.openHump === 0 ? 'font-1' : 'font-2'">{{
            getGlobalDicLabel("openHumps", scope.row.openHump)
          }}</span>
        </template>
      </el-table-column>

<!--      <el-table-column-->
<!--        label="数据服务"-->
<!--        align="left"-->
<!--        prop="serviceId"-->
<!--        show-overflow-tooltip-->
<!--      >-->
<!--        <template slot-scope="scope">-->
<!--          {{ serviceMap.get(scope.row.serviceId) }}-->
<!--        </template>-->
<!--      </el-table-column>-->
      <el-table-column
        label="数据源名称"
        align="left"
        prop="dataSourceId"
        show-overflow-tooltip
      >
        <template slot-scope="scope">
          {{ dataSourceMap.get(scope.row.dataSourceId) }}
        </template>
      </el-table-column>
<!--      <el-table-column-->
<!--        label="业务类型"-->
<!--        align="center"-->
<!--        prop="apiPath"-->
<!--        show-overflow-tooltip-->
<!--      >-->
<!--        <template slot-scope="scope">-->
<!--          {{ categoryMap.get(scope.row.categoryId) }}-->
<!--        </template>-->
<!--      </el-table-column>-->
      <el-table-column
        label="操作"
        align="center"
        width="250"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            @click="handleShow(scope.row)"-->
<!--            >查看-->
<!--          </el-button>-->
<!--          <el-button-->
<!--            v-if="scope.row.doc !== null"-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            @click="handleUpdateDoc(scope.row)"-->
<!--            v-hasPermi="['interface:edit']"-->
<!--            >编辑文档-->
<!--          </el-button>-->
<!--          <el-button-->
<!--            v-if="scope.row.doc === null"-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            @click="handleUpdateDoc(scope.row)"-->
<!--            v-hasPermi="['interface:edit']"-->
<!--            >创建文档-->
<!--          </el-button>-->
          <el-button
            size="mini"
            type="text"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['interface:edit']"
            >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            @click="handleDelete(scope.row)"
            v-hasPermi="['interface:del']"
            >删除
          </el-button>

          <el-button size="mini" type="text" @click="loadLog(scope.row)"
            >发布日志
          </el-button>
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

    <ModifyDialog
      v-if="open"
      :open="open"
      :op-type="opType"
      :record="editRecord"
      :dataSourceList="dataSourceList"
      :categoryList="categoryList"
      @close="closeDialog"
      @refreshTable="getList"
    ></ModifyDialog>

    <LogDialog
      v-if="openLog"
      :openLog="openLog"
      :apiId="apiId"
      @close="closeDialog"
    />
  </div>
</template>

<script>
import { listApi, delApi } from "@/api/interface/api";
import { globalStaticVariable } from "@/config";
import ModifyDialog from "./components/ModifyDialog";
import LogDialog from "./components/LogDialog";

export default {
  name: "api",
  components: { ModifyDialog, LogDialog },
  data() {
    return {
      editRecord: {},
      reqMethods: globalStaticVariable["reqMethods"],
      fieldTypes: globalStaticVariable["fieldTypes"],
      execTypes: globalStaticVariable["execTypes"],
      openHumps: globalStaticVariable["openHumps"],
      apiTypes: globalStaticVariable["apiTypes"],
      // 遮罩层
      opType: "",
      loading: true,
      // 选中数组
      ids: [],
      categoryMap: {},
      serviceMap: {},
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 接口信息表格数据
      baseList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      openLog: false,
      apiId: null,
      // 查询参数
      queryParams: {
        curPagerNo: 1,
        pageSize: 10,
        apiName: null,
        apiPath: null,
        reqMethod: null,
        respType: null,
        apiDescription: null,
        templateSql: null,
        apiStatus: null,
        dataSourceId: null,
        mainTableName: null,
        sourceTableName: null,
        categoryId: null,
        auth: null,
        execType: null,
        tally: null,
        deleted: null,
      },
      dataSourceList: [],
      relServices: [],
      categoryList: [],
    };
  },
  created() {
    this.init();
  },
  methods: {
    checkUrlFormat(rule, value, callback) {
      // 除必填外，判断是否包含两个连续的//或以/开头
      const regex = /^(?!.*\/\/)(?!\/).*$/;
      if (value && !regex.test(value)) {
        callback(new Error('URL中不能包含连续的两个斜杠 "//"'));
      } else {
        callback();
      }
    },
    watchSql(val) {
      this.form.templateSql = val;
    },
    /** 查询接口信息列表 */
    getList() {
      this.loading = true;
      listApi(this.queryParams).then((response) => {
        this.baseList = response.result.page.list;
        this.total = response.result.page.rowsCount;
        this.loading = false;
      });
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.curPagerNo = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.open = true;
      this.opType = "ADD";
      this.editRecord = {};
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.open = true;
      this.opType = "EDIT";
      this.editRecord = row;
    },
    /** 修改按钮操作 */
    handleShow(row) {
      this.open = true;
      this.opType = "SHOW";
      this.editRecord = row;
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal
        .confirm('是否确认删除接口信息编号为"' + ids + '"的数据项？')
        .then(function () {
          return delApi(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    loadLog(row) {
      this.openLog = true;
      this.apiId = row.id;
    },
    init() {
      this.$api.dataSourceInfo.list({}).then((res) => {
        if (res.code === 200) {
          this.dataSourceList = res.result;

          let map = new Map();
          this.dataSourceList.forEach((a) => {
            map.set(a.id, a.dataSourceName);
          });
          this.dataSourceMap = map;
        }
      });
      this.$api.application.serviceList().then((res) => {
        if (res.code === 200) {
          this.relServices = res.result;

          let map = new Map();
          this.relServices.forEach((a) => {
            map.set(a.id, a.serviceName);
          });
          this.serviceMap = map;
        }
      });

      this.$api.category.list({}).then((res) => {
        if (res.code === 200) {
          this.categoryList = res.result;
          let map = new Map();
          this.categoryList.forEach((a) => {
            map.set(a.id, a.categoryName);
          });
          this.categoryMap = map;
        }
      });
      this.getList();
    },
    handleUpdateDoc(row) {
      this.$router.push({
        path: "doc/edit",
        query: { id: row.id },
      });
    },
    closeDialog() {
      this.open = false;
      this.openLog = false;
    },
  },
};
</script>

<style>
.demo-table-expand {
  font-size: 0;
}
.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
}
.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}
.font-1 {
  color: #fa8072;
  font-weight: bold;
}
.font-2 {
  color: #4169e1;
  font-weight: bold;
}
.font-3 {
  color: #010101;
  font-weight: bold;
}
.font-4 {
  color: #e10d1f;
  font-weight: bold;
}
</style>
