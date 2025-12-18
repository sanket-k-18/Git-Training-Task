const orderService = require('../service/orderService');

const makeOrder = async (req, res) => {
    const {productId, quantity} = req.body;
    if(!productId || !quantity){
        return res.status(400).json({message: "all feilds are required"});
    }

    try{
        const order = await orderService.makeOrder(productId, quantity);
        return res.status(201).json(order);
    } catch(err){
        return res.status(err.statusCode || 500).json({message: err.message || "Internal Server Error" });
    }
}

const getOrder = async (req, res) => {
    const id = req.params.id;
    
    try{
        const order = await orderService.getOrder(id);
        return res.status(200).json(order);
    } catch(err){
        return res.status(err.statusCode || 500).json({message: err.message || "Internal Server Error" });
    }
}

const updateOrder = async (req, res) => {
    const id = req.params.id;
    const updateData = req.body;

    console.log("updateData in controller", updateData);
    try{
        const order = await orderService.updateOrder(id, updateData);
        return res.status(200).json(order);
    } catch(err){
        return res.status(err.statusCode || 500).json({message: err.message || "Internal Server Error" });
    }
}


const cancelOrder = async (req, res) => {
    const id = req.params.id;
    try{
        await orderService.cancelOrder(id);
        return res.status(200).json({message: "Order cancelled successfully"});
    } catch(err){
        return res.status(err.statusCode || 500).json({message: err.message || "Internal Server Error" });
    }
}


module.exports = {
    makeOrder,
    getOrder,
    updateOrder,
    cancelOrder
}