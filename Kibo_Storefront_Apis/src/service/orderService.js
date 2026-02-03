const { response } = require("express");
const kiboClient = require("../config/kiboClient")



const  createOrder = async (order) => {
    const response = await kiboClient.post("api/commerce/orders", order, {
        timeout : 20000
    });
    return response.data;
}

const addProductsToOrderService = async (productCode, product) => {
    const response = await kiboClient.post(`api/commerce/orders/${productCode}/items`, product, {
        timeout : 20000
    });
    return response.data;
}


const cancleOrderService = async (orderId, order) => {
    const response = await kiboClient.put(`api/commerce/orders/cancel/${orderId}`, order, {
        timeout : 20000
    });
    return response.data;
}

const getOrders = async () => {
    const response = await kiboClient.get('/api/commerce/orders', {
        timeout : 20000
    });
    return response.data;
}

const getOrderById = async (orderId) => {
    const respone = await kiboClient.get(`api/commerce/orders/${orderId}`, {
        timeout : 20000
    });
    return respone.data;
}

const performOrderActionService = async (orderId, action) => {
    const response = await kiboClient.put(`api/commerce/orders/${orderId}/actions`, action, {
        timeout : 20000
    });
    return response.data;
}


const createPaymentService = async (orderId, payment) => {
    const response = await kiboClient.post(`/api/commerce/orders/${orderId}/payments/actions`, payment, {
        timeout : 20000
    });
    // console.log("respnse" , response.data);
    return response.data;
}

const performPaymentActionService = async(orderId, paymentId, action) => {
    const response = await kiboClient.post(`api/commerce/orders/${orderId}/payments/${paymentId}/actions`, action, {
        timeout : 20000
    });
    return response.data;
}

const fulfillShipmentService = async(shipmentNo) => {
    const response = await kiboClient.put(`api/commerce/shipments/${shipmentNo}/fulfilled`, {
        timeout : 20000
    });
    return response.data;
}


const createReturnService = async(returnData) => {
    const response = await kiboClient.post(`api/commerce/returns`, returnData, {
        timeout : 20000
    });
    return response.data;
}

const returnAction = async(action) => {
    const response = await kiboClient.post(`api/commerce/returns/actions`, action, {
        timeout : 20000
    });
    return response.data;
}

const addReturnItem = async(returnId, item) => {
    const response = await kiboClient.post(`/api/commerce/returns/${returnId}/items`, item, {
        timeout : 20000
    })
    return response.data;
}

const getReturnableItems = async(orderId) => {
    const response = await kiboClient.get(`/api/commerce/orders/${orderId}/returnableitems`, {
        timeout : 20000
    });
    return response.data;
}

const autoRefund = async(returnId, refund) => {
    const response = await kiboClient.post(`/api/commerce/returns/${returnId}/autorefund`, refund, {
        timeout : 20000
    });
    return response.data;
}

const getReturnItemsService = async(returnId) => {
    const response = await kiboClient.get(`/api/commerce/returns/${returnId}/items`, {
        timeout : 20000
    });
    return response.data;
}

const createReturnShippingOrder = async(returnId) => {
    const response = await kiboClient.post(`/api/commerce/returns/${returnId}/ship`, {
        timeout : 20000
    });
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
    fulfillShipmentService,
    createReturnService,
    returnAction,
    addReturnItem,
    getReturnableItems,
    autoRefund,
    getReturnItemsService,
    createReturnShippingOrder
}