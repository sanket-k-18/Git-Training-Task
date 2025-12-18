const express = require('express');
const app = express();
const productRoute = require("./routes/productRoute");
const orderRoute = require("./routes/orderRoute");
const authRoute = require("./routes/authRoute");
const sequelize = require('./db.config');
require("dotenv").config()

app.use(express.json());

sequelize.sync({alter : true}).then(() => {console.log("database synced")})

app.use('/auth', authRoute);
app.use('/product', productRoute);
app.use('/order', orderRoute);

app.listen(3000, () => {
    console.log('Server is running on port 3000');
})

