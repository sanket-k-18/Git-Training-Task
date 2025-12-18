const express = require("express");
const jwt = require("jsonwebtoken");
const { authenticateToken, SECRET_KEY } = require("./authMiddleware");

console.log("authenticateToken =", authenticateToken);

const app = express();
app.use(express.json());

// In-memory data
let products = [];
let orders = [];

// Fixed credentials
const USERNAME = "admin";
const PASSWORD = "admin123";

// LOGIN
app.post("/login", (req, res) => {
  const { username, password } = req.body;

  if (username === USERNAME && password === PASSWORD) {
    const token = jwt.sign({ username }, SECRET_KEY, { expiresIn: "1h" });
    return res.json({ token });
  }

  res.status(401).json({ message: "Invalid credentials" });
});

// PRODUCTS
app.post("/products", authenticateToken, (req, res) => {
  const { name, price, quantity } = req.body;

  const product = {
    id: products.length + 1,
    name,
    price,
    quantity,
    created_at: new Date(),
  };

  products.push(product);
  res.json(product);
});

app.get("/products", authenticateToken, (req, res) => {
  res.json(products);
});

app.put("/products/:id", authenticateToken, (req, res) => {
  const id = parseInt(req.params.id);
  const product = products.find(p => p.id === id);

  if (!product) {
    return res.status(404).json({ message: "Product not found" });
  }

  const { name, price, quantity } = req.body;
  if (name) product.name = name;
  if (price) product.price = price;
  if (quantity) product.quantity = quantity;

  res.json(product);
});

app.delete("/products/:id", authenticateToken, (req, res) => {
  const id = parseInt(req.params.id);
  products = products.filter(p => p.id !== id);
  res.json({ message: "Product deleted" });
});

// ORDERS
app.post("/orders", authenticateToken, (req, res) => {
  const { product_id, quantity } = req.body;

  const product = products.find(p => p.id === product_id);

  if (!product) {
    return res.status(400).json({ message: "Product does not exist" });
  }

  if (product.quantity < quantity) {
    return res.status(400).json({ message: "Insufficient quantity" });
  }

  const total_price = product.price * quantity;

  const order = {
    id: orders.length + 1,
    product_id,
    quantity,
    total_price,
    created_at: new Date(),
  };

  product.quantity -= quantity;
  orders.push(order);

  res.json(order);
});

app.get("/orders", authenticateToken, (req, res) => {
  res.json(orders);
});

app.listen(3000, () => {
  console.log("Server running on http://localhost:3000");
});
