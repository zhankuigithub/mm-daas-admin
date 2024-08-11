<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="mini" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="数据库ip" prop="dbIp">
        <el-input
          v-model="queryParams.dbIp"
          placeholder="请输入数据库ip"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="数据库端口" prop="dbPort">
        <el-input
          v-model="queryParams.dbPort"
          placeholder="请输入数据库端口"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="账号" prop="dbUserName">
        <el-input
          v-model="queryParams.dbUserName"
          placeholder="请输入账号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="库名" prop="dbName">
        <el-input
          v-model="queryParams.dbName"
          placeholder="请输入库名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="数据源类型" prop="type">
        <el-select
          clearable
          v-model="queryParams.type"
          class="form-input"
          placeholder="请选择数据源类型"
        >
          <el-option
            v-for="(item, index) of dataSourceTypes"
            :key="index"
            :label="item.typeName"
            :value="item.typeName.toLowerCase()"
          >{{ item.typeName }}</el-option
          >
        </el-select>
      </el-form-item>
      <el-form-item label="最小连接数" prop="minSize">
        <el-input
          v-model="queryParams.minSize"
          placeholder="请输入最小连接数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="最大连接数" prop="maxSize">
        <el-input
          v-model="queryParams.maxSize"
          placeholder="请输入最大连接数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="初始化连接数" prop="initSize">
        <el-input
          v-model="queryParams.initSize"
          placeholder="请输入初始化连接数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
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
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="infoList" @selection-change="handleSelectionChange" size="mini" label-width="90px">
      <el-table-column type="expand" label="">
        <template slot-scope="props">
          <el-form label-position="left" class="demo-table-expand">

            <el-form-item label="数据源url">
              <span>{{ props.row.dbUrl }}</span>
            </el-form-item>

            <el-form-item label="驱动">
              <span>{{ props.row.dbDriver }}</span>
            </el-form-item>

            <el-form-item label="最小连接数">
              <span>{{ props.row.minSize }}</span>
            </el-form-item>
            <el-form-item label="最大连接数">
              <span>{{ props.row.maxSize }}</span>
            </el-form-item>
            <el-form-item label="初始化连接数">
              <span>{{ props.row.initSize }}</span>
            </el-form-item>
          </el-form>
        </template>

      </el-table-column>
      <el-table-column show-overflow-tooltip type="selection" align="center"/>
      <el-table-column show-overflow-tooltip label="数据源名称" align="left" prop="dataSourceName"/>
      <el-table-column show-overflow-tooltip label="库类型" align="center" prop="type"/>
      <el-table-column show-overflow-tooltip label="数据库ip" align="left" prop="dbIp">
        <template slot-scope="scope">
          {{scope.row.dbIp}}:{{scope.row.dbPort}}
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip label="账号" align="left" prop="dbUserName"/>
      <el-table-column show-overflow-tooltip label="库名" align="left" prop="dbName"/>
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.curPagerNo"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改数据源配置对话框 -->
    <el-dialog :close-on-click-modal="false" :title="title" :visible.sync="open" append-to-body>
      <el-form ref="form" size="mini" :model="form"  label-width="auto" :rules="rules" >
        <el-form-item label="数据库ip" prop="dbIp">
          <el-input v-model="form.dbIp" placeholder="请输入数据库ip"/>
        </el-form-item>
        <el-form-item label="数据源名称" prop="dataSourceName">
          <el-input v-model="form.dataSourceName" placeholder="请输入数据源名称"/>
        </el-form-item>
        <el-form-item label="数据库端口" prop="dbPort">
          <el-input v-model="form.dbPort" placeholder="请输入数据库端口"/>
        </el-form-item>
        <el-form-item label="账号" prop="dbUserName">
          <el-input v-model="form.dbUserName" placeholder="请输入账号"/>
        </el-form-item>
        <el-form-item label="密码" prop="dbPassword">
          <el-input v-model="tempPassword" placeholder="请输入内容"/>
        </el-form-item>
        <el-form-item label="库名" prop="dbName">
          <el-input v-model="form.dbName" placeholder="请输入库名"/>
        </el-form-item>
        <el-form-item label="数据源类型">
        <el-select placeholder="请选择数据源类型" v-model="dataSourceType" value-key="form.type">
          <el-option v-for="option in dataSourceTypes"
                     :key="option.typeName" :label="option.typeName" :value="JSON.stringify(option) ">
          </el-option>
        </el-select>
        </el-form-item>
        <el-form-item label="数据源url" prop="dbUrl">
          <el-input v-model="form.dbUrl" placeholder="请输入数据源url"/>
        </el-form-item>
        <el-form-item label="驱动" prop="dbDriver">
          <el-input v-model="form.dbDriver" placeholder="请输入驱动"/>
        </el-form-item>
        <el-form-item label="最小连接数" prop="minSize">
          <el-input v-model="form.minSize" placeholder="请输入最小连接数"/>
        </el-form-item>
        <el-form-item label="最大连接数" prop="maxSize">
          <el-input v-model="form.maxSize" placeholder="请输入最大连接数"/>
        </el-form-item>
        <el-form-item label="初始化连接数" prop="initSize">
          <el-input v-model="form.initSize" placeholder="请输入初始化连接数"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="testConnection" size="mini">测 试</el-button>
        <el-button type="primary" @click="submitForm" size="mini">确 定</el-button>
        <el-button @click="cancel" size="mini">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {listInfo, getInfo, delInfo, addInfo, updateInfo, dataTypes,test} from "@/api/modules/dataSourceInfo";
