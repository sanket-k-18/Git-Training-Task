const {getAllProductsService, getProductByCodeService} = require("../service/productService");

const getAllProducts = async (req, res) => {
  try {
    const pageSize = Number(req.query.pageSize) || 20;
    const page = Number(req.query.page) || 1;


    const data = await getAllProductsService(page, pageSize);
    res.json(data);
  } catch (err) {
    res.status(500).json({ message: "INTERNAL SERVER ERROR" });
  }
};

const getProductByCode = async(req, res) => {
    try{

        const productId = req.params.productId;
        
        console.log("Product code", productId);
        const product = await getProductByCodeService(productId);
        res.json(product);
    }catch(error){
        res.status(404).json({message : "Product not found"});
    }
};


module.exports = {getAllProducts, getProductByCode}