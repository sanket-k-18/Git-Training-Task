const  { createCart, getCart, addItemToCart } = require("../services/cartService.js");

const getMyCart = async (req, res) => {
  try {
    let cartId = req.headers["x-cart-id"];

    if (!cartId) {
      const cart = await createCart();
      return res.json(cart);
    }

    const cart = await getCart(cartId);
    res.json(cart);
  } catch {
    res.status(500).json({ message: "Cart error" });
  }
};

const addToCart = async (req, res) => {
  try {
    const { cartId, productCode, quantity } = req.body;
    const cart = await addItemToCart(
      cartId,
      productCode,
      quantity
    );
    res.json(cart);
  } catch {
    res.status(500).json({ message: "Add to cart failed" });
  }
};

module.exports = { getMyCart, addToCart };
