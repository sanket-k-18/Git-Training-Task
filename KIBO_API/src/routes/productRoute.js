const router = require("express").Router();
const { getAllProducts, getProduct } = require("../controllers/productController.js");

router.get("/", getAllProducts);
router.get("/:code", getProduct);

module.exports = router;

