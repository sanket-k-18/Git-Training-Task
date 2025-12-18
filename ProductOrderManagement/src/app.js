require("dotenv").config();
const express = require("express");


const authRoutes = require("./routes/authRoutes");
const productRoutes = require("./routes/productRoutes");
const orderRoutes = require("./routes/orderRoutes");


const app = express();
app.use(express.json());


app.use("/login", authRoutes);
app.use("/products", productRoutes);
app.use("/orders", orderRoutes);


app.listen(process.env.PORT, () => {
console.log(`Server running on port ${process.env.PORT}`);
});