import { Encrypt } from '@/utils/rsa.js'

export default {
  name: "Info",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 数据源配置表格数据
      infoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      tempPassword:"",
      // 查询参数
      queryParams: {
        curPagerNo: 1,
        pageSize: 10,
        dbIp: null,
        dbPort: null,
        dbUserName: null,
        dbPassword: null,
        dbName: null,
        dbUrl: null,
        dbDriver: null,
        minSize: null,
        maxSize: null,
        initSize: null,
        type: null,
        logicDel: null,
      },
      dataSourceType: "",
      dataSourceTypes: [],
      // 表单参数
      form: {
        type:''
      },
      // 表单校验
      rules: {
        dataSourceName: [
          {required: true, message: "数据源名称不能为空", trigger: "blur"}
        ],
        dbIp: [
          {required: true, message: "数据库ip不能为空", trigger: "blur"}
        ],
        dbPort: [
          {required: true, message: "数据库端口不能为空", trigger: "blur"}
        ],
        dbUserName: [
          {required: true, message: "账号不能为空", trigger: "blur"}
        ],
        dbPassword: [
          {required: true, message: "密码不能为空", trigger: "blur"}
        ],
        dbName: [
          {required: true, message: "库名不能为空", trigger: "blur"}
        ],
        dbUrl: [
          {required: true, message: "数据源url不能为空", trigger: "blur"}
        ],
        dbDriver: [
          {required: true, message: "驱动不能为空", trigger: "blur"}
        ],
        type: [
          {required: true, message: "库类型不能为空", trigger: "change"}
        ],
        logicDel: [
          {required: true, message: "逻辑删除， 0. 正常、1. 删除不能为空", trigger: "blur"}
        ],
        createTime: [
          {required: true, message: "$comment不能为空", trigger: "blur"}
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getDataTypes();
  },
  watch: {
    form: {
      deep: true,
      handler() {
        if (this.form.dbIp === null || this.form.dbPort === null || this.form.dbName === null || this.dataSourceType.length === 0) {
          this.form.dbUrl = ``;
        } else {
          this.form.dbUrl = `jdbc:${this.form.type}://${this.form.dbIp}:${this.form.dbPort}/${this.form.dbName}?useUnicode=TRUE&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=TRUE&serverTimezone=GMT%2B8`
        }
      }
    },
    dataSourceType:{
      deep: true,
      handler() {
        if (this.dataSourceType.length !== 0) {
          this.form.type = JSON.parse(this.dataSourceType).typeName;
          this.form.dbDriver = JSON.parse(this.dataSourceType).driverClassName;
        }
      }
    },
    tempPassword:{
      deep: true,
      handler() {
      this.form.dbPassword = Encrypt(this.tempPassword);
      }
    }
  },
  methods: {
    /** 查询数据源配置列表 */
    getList() {
      this.loading = true;
      listInfo(this.queryParams).then(response => {
        this.infoList = response.result.page.list;
        this.total = response.result.page.rowsCount;
        this.loading = false;
      });
    },
    getDataTypes() {
      dataTypes().then(response => {
        this.dataSourceTypes = response.result;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.tempPassword="";
      this.dataSourceType= "";
      this.type='';
      this.form = {
        id: null,
        dataSourceName: null,
        dbIp: null,
        dbPort: null,
        dbUserName: null,
        dbPassword: null,
        dbName: null,
        dbUrl: null,
        dbDriver: null,
        minSize: 1,
        maxSize: 10,
        initSize: 10,
        type: null,
      };
      this.resetForm("form");
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
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加数据源配置";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getInfo(id).then(response => {
        this.form = response.result;
        this.dataSourceType=JSON.stringify(this.dataSourceTypes.find(item => item.typeName === this.form.type.toUpperCase()));
        this.open = true;
        this.title = "修改数据源配置";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateInfo(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addInfo(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    testConnection() {
      this.$refs["form"].validate(valid => {
        if (valid) {
            let req = {
            dbIp: this.form.dbIp,
            dbPort: this.form.dbPort,
            dbUserName: this.form.dbUserName,
            dbPassword: this.form.dbPassword,
            dbName: this.form.dbName,
            dbUrl: this.form.dbUrl,
            dbDriver: this.form.dbDriver,
            type:this.form.type
          }
            test(req).then(response => {
            if (response.result) {
              this.$modal.msgSuccess("链接成功");
            } else {
              this.$modal.msgError("链接失败，请检查链接信息");

            }
          });
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除数据源配置编号为"' + ids + '"的数据项？').then(function () {
        return delInfo(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    }
  }
};
</script>

