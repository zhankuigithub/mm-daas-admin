<template>
  <el-dialog
    :title="getTitle"
    append-to-body
    :visible.sync="open"
    :close-on-click-modal="false"
    :modal-append-to-body="false"
    v-if="open"
    :before-close="cancel"
    custom-class="dialogWrap"
    width="70%"
  >
    <el-form
      ref="form"
      size="mini"
      :model="form"
      label-width="auto"
      :rules="rules"
    >
      <el-form-item label="接口名称" prop="apiName">
        <el-input v-model.trim="form.apiName" placeholder="请输入接口名称" />
      </el-form-item>
      <el-form-item label="接口路径" prop="apiPath">
        <el-input v-model.trim="form.apiPath" placeholder="请输入接口路径">
          <template slot="prepend">/</template>
        </el-input>
      </el-form-item>
      <el-form-item label="请求方法" prop="reqMethod" v-show="false">
        <el-select
          clearable
          v-model="form.reqMethod"
          class="form-input"
          placeholder="请输入请求方法"
        >
          <el-option
            v-for="(item, index) of reqMethods"
            :key="index"
            :label="item.label"
            :value="item.value"
            >{{ item.label }}
          </el-option>
        </el-select>
      </el-form-item>

<!--      <el-form-item label="接口类型" prop="apiType">-->
<!--        <el-radio-->
<!--          :disabled="opType === 'EDIT'"-->
<!--          v-model="form.apiType"-->
<!--          v-for="option in apiTypes"-->
<!--          :key="option.value"-->
<!--          :label="option.value"-->
<!--          >{{ option.label }}-->
<!--        </el-radio>-->
<!--      </el-form-item>-->

      <div v-show="form.apiType === 2">
        <el-row>
          <el-col :span="6">
            <el-form-item label="接口目录" prop="">
              <el-select v-model="selectedCategory" @change="handleApiChange">
                <el-option
                  v-for="item in apiCategory"
                  :key="item.id"
                  :label="item.categoryName"
                  :value="item.id"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="选择接口" prop="apiIds">
              <el-select
                v-model="form.apiIds"
                placeholder="请绑定接口"
                clearable
                multiple
                collapse-tags
              >
                <el-option
                  v-for="item in apis"
                  :key="item.id"
                  :label="item.apiName"
                  :value="item.id"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="组合方式" prop="connectType">
          <el-select
            clearable
            v-model="form.connectType"
            class="form-input"
            placeholder="请输入组合方式"
          >
            <el-option
              v-for="(item, index) of connectTypes"
              :key="index"
              :label="item.label"
              :value="item.value"
              >{{ item.label }}
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item
          label="组合keys"
          prop="joinKeys"
          v-if="form.connectType === 'join'"
        >
          <el-input v-model.trim="form.joinKeys" placeholder="请输入组合keys" />
        </el-form-item>
      </div>
        <el-form-item label="数据格式" prop="respType">
          <el-select
            clearable
            v-model="form.respType"
            class="form-input"
            placeholder="选择接口响应数据格式"
          >
            <el-option
              v-for="(item, index) of responseTypes"
              :key="index"
              :label="item.label"
              :value="item.value"
              >{{ item.label }}</el-option
            >
          </el-select>
        </el-form-item>

        <el-form-item label="驼峰返回" prop="openHump">
          <el-select
            clearable
            v-model="form.openHump"
            class="form-input"
            placeholder="驼峰返回"
          >
            <el-option
              v-for="(item, index) of openHumps"
              :key="index"
              :label="item.label"
              :value="item.value"
              >{{ item.label }}
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="接口描述" prop="apiDescription">
          <el-input
            v-model.trim="form.apiDescription"
            placeholder="请输入描述"
          />
        </el-form-item>
        <el-form-item label="执行语句" prop="templateSql">
          <MonacoEditor
            :openMiniMap="false"
            :codes="form.templateSql"
            id="id2"
            width="90%"
            height="50vh"
            @change="watchSql"
          ></MonacoEditor>
        </el-form-item>

        <el-form-item label="防转义字符" prop="forbidTransferKeys">
          <el-input
            v-model.trim="form.forbidTransferKeys"
            placeholder="请输入防转义字符"
          />
        </el-form-item>

        <el-form-item label="变更备注" prop="remark" v-if="opType === 'EDIT'">
          <el-input v-model.trim="form.remark" placeholder="请输入变更备注" />
        </el-form-item>

        <el-form-item label="数据源" prop="dataSourceId">
          <el-select
            clearable
            v-model="form.dataSourceId"
            class="form-input"
            placeholder="请选择数据源"
          >
            <el-option
              v-for="(item, index) of dataSourceList"
              :key="item.id"
              :label="item.dataSourceName + '/' + item.dbIp"
              :value="item.id"
              >{{ item.dataSourceName + "/" + item.dbIp }}</el-option
            >
          </el-select>
        </el-form-item>
