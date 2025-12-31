require('dotenv').config();

const express = require('express');
const sequilize = require('./src/config/db.config');
const authRoutes = require('./src/routes/authRoute');
const productRoute = require('./src/routes/productRoute');
const cartRoute = require('./src/routes/cartRoute');
const orderRoute = require('./src/routes/orderRoute');
const inventoryRoute = require('./src/routes/inventoryRoute');
const app = express();

app.use(express.json());


app.use('/user', authRoutes);
app.use('/product', productRoute);
app.use('/cart', cartRoute);
app.use('/order', orderRoute);
app.use('/inventory', inventoryRoute);


// sequilize.sync({alter:true}).then(() => "database synced");

app.listen(3000, () => {
    console.log("server is listening on port 3000");
})

