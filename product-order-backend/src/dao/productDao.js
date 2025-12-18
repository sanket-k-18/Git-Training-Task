const pool = require("../config/db");

exports.create = (data) =>
  pool.query(
    "INSERT INTO products(name,price,quantity) VALUES($1,$2,$3) RETURNING *",
    [data.name, data.price, data.quantity]
  );

exports.findAll = () => pool.query("SELECT * FROM products");

exports.update = (id, data) =>
  pool.query(
    "UPDATE products SET name=$1,price=$2,quantity=$3 WHERE id=$4 RETURNING *",
    [data.name, data.price, data.quantity, id]
  );

exports.delete = (id) =>
  pool.query("DELETE FROM products WHERE id=$1", [id]);

exports.findById = (id) =>
  pool.query("SELECT * FROM products WHERE id=$1", [id]);
