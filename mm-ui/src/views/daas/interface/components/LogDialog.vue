<template>
  <el-dialog
    title="发布日志"
    append-to-body
    width="90%"
    :visible.sync="openLog"
    :close-on-click-modal="false"
    :modal-append-to-body="false"
    v-if="openLog"
    :before-close="cancel"
    custom-class="dialogWrap"
  >
    <span v-if="this.baseList.length === 0">暂无发布日志</span>
    <el-row>
      <el-col :span="4">
        <ul class="publish-list">
          <li
            v-for="(item, index) in baseList"
            @click="handleCurrentChange(item)"
            :class="{ clicked: item.clicked }"
          >
            {{ convertDateFormat(item.createTime) }} {{ item.remark }}
          </li>
        </ul>
      </el-col>
      <el-col :span="20">
        <code-diff
          v-if="oldPayload != null && newPayload != null"
          :old-string="oldPayload"
          :new-string="newPayload"
          output-format="side-by-side"
        />
      </el-col>
    </el-row>
  </el-dialog>
</template>

<script>
import { convertDateFormat } from "@/utils/index";
import { CodeDiff } from "v-code-diff";
export default {
  name: "LogDialog",
  components: { CodeDiff },
  props: {
    openLog: {
      required: true,
      type: Boolean,
      default: false,
    },
    apiId: {
      required: true,
      type: Number,
      default: 0,
    },
  },
  data() {
    return {
      checkStatus: false,
      loading: false,
      form: {},
      baseList: [],
      oldPayload: null,
      newPayload: null,
    };
  },
  created() {
    this.getList();
  },
  methods: {
    convertDateFormat,
    getList() {
      this.loading = true;
      this.$api.apiLog.list(this.apiId).then((response) => {
        this.baseList = response.result;
        this.loading = false;
        // 默认加载最后一个
        if (this.baseList.length > 0) {
          this.handleCurrentChange(this.baseList[0]);
        }
      });
    },
    cancel() {
      this.$emit("close");
    },
    handleCurrentChange(row) {
      this.baseList.forEach((item) => {
        item.clicked = false;
      });
      row.clicked = true;

      const currentIndex = this.baseList.findIndex(
        (item) => item.id === row.id
      );
      if (currentIndex !== -1 && currentIndex < this.baseList.length - 1) {
        const nextRow = this.baseList[currentIndex + 1];
        this.newPayload = row.templateSql;
        this.oldPayload = nextRow.templateSql;
      } else {
        this.newPayload = row.templateSql;
        this.oldPayload = "";
      }
    },
  },
};
</script>

<style scoped>
.publish-list {
  overflow: hidden;
  margin-top: 12px;
}

li {
  display: list-item;
  text-align: -webkit-match-parent;
  text-decoration: none; /* 移除默认文本装饰（下划线） */
  cursor: pointer;
  margin-bottom: 10px;
  width: 100%;
  white-space: nowrap; /* 防止文本换行 */
  overflow: hidden; /* 隐藏溢出的文本 */
  text-overflow: ellipsis; /* 当文本溢出时显示省略号 */
}
li:hover {
  text-decoration: underline; /* 添加下划线 */
}
.clicked {
  color: #2c9eff; /* 设置文本颜色为蓝色 */
  text-decoration: underline; /* 保持下划线 */
}
</style>
