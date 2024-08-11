<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="mini" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="业务类型" prop="categoryName">
        <el-input
          v-model="queryParams.categoryName"
          placeholder="请输入业务类型"
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
        >新增</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="categoryList" @selection-change="handleSelectionChange" size="mini"  border>
      <el-table-column label="业务类型" align="left" prop="categoryName" />
      <el-table-column label="类型排序" align="center" prop="categorySort" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
          >删除</el-button>
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

    <!-- 添加或修改业务类型对话框 -->
    <el-dialog :close-on-click-modal="false" :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" size="mini" :model="form"  label-width="auto" :rules="rules" >
        <el-form-item label="业务类型" prop="categoryName">
          <el-input v-model="form.categoryName" placeholder="请输入业务类型" />
        </el-form-item>
        <el-form-item label="类型排序" prop="categorySort">
          <el-input v-model="form.categorySort" placeholder="请输入类型排序" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm" size="mini" >确 定</el-button>
        <el-button @click="cancel" size="mini" >取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listCategory, getCategory, delCategory, addCategory, updateCategory } from "@/api/modules/category";

export default {
  name: "Category",
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
      // 业务类型表格数据
      categoryList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        curPagerNo: 1,
        pageSize: 10,
        categoryName: null,
        categoryType: null,
        categorySort: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        categoryName: [
          { required: true, message: "业务类型不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询业务类型列表 */
    getList() {
      this.loading = true;
      listCategory(this.queryParams).then(response => {
        this.categoryList = response.result.page.list;
        this.total = response.result.page.rowsCount;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        categoryName: null,
        categoryType: null,
        categorySort: null,
        createTime: null,
        createBy: null,
        updateTime: null,
        updateBy: null
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
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加业务类型";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getCategory(id).then(response => {
        this.form = response.result;
        this.open = true;
        this.title = "修改业务类型";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateCategory(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCategory(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除业务类型编号为"' + ids + '"的数据项？').then(function() {
        return delCategory(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    }
  }
};
</script>
