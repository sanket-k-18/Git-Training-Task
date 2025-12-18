const service = require("../services/orderService");

exports.create = async (req, res) => {
  try {
    const total = await service.createOrder(req.body);
    res.json({ message: "Order placed", total });
  } catch (err) {
    res.status(400).json({ message: err });
  }
};

exports.getAll = async (req, res) => res.json(await service.getOrders());