<!--        <el-form-item label="主表名称" prop="mainTableName">-->
<!--          <el-input-->
<!--            v-model.trim="form.mainTableName"-->
<!--            placeholder="请输入主表名称"-->
<!--          />-->
<!--        </el-form-item>-->
<!--        <el-form-item-->
<!--          label="数据源表名称"-->
<!--          prop="sourceTableName"-->
<!--          v-show="false"-->
<!--        >-->
<!--          <el-input-->
<!--            v-model.trim="form.sourceTableName"-->
<!--            placeholder="请输入数据源表名称"-->
<!--          />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="请求字段">-->
<!--          <el-table :data="form.apiParams" ref="tb" size="mini" border>-->
<!--            <el-table-column align="left" width="75">-->
<!--              <template slot="header" slot-scope="scope">-->
<!--                <el-button-->
<!--                  @click="clickAddProblems(scope.row, 1)"-->
<!--                  icon="iconfont icon-add"-->
<!--                  type="text"-->
<!--                  size="mini"-->
<!--                  >添加-->
<!--                </el-button>-->
<!--              </template>-->
<!--              <template slot-scope="scope">-->
<!--                <el-button-->
<!--                  @click="clickRemoveProblems(scope.row, 1)"-->
<!--                  icon="iconfont icon-guanbi"-->
<!--                  type="text"-->
<!--                  size="mini"-->
<!--                >-->
<!--                  移除-->
<!--                </el-button>-->
<!--              </template>-->
<!--            </el-table-column>-->
<!--            <el-table-column-->
<!--              label="是否必填"-->
<!--              width="150"-->
<!--              align="center"-->
<!--              prop="ts"-->
<!--            >-->
<!--              <template slot-scope="scope">-->
<!--                <el-radio-group v-model="scope.row.required">-->
<!--                  <el-radio :label="1">是</el-radio>-->
<!--                  <el-radio :label="2">否</el-radio>-->
<!--                </el-radio-group>-->
<!--              </template>-->
<!--            </el-table-column>-->
<!--            <el-table-column label="字段名" align="center" prop="ts">-->
<!--              <template slot-scope="scope">-->
<!--                <el-input-->
<!--                  v-model.trim="scope.row.fieldName"-->
<!--                  placeholder="字段名称"-->
<!--                />-->
<!--              </template>-->
<!--            </el-table-column>-->
<!--            <el-table-column label="字段类型" align="center" prop="ts">-->
<!--              <template slot-scope="scope">-->
<!--                <el-select-->
<!--                  clearable-->
<!--                  v-model="scope.row.fieldType"-->
<!--                  class="form-input"-->
<!--                  placeholder="请选择字段类型"-->
<!--                >-->
<!--                  <el-option-->
<!--                    v-for="(item, index) of fieldTypes"-->
<!--                    :key="item.value"-->
<!--                    :label="item.value"-->
<!--                    :value="item.value"-->
<!--                    >{{ item.value }}</el-option-->
<!--                  >-->
<!--                </el-select>-->
<!--              </template>-->
<!--            </el-table-column>-->
<!--            <el-table-column label="字段说明" align="center" prop="ts">-->
<!--              <template slot-scope="scope">-->
<!--                <el-input-->
<!--                  v-model.trim="scope.row.remark"-->
<!--                  placeholder="字段说明"-->
<!--                />-->
<!--              </template>-->
<!--            </el-table-column>-->
<!--          </el-table>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="响应字段">-->
<!--          <el-table :data="form.apiReturns" ref="tb" size="mini" border>-->
<!--            <el-table-column align="left" width="75">-->
<!--              <template slot="header" slot-scope="scope">-->
<!--                <el-button-->
<!--                  @click="clickAddProblems(scope.row, 2)"-->
<!--                  icon="iconfont icon-add"-->
<!--                  type="text"-->
<!--                  size="mini"-->
<!--                  >添加-->
<!--                </el-button>-->
<!--              </template>-->
<!--              <template slot-scope="scope">-->
<!--                <el-button-->
<!--                  @click="clickRemoveProblems(scope.row, 2)"-->
<!--                  icon="iconfont icon-guanbi"-->
<!--                  type="text"-->
<!--                  size="mini"-->
<!--                >-->
<!--                  移除-->
<!--                </el-button>-->
<!--              </template>-->
<!--            </el-table-column>-->
<!--            <el-table-column-->
<!--              label="是否必填"-->
<!--              width="150"-->
<!--              align="center"-->
<!--              prop="ts"-->
<!--            >-->
<!--              <template slot-scope="scope">-->
<!--                <el-radio-group v-model="scope.row.required">-->
<!--                  <el-radio :label="1">是</el-radio>-->
<!--                  <el-radio :label="2">否</el-radio>-->
<!--                </el-radio-group>-->
<!--              </template>-->
<!--            </el-table-column>-->
<!--            <el-table-column label="字段名" align="center" prop="ts">-->
<!--              <template slot-scope="scope">-->
<!--                <el-input-->
<!--                  v-model.trim="scope.row.fieldName"-->
<!--                  placeholder="字段名称"-->
<!--                  @blur="underscoreToCamel(scope.row)"-->
<!--                />-->
<!--              </template>-->
<!--            </el-table-column>-->
<!--            <el-table-column label="字段类型" align="center" prop="ts">-->
<!--              <template slot-scope="scope">-->
<!--                <el-select-->
<!--                  clearable-->
<!--                  v-model="scope.row.fieldType"-->
<!--                  class="form-input"-->
<!--                  placeholder="请选择字段类型"-->
<!--                >-->
<!--                  <el-option-->
<!--                    v-for="(item, index) of fieldTypes"-->
<!--                    :key="item.value"-->
<!--                    :label="item.value"-->
<!--                    :value="item.value"-->
<!--                    >{{ item.value }}-->
<!--                  </el-option>-->
<!--                </el-select>-->
<!--              </template>-->
<!--            </el-table-column>-->
<!--            <el-table-column label="字段说明" align="center" prop="ts">-->
<!--              <template slot-scope="scope">-->
<!--                <el-input-->
<!--                  v-model.trim="scope.row.remark"-->
<!--                  placeholder="字段说明"-->
<!--                />-->
<!--              </template>-->
<!--            </el-table-column>-->
<!--          </el-table>-->
<!--        </el-form-item>-->
<!--      <el-form-item label="业务类型" prop="categoryId">-->
<!--        <el-select-->
<!--          clearable-->
<!--          v-model="form.categoryId"-->
<!--          class="form-input"-->
<!--          placeholder="请选择业务类型"-->
<!--        >-->
<!--          <el-option-->
<!--            v-for="(item, index) of categoryList"-->
<!--            :key="item.id"-->
<!--            :label="item.categoryName"-->
<!--            :value="item.id"-->
<!--            >{{ item.categoryName }}</el-option-->
<!--          >-->
<!--        </el-select>-->
<!--      </el-form-item>-->
    </el-form>

    <div slot="footer" class="dialog-footer">
      <el-button
        type="primary"
        size="mini"
        @click="submitForm"
        :disabled="opType === 'SHOW'"
        >确 定</el-button
      >
      <el-button size="mini" @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { handleCommonTrip, timeVerification } from "@/utils";
