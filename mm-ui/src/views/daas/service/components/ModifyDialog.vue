<template>
  <el-dialog
    :title="opType === 'ADD' ? '添加平台服务' : '编辑平台服务'"
    append-to-body
    width="30%"
    :visible.sync="open"
    :close-on-click-modal="false"
    :modal-append-to-body="false"
    v-if="open"
    :before-close="cancel"
    custom-class="dialogWrap"
  >
    <el-form
      ref="form"
      size="mini"
      :model="form"
      label-width="110px"
      :rules="rules"
      label-position="left"
    >
      <el-form-item label="服务名称" prop="serviceName">
        <el-input
          v-model.trim="form.serviceName"
          placeholder="请输入服务名称"
        />
      </el-form-item>

      <el-row>
        <el-col :span="12">
          <el-form-item label="选择接口" prop="apiType">
            <el-select
              v-model="selectedApiType"
              @change="handleApiChange"
              :disabled="opType === 'EDIT'"
            >
              <el-option
                v-for="item in apiTypes"
                :key="item.id"
                :label="item.categoryName"
                :value="item.id"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item prop="apiIds">
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
                :disabled="
                  (opType === 'ADD' && item.serviceId !== null) ||
                  (opType === 'EDIT' &&
                    item.serviceId !== null &&
                    item.serviceId !== form.id)
                "
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-form-item label="服务类型" prop="serviceType">
        <el-radio
          v-model="form.serviceType"
          v-for="option in serviceTypes"
          :key="option.value"
          :label="option.value"
          >{{ option.label }}
        </el-radio>
      </el-form-item>

      <el-form-item label="安全等级" prop="safetyLevel">
        <el-radio
          v-model="form.safetyLevel"
          v-for="option in safetyLevels"
          :key="option.value"
          :label="option.value"
          >{{ option.label }}
        </el-radio>
      </el-form-item>

      <el-form-item label="服务限制" prop="restriction">
        <el-radio
          v-model="form.restriction"
          v-for="option in restrictions"
          :key="option.value"
          :label="option.value"
          >{{ option.label }}
        </el-radio>
      </el-form-item>

      <el-form-item label="服务状态" prop="serviceStatus">
        <el-radio
          v-model="form.serviceStatus"
          v-for="option in serviceStatus"
          :key="option.value"
          :label="option.value"
          >{{ option.label }}
        </el-radio>
      </el-form-item>

      <el-form-item label="服务描述" prop="serviceDesc">
        <el-input
          v-model.trim="form.serviceDesc"
          placeholder="请输入服务描述"
        />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm" size="mini"
        >确 定
      </el-button>
      <el-button @click="cancel" size="mini">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { handleCommonTrip } from "@/utils";
import { globalStaticVariable } from "@/config";
import { cloneDeep } from "lodash";
import { addService, updateService } from "@/api/service/service";
import { getApi } from "@/api/interface/api";

export default {
  name: "ModifyDialog",
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
  },
  data() {
    return {
      selectedApiType: null,
      apiTypes: {
        required: true,
        type: Array,
        default: [],
      },
      apis: [],
      checkStatus: false,
      loading: false,
      form: {
        serviceType: 0,
        serviceStatus: 0,
        restriction: 0,
        safetyLevel: 1,
        apiIds: [],
      },
      safetyLevels: globalStaticVariable["safetyLevels"],
      serviceTypes: globalStaticVariable["serviceTypes"],
      serviceStatus: globalStaticVariable["serviceStatus"],
      restrictions: globalStaticVariable["restrictions"],
      rules: {
        serviceName: [
          { required: true, message: "服务名称不能为空", trigger: "blur" },
        ],
        serviceDesc: [
          { required: true, message: "服务描述不能为空", trigger: "blur" },
        ],
        serviceType: [
          {
            required: true,
            message: "服务类型 0不限 1内网 2外网 不能为空",
            trigger: "change",
          },
        ],
        safetyLevel: [
          { required: true, message: "请选择安全等级", trigger: "change" },
        ],
        serviceStatus: [
          { required: true, message: "请选择服务状态", trigger: "change" },
        ],
        restriction: [
          { required: true, message: "请选择服务限制", trigger: "change" },
        ],
        apiIds: [{ required: true, message: "请选择接口", trigger: "change" }],
        apiType: [
          {
            required: true,
            validator: (rule, value, cb) => {
              cb();
            },
          },
        ],
      },
    };
  },
  created() {
    this.initSelect();
    if (this.opType === "EDIT") {
      this.form = cloneDeep(this.record);
      this.loadApiInfo();
    }
  },
  methods: {
    resolveResponse(res) {
      if (res.code === 200) {
        handleCommonTrip("success", res.message);
        this.cancel();
        this.$emit("refreshTable");
      }
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateService(this.form).then((response) => {
              this.resolveResponse(response);
            });
          } else {
            addService(this.form).then((response) => {
              this.resolveResponse(response);
            });
          }
        }
      });
    },
    cancel(isUpd) {
      this.$emit("close", isUpd);
      this.$refs.form.resetFields();
    },
    initSelect() {
      this.$api.category.list().then((res) => {
        if (res.code === 200) {
          this.apiTypes = res.result;
        }
      });
    },
    handleApiChange() {
      this.form.apiIds = [];
      this.$api.doc
        .apiList({ categoryId: this.selectedApiType })
        .then((res) => {
          if (res.code === 200) {
            this.apis = res.result;
          }
        });
    },
    loadApiInfo() {
      getApi(this.form.apiIds[0]).then((res) => {
        if (res.code === 200) {
          this.selectedApiType = res.result.categoryId;
          this.$api.doc
            .apiList({ categoryId: this.selectedApiType })
            .then((res) => {
              if (res.code === 200) {
                this.apis = res.result;
              }
            });
        }
      });
    },
  },
};
</script>

<style scoped></style>
