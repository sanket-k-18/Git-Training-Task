require("dotenv").config();
const express = require("express");

const productRoutes = require("./routes/productRoute.js");
const cartRoutes = require("./routes/cartRoute.js");

const app = express();
app.use(express.json());

app.use("/products", productRoutes);
app.use("/cart", cartRoutes);

app.listen(process.env.PORT, () => {
  console.log(`Server running on port ${process.env.PORT}`);
});
