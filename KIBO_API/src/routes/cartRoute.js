const router = require("express").Router();
const {getMyCart,addToCart} = require("../controllers/cartController.js");

router.get("/", getMyCart);
router.post("/items", addToCart);

module.exports = router;
