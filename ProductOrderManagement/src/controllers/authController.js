const jwt = require("jsonwebtoken");
const { secret, expiresIn } = require("../config/jwt");


const USER = {
username: "admin",
password: "admin123",
};


exports.login = (req, res) => {
const { username, password } = req.body;


if (username !== USER.username || password !== USER.password) {
return res.status(401).json({ message: "Invalid credentials" });
}


const token = jwt.sign({ username }, secret, { expiresIn });
res.json({ token });
};