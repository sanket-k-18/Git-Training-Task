const kiboClient = require("../config/kiboClient");



const allocateInventory = async (items) => {
    const response = await kiboClient.post('api/commerce/inventory/v5/inventory/allocate', items);
    return response.data;
}

const deallocateInvetory = async (items) => {
    const response = await kiboClient.post('api/commerce/inventory/v5/inventory/deallocate', items);
    return response.data;
}


const findJob = async (jobId) => {
    const response = await kiboClient.get(`/api/commerce/inventory/v1/queue/${jobId}`);
    return response.data;
}

const getProductInventoryService = async (productCode) => {
    const response = await kiboClient.get(`api/commerce/catalog/storefront/products/${productCode}/locationinventory`);
    return response.data;
}



module.exports = {allocateInventory, findJob, getProductInventoryService, deallocateInvetory};