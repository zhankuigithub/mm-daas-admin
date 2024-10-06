<template>
  <div class="app-container">
    <img src="@/assets/images/welcome.png" style="width: 80%;">
  </div>
</template>

<script>
  export default {
    name: "Index",
    data() {
      return {};
    },
    methods: {}
  };
</script>

<style scoped lang="scss">
  .app-container {
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 10px;
    min-height: calc(100vh - 120px);
    background: #ffffff;
  }
</style>

