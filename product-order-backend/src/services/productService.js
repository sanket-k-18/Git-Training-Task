const productDao = require("../dao/productDao");

exports.addProduct = async (data) => (await productDao.create(data)).rows[0];
exports.getProducts = async () => (await productDao.findAll()).rows;
exports.updateProduct = async (id, data) =>
  (await productDao.update(id, data)).rows[0];
exports.deleteProduct = async (id) => productDao.delete(id);
exports.getProductById = async (id) =>
  (await productDao.findById(id)).rows[0];
