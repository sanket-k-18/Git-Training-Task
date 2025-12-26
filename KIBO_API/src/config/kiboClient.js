const axios = require("axios");
const getKiboToken = require("./kiboTokenManager");

const kiboClient = axios.create({
  baseURL: process.env.KIBO_BASE_URL,
  timeout: 5000
});

kiboClient.interceptors.request.use(async (config) => {
  console.log("Using token for request");
  const token = await getKiboToken();
    

  config.headers.Authorization = `Bearer ${token}`;
  config.headers.Accept = "application/json";
  config.headers["Content-Type"] = "application/json";


//   config.headers["X-Mozu-Context-Level"] = "Site";
//   config.headers["X-Mozu-Tenant"] = process.env.KIBO_TENANT_ID;
//   config.headers["X-Mozu-Site"] = process.env.KIBO_SITE_ID;

 config.headers["x-vol-context-level"] = "Site";
  config.headers["x-vol-tenant"] = process.env.KIBO_TENANT_ID;
  config.headers["x-vol-site"] = process.env.KIBO_SITE_ID;


//   console.log("Access Token:", token);
  return config;
  
});

module.exports = kiboClient;
