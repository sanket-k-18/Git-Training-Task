const { allocateInventory, findJob } = require("../service/inventoryService");


const addInventory = async (req, res ) => {
    const items = req.body;
    // console.log("Items " , items)
    try{
        const response = await allocateInventory(items);
        res.json(response);
    }catch(err){
        // console.log(err);
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


module.exports = {addInventory, getJob};