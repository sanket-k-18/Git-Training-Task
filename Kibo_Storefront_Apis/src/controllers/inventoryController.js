const { allocateInventory, findJob,getProductInventoryService, deallocateInvetory } = require("../service/inventoryService");


const addInventory = async (req, res ) => {
    const items = req.body;
    try{
        const response = await allocateInventory(items);
        res.json(response);
    }catch(err){
        // console.log(err);
         res.status(err.response?.status || 500).json({message : err.message || "INTERNAL SERVER ERROR"});
    }
}

const removeInventory = async (req, res) => {
    const items = req.body;
    try{
        const response = await deallocateInvetory(items);
        res.json(response);
    }catch(err){
        res.status(err.response?.status || 500).json({message : err.message || "INTERNAL SERVER ERROR"});
    }
}


const getJob = async(req, res) => {
    const jobId = req.params.jobId;
    try{
        const response = await findJob(jobId);
        res.json(response);
    }catch(err){
        res.status(err.response?.status || 500).json({message : err.message || "INTERNAL SERVER ERROR"});
    }
}

const getProductInventory = async (req, res) => {
    const productCode = req.params.productCode;
    // console.log("PRodct code", productCode);
    try{
        const response = await getProductInventoryService(productCode);
        res.json(response);
    }catch(err){
        res.status(err.response?.status || 500).json({message : err.message || "INTERNAL SERVER ERROR"});
    }
}


module.exports = {addInventory, getJob, getProductInventory, removeInventory};