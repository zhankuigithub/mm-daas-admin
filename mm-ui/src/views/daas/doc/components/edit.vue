<template>
  <div style="padding: 10px" v-if="load">
    <el-form
      ref="form"
      :model="form"
      :rules="rules"
      label-width="100px"
      size="mini"
      label-position="left"
    >
      <el-row>
        <el-col :span="22">
          <el-form-item v-show="false">
            <el-input v-model="form.id"></el-input>
          </el-form-item>

          <el-form-item v-show="false">
            <el-input v-model="form.interfaceId"></el-input>
          </el-form-item>

          <el-form-item label="接口名称">
            <el-input disabled v-model.trim="apiName" class="form-input" />
          </el-form-item>

          <el-form-item label="接口地址" prop="reqUrl">
            <el-input
              v-model.trim="form.reqUrl"
              placeholder="请输入接口地址"
              class="form-input"
            />
          </el-form-item>

          <el-form-item label="请求方式" prop="reqType">
            <el-select v-model="form.reqType" class="form-input">
              <el-option
                v-for="item in reqMethods"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="接口备注" prop="remark">
            <el-input
              v-model="form.remark"
              placeholder="接口备注"
              class="form-input"
            />
          </el-form-item>
          <el-form-item label="请求示例" prop="reqEx">
            <MonacoEditor
              :openMiniMap="false"
              :codes="form.reqEx"
              id="id3"
              language="java"
              width="80%"
              height="80vh"
              @change="watchEx"
            ></MonacoEditor>
          </el-form-item>
          <el-form-item label="请求参数说明">
            <el-table :data="reqTbParams" border style="width: 80%" size="mini">
              <el-table-column prop="fieldName" label="参数名称" width="150" />
              <el-table-column prop="fieldType" label="字段类型" width="90" />
              <el-table-column prop="required" label="是否必传" width="90">
                <template slot-scope="scope">
                  {{ scope.row.required === 1 ? "是" : "否" }}
                </template>
              </el-table-column>
              <el-table-column prop="remark" label="说明" />
            </el-table>
          </el-form-item>

          <el-form-item label="请求参数示例" prop="reqParamEx">
            <MonacoEditor
              :openMiniMap="false"
              :codes="form.reqParamEx"
              id="id1"
              language="json"
              width="80%"
              height="40vh"
              @change="watchReq"
            ></MonacoEditor>
          </el-form-item>
          <el-form-item label="返回参数示例" prop="resParamEx">
            <MonacoEditor
              :openMiniMap="false"
              :codes="form.resParamEx"
              id="id2"
              language="json"
              width="80%"
              height="40vh"
              @change="watchRes"
            ></MonacoEditor>
          </el-form-item>

          <el-form-item label="返回参数说明">
            <el-table :data="resTbParams" border style="width: 50%" size="mini">
              <el-table-column prop="fieldName" label="参数名称" width="150" />
              <el-table-column prop="fieldType" label="字段类型" width="90" />
              <el-table-column prop="required" label="是否必传" width="90">
                <template slot-scope="scope">
                  {{ scope.row.required === 1 ? "是" : "否" }}
                </template>
              </el-table-column>
              <el-table-column prop="remark" label="说明" />
            </el-table>
          </el-form-item>
        </el-col>
        <el-col :span="2">
          <el-button size="mini" type="primary" @click="submitForm"
            >确 定</el-button
          >
          <el-button size="mini" @click="cancel">取 消</el-button>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script>
import { handleCommonTrip } from "@/utils";
import { globalStaticVariable } from "@/config";
import MonacoEditor from "@/components/MonacoEditor/index";
import { getApi } from "@/api/interface/api";

export default {
  components: { MonacoEditor },
  name: "ModifyDialog",
  props: {},
  data() {
    return {
      apiName: null,
      load: false,
      reqTbParams: [],
      resTbParams: [],
      checkStatus: false,
      loading: false,
      form: {
        interfaceId: 0,
        reqType: 2,
        reqEx: "{}",
        reqParamEx: "{}",
        resParamEx: "{}",
      },
      reqMethods: globalStaticVariable["reqMethods"],
      rules: {
        interfaceId: [
          { required: true, message: "请选择接口", trigger: "change" },
        ],
        reqUrl: [
          { required: true, message: "请输入接口地址", trigger: "blur" },
        ],
        reqType: [
          { required: true, message: "请选择请求方式", trigger: "change" },
        ],
        reqEx: [{ required: true, message: "请输入请求示例", trigger: "blur" }],
        remark: [
          { required: true, message: "请填写接口备注", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    // 加载req、res参数
    let id = this.$route.query.id;
    // 获取接口信息
    this.selectChange(id);
  },
  methods: {
    resolveResponse(res) {
      if (res.code === 200) {
        handleCommonTrip("success", res.message);
        this.cancel();
        this.$emit("refreshTable");
      }
    },
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id !== undefined) {
            this.$api.doc.modify(this.form).then((res) => {
              this.resolveResponse(res);
            });
          } else {
            this.$api.doc.create(this.form).then((res) => {
              this.resolveResponse(res);
            });
          }
        }
      });
    },
    cancel() {
      this.$router.push({ path: "/daas/interface" });
    },
    watchEx(val) {
      this.form.reqEx = val;
    },
    watchReq(val) {
      this.form.reqParamEx = val;
    },
    watchRes(val) {
      this.form.resParamEx = val;
    },
    selectChange(val) {
      getApi(val).then((res) => {
        if (res.code === 200) {
          this.reqTbParams = res.result.apiParams;
          this.resTbParams = res.result.apiReturns;
          this.apiName = res.result.apiName;
          if (res.result.doc) {
            this.form = res.result.doc;
          } else {
            this.form.interfaceId = val;
            this.form.remark = res.result.apiDescription;
            this.form.reqUrl = `http://kc-data-daas/daas/outer/query/${res.result.apiPath}`;
          }
          this.reqTbParams = res.result.apiParams;
          this.resTbParams = res.result.apiReturns;
          this.load = true;
        }
      });
    },
  },
};
</script>

<style scoped>
.form-input {
  width: 80%;
}
</style>
