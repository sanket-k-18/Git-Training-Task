const authService = require("../services/authService");

exports.login = (req, res) => {
  const token = authService.login(req.body);
  if (!token) return res.status(401).json({ message: "Invalid credentials" });
  res.json({ token });
};
