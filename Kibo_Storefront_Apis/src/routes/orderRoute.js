const express = require('express');
const router = express.Router();

const orderController = require("../controllers/orderController")


router.post('/', orderController.makeOrder);

router.post('/:productCode', orderController.addProductsToOrder);

router.put("/:orderId", orderController.cancleOrder);

router.get("/", orderController.getOrder);

router.post("/action/:orderId", orderController.performOrderAction);

router.post("/payment/:orderId", orderController.createPayment);

router.post("/payment/:orderId/action/:paymentId", orderController.performPaymentAction);


module.exports = router;