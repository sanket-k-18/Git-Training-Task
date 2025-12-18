const { Product,Order } = require("../models");

const makeOrder = async (productId, quantity) => {
    const product = await Product.findOne({where: {id: productId}});
    if(!product){
        const error = new Error("Product not found");
        error.statusCode = 404;
        throw error;
    }

    if(product.quantity < quantity){
        const error = new Error("Insufficient product quantity");
        error.statusCode = 400;
        throw error;
    }

    const totalPriceCalucualted = product.price*quantity;

    try{
        const order = await Order.create({product_id : productId, quantity, total_price: totalPriceCalucualted});
        product.quantity -= quantity;
        await product.save();
        return order;
    }catch(err){
        throw err;
    }
}

const getOrder = async (orderId) => {
    const order = await Order.findOne({where: {id:orderId}});
    if(!order){
        const error = new Error("Order not found");
        error.statusCode = 404;
        throw error;
    }
    return order;
}   

const updateOrder = async (orderId, updateData) => {
      const order = await Order.findOne({where: {id:orderId}});
    if(!order){
        const error = new Error("Order not found");
        error.statusCode = 404;
        throw error;
    }

    const product = await Product.findOne({where: {id: order.product_id}});
    if(!product){
        const error = new Error("product not found");
        error.statusCode = 404;
        throw error;
    }
    
    console.log("upateData", updateData);
    
    if(updateData.quantity){
        const quantityDifference = updateData.quantity - order.quantity;
        if(product.quantity < quantityDifference){
            const error = new Error("Insufficient product quantity");
            error.statusCode = 400;
            throw error;
        }
        product.quantity -= quantityDifference;
        await product.save();
        order.total_price = product.price * updateData.quantity;
    }

    return order.update(updateData);
}

const cancelOrder = async(orderId) => {

    const order = await Order.findOne({where: {id:orderId}});
    if(!order){
        const error = new Error("Order not found");
        error.statusCode = 404;
        throw error;
    }

    const product = await Product.findOne({where: {id: order.product_id}});
    product.quantity += order.quantity;
    await product.save();
    return await order.destroy();
}

module.exports = {
    makeOrder,
    getOrder,
    updateOrder,
    cancelOrder
}




