export const apiBaseUrl = 'http://localhost:9999';//服务端地址
uni.$u.apiBaseUrl = apiBaseUrl;

const install = (app, options) => {
	
	app.config.globalProperties.$u.apiBaseUrl = apiBaseUrl;
	
	
};

export default {install};
