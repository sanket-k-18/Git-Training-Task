const {createOrder} = require('../service/orderService') 

const makeOrder = async(req, res) => {
    try{
    const createdOrder = await createOrder(req.body);
    res.json(createdOrder);
    }catch(err){
         res.status(err.response?.status || 500).json({message : err.message || "INTERNAL SERVER ERROR"});
    }
}



module.exports = {makeOrder}