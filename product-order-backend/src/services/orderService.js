const orderDao = require("../dao/orderDao");
const productDao = require("../dao/productDao");

exports.createOrder = async ({ product_id, quantity }) => {
  const product = (await productDao.findById(product_id)).rows[0];
  if (!product) throw "Product not found";
  if (product.quantity < quantity) throw "Insufficient stock";

  const total = product.price * quantity;

  await orderDao.create(product_id, quantity, total);
  await productDao.update(product_id, {
    ...product,
    quantity: product.quantity - quantity
  });

  return total;
};

exports.getOrders = async () => (await orderDao.findAll()).rows;
