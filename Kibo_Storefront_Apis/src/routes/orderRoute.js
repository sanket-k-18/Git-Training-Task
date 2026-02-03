const express = require('express');
const router = express.Router();

const orderController = require("../controllers/orderController")


router.post('/', orderController.makeOrder);

router.post("/return", orderController.createReturn);

router.post('/:productCode', orderController.addProductsToOrder);

router.put("/:orderId", orderController.cancleOrder);

router.get("/", orderController.getOrder);

router.post("/action/:orderId", orderController.performOrderAction);

router.post("/payment/:orderId", orderController.createPayment);

router.post("/payment/:orderId/action/:paymentId", orderController.performPaymentAction);

router.put("/fulfill/:shipmentNo", orderController.fulfillShipment);

router.post("/return/action", orderController.performReturnAction);

router.post("/return/item/:returnId", orderController.createReturnItem);

router.get("/returnable/items/:orderId", orderController.getReturnables);

router.post("/return/refund/:returnId", orderController.refund); 4

router.get("/return/items/:returnId", orderController.getReturnItems);

router.post("/return/order/:returnId", orderController.returnShippingOrder);

module.exports = router;