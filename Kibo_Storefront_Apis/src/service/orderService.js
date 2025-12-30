const kiboClient = require("../config/kiboClient")



const  createOrder = async (order) => {
    const response = await kiboClient.post("api/commerce/orders", order);
    return response.data;
}

const addProductsToOrderService = async (productCode, product) => {
    const response = await kiboClient.post(`api/commerce/orders/${productCode}/items`, product);
    return response.data;
}


const cancleOrderService = async (orderId, order) => {
    const response = await kiboClient.put(`api/commerce/orders/cancel/${orderId}`, order);
    return response.data;
}



module.exports = {createOrder, addProductsToOrderService, cancleOrderService};