const express = require("express");
const router = express.Router();

const productController = require("../controllers/productController");



router.get("/get", productController.getAllProducts);
router.get("/get/:productId", productController.getProductByCode);

module.exports = router;