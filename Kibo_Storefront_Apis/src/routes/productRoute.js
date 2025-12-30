const express = require("express");
const router = express.Router();

const productController = require("../controllers/productController");



router.get("/", productController.getAllProducts);
router.get("/:productId", productController.getProductByCode);
router.post("/",productController.createProduct);
router.delete("/:productId", productController.deleteProduct);

module.exports = router;