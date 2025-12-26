const { getProducts, getProductByCode } = require("../services/productService.js");

const getAllProducts = async (req, res) => {
  try {
    const page = Number(req.query.page) || 1;
    const pageSize = Number(req.query.pageSize) || 20;

    const data = await getProducts(page, pageSize);
    res.json(data);
  } catch (err) {
    res.status(500).json({ message: "Failed to fetch products" });
  }
};

const getProduct = async (req, res) => {
  try {
    const product = await getProductByCode(req.params.code);
    res.json(product);
  } catch {
    res.status(404).json({ message: "Product not found" });
  }
};

module.exports = { getAllProducts, getProduct };
