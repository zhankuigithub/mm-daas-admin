<template>
  <div>
    <el-button size="mini" type="primary" plain @click="toggleFullscreen"
      >全屏</el-button
    >
    <div :style="{ height: height, width: width, border: '1px solid #c0c0c0' }">
      <div :id="id" ref="code-editor" style="height: 100%; width: 100%"></div>
    </div>
  </div>
</template>
<script>
import * as monaco from "monaco-editor";
import { sqlCompletion } from "@/utils/completion";
export default {
  name: "Monaco",
  props: {
    // 如果在一个页面中引入多次，则需要手动id
    id: {
      type: String,
      default: function () {
        return "code-editor";
      },
    },
    height: {
      type: String,
      default: function () {
        return "50vh";
      },
    },
    width: {
      type: String,
      default: function () {
        return "100%";
      },
    },
    codes: {
      type: String,
      default: function () {
        return "";
      },
    },
    fontSize: {
      type: Number,
      default: function () {
        return 12;
      },
    },
    language: {
      type: String,
      default: function () {
        return "sql";
      },
    },
    openMiniMap: {
      type: Boolean,
      default: function () {
        return true;
      },
    },
  },
  data() {
    return {
      monacoEditor: null,
      theme: "vs-dark",
    };
  },
  mounted() {
    this.monacoEditor = monaco.editor.create(document.getElementById(this.id), {
      value: this.codes, // 初始文字
      language: this.language, // 语言
      readOnly: false, // 是否只读
      automaticLayout: true, // 自动布局
      theme: this.theme, // vs | hc-black | vs-dark
      tabSize: 2, // tab缩进长度
      fontSize: this.fontSize, // 文字大小
      minimap: {
        enabled: this.openMiniMap,
      },
      scrollBeyondLastLine: false, // 最后一行之后，禁止滚动
    });
    if (this.language === "sql") {
      sqlCompletion();
    }
  },
  beforeDestroy() {
    if (this.monacoEditor) {
      this.monacoEditor.dispose();
    }
  },
  computed: {
    inputVal() {
      return this.monacoEditor?.getValue();
    },
  },
  watch: {
    inputVal() {
      if (this.monacoEditor) {
        this.$emit("change", this.monacoEditor.getValue());
      }
    },
  },
  methods: {
    toggleFullscreen() {
      const editorContainer = document.getElementById(this.id);
      if (!document.fullscreenElement) {
        if (editorContainer.requestFullscreen) {
          editorContainer.requestFullscreen();
        } else if (editorContainer.mozRequestFullScreen) {
          editorContainer.mozRequestFullScreen();
        } else if (editorContainer.webkitRequestFullscreen) {
          editorContainer.webkitRequestFullscreen();
        } else if (editorContainer.msRequestFullscreen) {
          editorContainer.msRequestFullscreen();
        }
      }
    },
  },
};
</script>
