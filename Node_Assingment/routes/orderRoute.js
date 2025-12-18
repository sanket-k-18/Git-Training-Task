const express = require('express');
const router = express.Router();
const orderController = require('../controller/orderController');
const { tokenVerify } = require('../middleware/authMiddleware');

router.post('/make', tokenVerify, orderController.makeOrder);
router.get('/get/:id', tokenVerify, orderController.getOrder);
router.put('/update/:id', tokenVerify, orderController.updateOrder);
router.delete('/cancel/:id', tokenVerify, orderController.cancelOrder);

module.exports = router;