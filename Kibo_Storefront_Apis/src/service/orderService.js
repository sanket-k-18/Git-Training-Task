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

const getOrders = async () => {
    const response = await kiboClient.get('/api/commerce/orders');
    return response.data;
}

const getOrderById = async (orderId) => {
    const respone = await kiboClient.get(`api/commerce/orders/${orderId}`);
    return respone.data;
}

const performOrderActionService = async (orderId, action) => {
    const response = await kiboClient.put(`api/commerce/orders/${orderId}/actions`, action);
    return response.data;
}


const createPaymentService = async (orderId, payment) => {
    const response = await kiboClient.post(`/api/commerce/orders/${orderId}/payments/actions`, payment);
    // console.log("respnse" , response.data);
    return response.data;
}

const performPaymentActionService = async(orderId, paymentId, action) => {
    const response = await kiboClient.post(`api/commerce/orders/${orderId}/payments/${paymentId}/actions`, action);
    return response.data;
}

const fulfillShipmentService = async(shipmentNo) => {
    const response = await kiboClient.put(`api/commerce/shipments/${shipmentNo}/fulfilled`);
    return response.data;
}

module.exports = {
    createOrder, 
    addProductsToOrderService, 
    cancleOrderService, 
    getOrderById, 
    getOrders, 
    performOrderActionService,
    createPaymentService, 
    performPaymentActionService,
    fulfillShipmentService
};