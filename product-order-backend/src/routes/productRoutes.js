const router = require("express").Router();
const ctrl = require("../controllers/productController");
const auth = require("../middlewares/authMiddleware");

router.post("/", auth, ctrl.add);
router.get("/", auth, ctrl.getAll);
router.put("/:id", auth, ctrl.update);
router.delete("/:id", auth, ctrl.remove);

module.exports = router;
