const express = require('express')
const router = express.Router();
const productContrller = require('../controller/productController');
const { tokenVerify } = require('../middleware/authMiddleware');




router.post("/add", tokenVerify, productContrller.addProduct);
router.get("/get", tokenVerify, productContrller.getProduct);
router.put("/update/:id", tokenVerify, productContrller.updateProduct);
router.delete("/delete/:id", tokenVerify, productContrller.deleteProduct);

module.exports =  router;