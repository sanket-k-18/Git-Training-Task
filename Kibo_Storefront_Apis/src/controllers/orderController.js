const {
  createOrder, 
  addProductsToOrderService, 
  cancleOrderService, 
  getOrderById, 
  getOrders, 
  performOrderActionService, 
  fulfillShipmentService, 
  createPaymentService, 
  performPaymentActionService
   } = require('../service/orderService') 

const makeOrder = async(req, res) => {
    try{
    const createdOrder = await createOrder(req.body);
    res.json(createdOrder);
    }catch(err){
         res.status(err.response?.status || 500).json({message : err.message || "INTERNAL SERVER ERROR"});
    }
}

const addProductsToOrder = async (req, res) => {
  try{
    const product = req.body;
    const productCode = req.params.productCode;
   const response =  await addProductsToOrderService(productCode, product);
   res.json(response);
  }catch(err){
        res.status(err.response?.status || 500).json({message : err.message || "INTERNAL SERVER ERROR"});
  }
}

const cancleOrder = async (req, res) => {
    const orderId = req.params.orderId;
    const order = req.body;

    try{
        const response = await cancleOrderService(orderId, order);
        res.json(response);
    }catch(err){
         res.status(err.response?.status || 500).json({message : err.message || "INTERNAL SERVER ERROR"});
    }
}


const getOrder = async (req, res) => {
  const orderId = req.query.orderId;
  // console.log(orderId);
  try {
    if(orderId){
      const respone = await getOrderById(orderId);
      res.json(respone);
    }
    const response = await getOrders();
    res.json(response);
  }catch(err){
    // console.log(err)
    res.status(err.response?.status || 500).json({message : err.message || "INTERNAL SERVER ERROR"});
  }
}

const performOrderAction = async (req, res) => {
    const orderId = req.params.orderId;
    const action = req.body;

    try{
        const response = await performOrderActionService(orderId, action);
        res.json(response);
    }catch(err){
         res.status(err.response?.status || 500).json({message : err.message || "INTERNAL SERVER ERROR"});
    }
}

const createPayment = async (req, res) => {
  const orderId = req.params.orderId;
  const payment = req.body;
  try{
    const response = await createPaymentService(orderId, payment);
    res.json(response);
  }catch(err){
    res.status(err.respone?.status || 500).json({message : err.message || "INTERNAL SERVER ERROR"});
  }
}

const fulfillShipment = async (req, res) => {
  const shipmentNo = req.params.shipmentNo;
  try{
    const response = await fulfillShipmentService(shipmentNo);
    res.json(response);
  }catch(err){
    res.status(err.response?.status || 500).json({message : err.message || "INTERNAL SERVER ERROR"});
  }
}

const performPaymentAction = async(req, res) => {
  const {orderId, paymentId} = req.params;
  const payment = req.body;

  try{
    const response = await performPaymentActionService(orderId, paymentId, payment)
    res.json(response);
  }catch(err){
    res.status(err.response?.status || 500).json({message : err.message || "INTERNAL SERVER ERROR"});
  }
}

module.exports = {
  makeOrder, 
  addProductsToOrder, 
  cancleOrder, 
  getOrder, 
  performOrderAction,
  fulfillShipment,
  createPayment,
  performPaymentAction
};