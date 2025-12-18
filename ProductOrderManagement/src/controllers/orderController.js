const pool = require("../config/db");
const Order = require("../models/orderModel");


exports.createOrder = async (req, res) => {
const { product_id, quantity } = req.body;


const product = await pool.query("SELECT * FROM products WHERE id=$1", [product_id]);
if (product.rows.length === 0) {
return res.status(400).json({ message: "Product not found" });
}


if (product.rows[0].quantity < quantity) {
return res.status(400).json({ message: "Insufficient quantity" });
}


const total = product.rows[0].price * quantity;

await Order.create(product_id, quantity, total);
await pool.query(
"UPDATE products SET quantity = quantity - $1 WHERE id=$2",
[quantity, product_id]
);


res.json({ message: "Order created", total_price: total });
};


exports.getOrders = async (req, res) => {
const result = await Order.findAll();
res.json(result.rows);
};