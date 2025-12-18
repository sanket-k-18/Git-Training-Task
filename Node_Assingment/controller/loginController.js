const jwt = require("jsonwebtoken");

const login = (req, res) => {
    const { username, password } = req.body;

    if(username !== "GAU123" || password !== "pass123") {
        return res.status(401).json({ message: "Invalid credentials." }); 
    }

    const token = jwt.sign({username}, process.env.JWT_SECRET, { expiresIn: '1h' });
    return res.status(200).json({ message: "Login successful", token });

   
}

 module.exports = { login };