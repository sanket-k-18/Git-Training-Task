const router = require("express").Router();
const auth = require("../middleware/authMiddleware");
const controller = require("../controllers/orderController");


router.post("/", auth, controller.createOrder);
router.get("/", auth, controller.getOrders);


module.exports = router;