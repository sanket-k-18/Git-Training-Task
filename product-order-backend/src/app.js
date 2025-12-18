const express = require("express");

const app = express();
app.use(express.json());

app.use("/auth", require("./routes/authRoutes"));
app.use("/products", require("./routes/productRoutes"));
app.use("/orders", require("./routes/orderRoutes"));

app.listen(3000, () => console.log("Server running on 3000"));
