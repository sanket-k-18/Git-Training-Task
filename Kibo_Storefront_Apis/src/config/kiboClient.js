const axios = require("axios");
const getKiboToken = require("./kiboTokenManager");

const KIBO_BASE_URL = "https://t41316.sandbox.mozu.com";
const SITE_ID = process.env.KIBO_SITE_ID;

const kiboClient = axios.create({
    baseURL : KIBO_BASE_URL,
    timeout : 1000,
});


kiboClient.interceptors.request.use(async(config) => {
    const token = await getKiboToken();
    config.headers.Authorization = `Bearer ${token}`;
    config.headers["x-vol-site"] = SITE_ID;
    config.headers["Content-Type"] = "application/json";
    config.headers["Accept"] = "application/json";

    return config;
});

module.exports = kiboClient;