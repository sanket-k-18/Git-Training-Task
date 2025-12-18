const service = require("../services/productService");

exports.add = async (req, res) => res.json(await service.addProduct(req.body));
exports.getAll = async (req, res) => res.json(await service.getProducts());
exports.update = async (req, res) =>
  res.json(await service.updateProduct(req.params.id, req.body));
exports.remove = async (req, res) => {
  await service.deleteProduct(req.params.id);
  res.json({ message: "Deleted" });
};