import { globalStaticVariable } from "@/config";
import { cloneDeep } from "lodash";
import MonacoEditor from "@/components/MonacoEditor";
import { getApi, addApi, updateApi } from "@/api/interface/api";
export default {
  name: "ModifyDialog",
  components: { MonacoEditor },
  props: {
    open: {
      required: true,
      type: Boolean,
      default: false,
    },
    opType: {
      required: true,
      type: String,
      default: null,
    },
    record: {
      required: true,
      type: Object,
      default: {},
    },
    dataSourceList: {
      required: true,
      type: Array,
      default: [],
    },
    categoryList: {
      required: true,
      type: Array,
      default: [],
    },
  },
  data() {
    return {
      reqMethods: globalStaticVariable["reqMethods"],
      fieldTypes: globalStaticVariable["fieldTypes"],
      execTypes: globalStaticVariable["execTypes"],
      openHumps: globalStaticVariable["openHumps"],
      apiTypes: globalStaticVariable["apiTypes"],
      connectTypes: globalStaticVariable["connectTypes"],
      responseTypes: globalStaticVariable["responseTypes"],
      oldTemplateSql: null,
      selectedCategory: null,
      apiCategory: [],
      apis: [],
      // 表单参数
      form: {
        respType: "list",
        openHump: 1,
        reqMethod: 2,
        apiDescription: "",
        connectType: "union",
        apiParams: [],
        apiReturns: [],
        apiType: 1,
        apiIds: [],
        keysTable: [],
        joinKeys: "",
        forbidTransferKeys: "",
      },
      // 表单校验
      rules: {
        apiName: [
          { required: true, message: "接口名称不能为空", trigger: "blur" },
        ],
        mainTableName: [
          { required: false, message: "主表名称名称不能为空", trigger: "blur" },
        ],
        apiPath: [
          { required: true, message: "接口路径不能为空", trigger: "blur" },
          { validator: this.checkUrlFormat, trigger: "blur" },
        ],
        apiDescription: [
          { required: false, message: "接口描述不能为空", trigger: "blur" },
        ],
        templateSql: [
          { required: false, message: "执行SQL不能为空", trigger: "blur" },
        ],
        reqMethod: [
          { required: false, message: "请求方法不能为空", trigger: "blur" },
        ],
        dataSourceId: [
          { required: false, message: "对应数据源id不能为空", trigger: "blur" },
        ],
        execType: [
          { required: false, message: "操作类型不能为空", trigger: "change" },
        ],
        respType: [
          { required: false, message: "返回类型不能为空", trigger: "change" },
        ],
        openHump: [
          { required: false, message: "选择驼峰返回", trigger: "change" },
        ],
        apiType: [
          { required: true, message: "选择接口类型", trigger: "change" },
        ],
        remark: [
          {
            validator: (rule, value, cb) => {
              if (
                this.opType === "EDIT" &&
                this.oldTemplateSql.trim() !== this.form.templateSql.trim() &&
                !value
              ) {
                cb(new Error("变更执行语句后必须填写变更备注"));
              } else {
                cb();
              }
            },
            trigger: "change",
          },
        ],
      },
    };
  },
  created() {
    if (this.opType === "EDIT" || this.opType === "SHOW") {
      this.form = cloneDeep(this.record);
      const id = this.form.id;
      this.oldTemplateSql = this.form.templateSql;
      getApi(id).then((response) => {
        this.form = response.result;
        if (!this.form.apiParams) {
          this.form.apiParams = [];
        }
        if (!this.form.apiReturns) {
          this.form.apiReturns = [];
        }
      });
      if (this.form.apiType === 2) {
        this.loadApiInfo();
      }
    }
  },
  methods: {
    watchSql(val) {
      this.form.templateSql = val;
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateApi(this.form).then((res) => {
              this.resolveResponse(res);
            });
          } else {
            addApi(this.form).then((res) => {
              this.resolveResponse(res);
            });
          }
        }
      });
    },
    cancel(isUpd) {
      this.$emit("close", isUpd);
      this.$refs.form.resetFields();
    },
    resolveResponse(res) {
      if (res.code === 200) {
        this.$modal.msgSuccess(res.message);
        this.cancel();
        this.$emit("refreshTable");
      }
    },
    initSelect() {
      this.$api.category.list().then((res) => {
        if (res.code === 200) {
          this.apiCategory = res.result;
        }
      });
    },
    handleApiChange() {
      this.form.apiIds = [];
      this.$api.doc
        .apiList({ categoryId: this.selectedCategory })
        .then((res) => {
          if (res.code === 200) {
            this.apis = res.result;
          }
        });
    },
    loadApiInfo() {
      getApi(this.form.apiIds[0]).then((res) => {
        if (res.code === 200) {
          this.selectedCategory = res.result.categoryId;
          this.$api.doc
            .apiList({ categoryId: this.selectedCategory })
            .then((res) => {
              if (res.code === 200) {
                this.apis = res.result;
              }
            });
        }
      });
    },
  },
  computed: {
    getTitle() {
      switch (this.opType) {
        case "ADD":
          return "添加";
        case "EDIT":
          return "编辑";
        case "SHOW":
          return "查看";
        default:
          return "未知操作";
      }
    },
  },
};
</script>

<style scoped>
.form-input {
  width: 100%;
}
</style>
