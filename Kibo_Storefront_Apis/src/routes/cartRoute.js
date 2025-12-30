const express = require("express");
const router = express.Router();
const cartController = require("../controllers/cartController");
const {tokenVerify} = require('../middlewares/authMiddleware')

router.get("/", tokenVerify, cartController.getOrCreate);


module.exports = router;