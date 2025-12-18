const Product = require("../models/productModel");


exports.addProduct = async (req, res) => {
const result = await Product.create(req.body);
res.json(result.rows[0]);
};


exports.getProducts = async (req, res) => {
const result = await Product.findAll();
res.json(result.rows);
};


exports.updateProduct = async (req, res) => {
const result = await Product.update(req.params.id, req.body);
res.json(result.rows[0]);
};

exports.deleteProduct = async (req, res) => {
await Product.delete(req.params.id);
res.json({ message: "Product deleted" });
};