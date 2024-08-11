<template>
  <el-dialog
    :title="opType === 'ADD' ? '添加服务授权' : '编辑服务授权'"
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
      :model="form"
      :rules="rules"
      label-width="110px"
      size="mini"
      label-position="left"
    >
      <el-form-item v-show="false">
        <el-input v-model="form.id"></el-input>
      </el-form-item>
      <el-form-item label="服务名称" prop="serviceId">
        <el-select clearable v-model="form.serviceId" class="form-input">
          <el-option
            v-for="(item, index) of services"
            :key="index"
            :label="item.serviceName"
            :value="item.id"
            >{{ item.serviceName }}</el-option
          >
        </el-select>
      </el-form-item>

      <el-form-item label="应用名称" prop="appId">
        <el-select clearable v-model="form.appId" class="form-input">
          <el-option
            v-for="(item, index) of apps"
            :key="index"
            :label="item.appName"
            :value="item.id"
            >{{ item.appName }}</el-option
          >
        </el-select>
      </el-form-item>

      <el-form-item label="服务类型" prop="serviceType">
        <el-radio-group v-model="form.serviceType" :key="form.serviceType">
          <el-radio v-for="item of usableService" :label="item.value">{{
            item.label
          }}</el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item label="开始时间" prop="beginTime">
        <el-date-picker
          class="form-input"
          v-model="form.beginTime"
          type="datetime"
          placeholder="选择开始时间"
          value-format="yyyy-MM-dd HH:mm:ss"
        >
        </el-date-picker>
      </el-form-item>

      <el-form-item label="结束时间" prop="endTime">
        <el-date-picker
          class="form-input"
          v-model="form.endTime"
          type="datetime"
          placeholder="选择结束时间"
          value-format="yyyy-MM-dd HH:mm:ss"
        >
        </el-date-picker>
      </el-form-item>

      <el-form-item label="费用" prop="restriction">
        <el-radio-group v-model="form.restriction">
          <el-radio v-for="item of restrictions" :label="item.value">{{
            item.label
          }}</el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item label="鉴权开关" prop="openAuth">
        <el-radio-group v-model="form.openAuth">
          <el-radio v-for="item of openAuths" :label="item.value">{{
            item.label
            }}</el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item label="调用次数限制" prop="limitTotal">
        <el-input
          class="form-input"
          type="number"
          v-model="form.limitTotal"
          placeholder="调用次数限制"
        />
      </el-form-item>
      <el-form-item label="ip白名单" prop="inputList">
        <template>
          <div>
            <div v-for="(item, index) in inputList" :key="index">
              <el-input v-model="item.value" class="form-input"></el-input>
            </div>
            <el-button @click="addItem">添加</el-button>
            <el-button @click="removeItem">删除</el-button>
          </div>
        </template>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button size="mini" type="primary" @click="submitForm"
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
    apps: {
      required: true,
      type: Array,
      default: [],
    },
    services: {
      required: true,
      type: Array,
      default: [],
    },
    serviceMap: {},
  },
  data() {
    return {
      inputList: [
        { value: '' },
      ],
      checkStatus: false,
      loading: false,
      form: {
        serviceType: 0,
        restriction: 0,
      },
      safetyLevels: globalStaticVariable["safetyLevels"],
      serviceTypes: globalStaticVariable["serviceTypes"],
      restrictions: globalStaticVariable["restrictions"],
      openAuths: globalStaticVariable["openAuths"],
      rules: {
        serviceId: [{ required: true, message: "请选择服务", trigger: "blur" }],
        appId: [{ required: true, message: "请选择应用", trigger: "blur" }],
        serviceType: [
          { required: true, message: "请选择服务类型", trigger: "change" },
        ],
        beginTime: [
          { required: true, message: "请选择开始时间", trigger: "blur" },
        ],
        endTime: [
          { required: true, message: "请选择结束时间", trigger: "blur" },
        ],
        restriction: [
          { required: true, message: "请选择服务限制", trigger: "change" },
        ],
        limitTotal: [
          { required: true, message: "请输入调用限制", trigger: "blur" },
        ],
        openAuth: [
          { required: true, message: "请选择鉴权开关", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    if (this.opType === "EDIT") {
      this.form = cloneDeep(this.record);
     if(this.record.trustList && this.record.trustList.length>0) {
       this.inputList=[];
      this.record.trustList.split(',').forEach(value=>{
          this.inputList.push({ value: value })
       });
     }
    }
  },
  computed: {
    usableService() {
      if (!this.form.serviceId) {
        return this.serviceTypes;
      }

      let serviceType = this.serviceMap.get(this.form.serviceId).serviceType;
      if (serviceType === 1) {
        return [{value: 1, label: "内网"}];
      } else {
        return this.serviceTypes.filter(item => item.value !== 2);
      }
    },
  },
  watch: {
    "form.serviceId": {
      immediate: true,
      handler() {
        this.form.serviceType = this.serviceMap.get(this.form.serviceId)?.serviceType === 1 ? 1 : 0;
      },
    }},
  methods: {
    resolveResponse(res) {
      if (res.code === 200) {
        handleCommonTrip("success", res.message);
        this.cancel();
        this.$emit("refreshTable");
      }
    },
    submitForm() {
      this.form.trustList = this.getResult();
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (timeVerification(this.form.beginTime, this.form.endTime)) {
            handleCommonTrip("warning", "开始时间不可以大于结束时间");
            return;
          }

          if (this.form.id !== undefined) {
            this.$api.authorization.modify(this.form).then((res) => {
              this.resolveResponse(res);
            });
          } else {
            this.$api.authorization.create(this.form).then((res) => {
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
    addItem() {
      this.inputList.push({ value: '' });
    },
    removeItem() {
      this.inputList.pop();
    },
    getResult() {
      let result = this.inputList.map((item) => item.value).join(',');
      return result;
    },
  },
};
</script>

<style scoped>
.form-input {
  width: 90%;
}
</style>
