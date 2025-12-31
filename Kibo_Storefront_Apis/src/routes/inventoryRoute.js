const express = require('express');
const router = express.Router();

const inventoryController = require('../controllers/inventoryController');



router.post('/', inventoryController.addInventory);

router.get('/job/:jobId', inventoryController.getJob);


module.exports = router;