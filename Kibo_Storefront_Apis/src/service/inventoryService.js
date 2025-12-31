const kiboClient = require("../config/kiboClient");



const allocateInventory = async (items) => {
    const response = await kiboClient.post('api/commerce/inventory/v5/inventory/allocate', items);
    return response.data;
}


const findJob = async (jobId) => {
    const response = await kiboClient.get(`/api/commerce/inventory/v1/queue/${jobId}`);
    return response.data;
}



module.exports = {allocateInventory, findJob};