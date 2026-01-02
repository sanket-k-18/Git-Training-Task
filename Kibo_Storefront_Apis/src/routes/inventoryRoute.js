const express = require('express');
const router = express.Router();

const inventoryController = require('../controllers/inventoryController');



router.post('/allocate', inventoryController.addInventory);

router.get('/job/:jobId', inventoryController.getJob);

router.get('/product/:productCode', inventoryController.getProductInventory);

router.post('/deallocate', inventoryController.removeInventory);

module.exports = router;    