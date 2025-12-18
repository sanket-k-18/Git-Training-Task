const pool = require("../config/db");

exports.create = (productId, qty, total) =>
  pool.query(
    "INSERT INTO orders(product_id,quantity,total_price) VALUES($1,$2,$3)",
    [productId, qty, total]
  );

  
exports.findAll = () => pool.query("SELECT * FROM orders");
