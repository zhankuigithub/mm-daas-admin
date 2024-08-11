<template>
  <el-dialog
    :title="opType === 'ADD' ? '添加应用' : '编辑应用'"
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
    >
      <el-form-item v-show="false">
        <el-input v-model.trim="form.id"></el-input>
      </el-form-item>
      <el-form-item label="应用名称" prop="appName">
        <el-input v-model.trim="form.appName" placeholder="请输入应用名称" />
      </el-form-item>
      <el-form-item label="应用账号" prop="appAccount">
        <el-input v-model.trim="form.appAccount" placeholder="请输入应用账号" />
      </el-form-item>
      <el-form-item label="应用密码" prop="appPwd">
        <el-input v-model.trim="form.appPwd" placeholder="请输入应用密码" />
      </el-form-item>
      <el-form-item label="应用类型" prop="appType">
        <el-select v-model.trim="form.appType">
          <el-option
            v-for="item in appTypes"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          ></el-option>
        </el-select>
      </el-form-item>
      <div v-if="form.appType !== 3">
        <el-form-item label="应用所有人" prop="appOwner">
          <el-input v-model.trim="form.appOwner" placeholder="应用所有人" />
        </el-form-item>
        <el-form-item label="邮箱账号" prop="appEmail">
          <el-input v-model.trim="form.appEmail" placeholder="邮箱账号" />
        </el-form-item>
      </div>
      <div v-if="form.appType === 3">
        <el-form-item label="产品所有人" prop="appOwner">
          <el-input v-model.trim="form.appOwner" placeholder="主责产品所有人" />
        </el-form-item>
        <el-form-item label="产品邮箱账号" prop="appEmail">
          <el-input
            v-model.trim="form.appEmail"
            placeholder="主责产品邮箱账号"
          />
        </el-form-item>
      </div>
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
import { handleCommonTrip } from "@/utils";
import { globalStaticVariable } from "@/config";
import { cloneDeep } from "lodash";
import { Encrypt } from "@/utils/rsa.js";

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
      checkStatus: false,
      loading: false,
      form: {},
      appTypes: globalStaticVariable["appTypes"],
      rules: {
        appName: [
          { required: true, message: "应用名称不能为空", trigger: "blur" },
        ],
        appAccount: [
          { required: true, message: "应用账号不能为空", trigger: "blur" },
        ],
        appType: [
          { required: true, message: "请选择应用类型", trigger: "change" },
        ],
        appOwner: [
          { required: true, message: "所有人不能为空", trigger: "blur" },
        ],
        appEmail: [
          { required: true, message: "邮箱账号不能为空", trigger: "blur" },
        ],
        appPwd: [
          {
            min: 8,
            message: "密码不得少于8位",
            trigger: "blur",
          },
          { validator: this.validatePassword, trigger: "blur" },
        ],
      },
    };
  },
  created() {
    if (this.opType === "EDIT") {
      this.form = cloneDeep(this.record);
    }
  },
  methods: {
    validatePassword(rule, value, callback) {
      if (!value || value.length === 0) {
        callback();
      } else {
        const regExp = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\W)(?!.*\s).{6,}$/;
        if (!regExp.test(value)) {
          callback(new Error("密码必须包含数字、字母（大小写）、特殊字符"));
        } else {
          callback();
        }
      }
    },
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
          let requestParams = cloneDeep(this.form);
          if (requestParams.appPwd) {
            requestParams.appPwd = Encrypt(requestParams.appPwd);
          }
          if (this.form.id !== undefined) {
            this.$api.application.modify(requestParams).then((res) => {
              this.resolveResponse(res);
            });
          } else {
            this.$api.application.create(requestParams).then((res) => {
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
  },
};
</script>

<style scoped></style>
