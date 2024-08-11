const md = require.context("./modules", false, /\.js$/);
const api = {};
md.keys().forEach((key) => {
  api[key.replace(/(\.\/|\.js)/g, "")] = md(key);
});
export default api;
