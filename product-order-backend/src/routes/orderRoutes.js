const router = require("express").Router();
const ctrl = require("../controllers/orderController");
const auth = require("../middlewares/authMiddleware");

router.post("/", auth, ctrl.create);
router.get("/", auth, ctrl.getAll);

module.exports = router;
