const {createOrder, addProductsToOrderService, cancleOrderService} = require('../service/orderService') 

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
        console.log(err);   
         res.status(err.response?.status || 500).json({message : err.message || "INTERNAL SERVER ERROR"});
    }
}

module.exports = {makeOrder, addProductsToOrder, cancleOrder}