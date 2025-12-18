const router = require("express").Router();
const auth = require("../middleware/authMiddleware");
const controller = require("../controllers/productController");


router.post("/", auth, controller.addProduct);
router.get("/", auth, controller.getProducts);
router.put("/:id", auth, controller.updateProduct);
router.delete("/:id", auth, controller.deleteProduct);


module.exports = router;