package com.wz.xposedalipay.bean;

public class LoginBean {


    /**
     * message : 请求成功
     * status : 1
     * result : {"token":"a9b49f34b253ce0cb668e80143079306","data":{"bank_name":"支付宝","code_name":"测试账号","url":"#","logo":"#","code_type":2,"code_ip_proxy_agent_url":"123","code_ip_proxy_usename":"123","code_ip_proxy_password":"123","code_ip_proxy_ip":"122.114.112.242:16818","code_login_password":"123","code_login_username":"123","is_online":"1","code_id":3334,"bank_id":"29","auto_login":"1"}}
     */

    private String message;
    private String status;
    private ResultBean result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * token : a9b49f34b253ce0cb668e80143079306
         * data : {"bank_name":"支付宝","code_name":"测试账号","url":"#","logo":"#","code_type":2,"code_ip_proxy_agent_url":"123","code_ip_proxy_usename":"123","code_ip_proxy_password":"123","code_ip_proxy_ip":"122.114.112.242:16818","code_login_password":"123","code_login_username":"123","is_online":"1","code_id":3334,"bank_id":"29","auto_login":"1"}
         */

        private String token;
        private DataBean data;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * bank_name : 支付宝
             * code_name : 测试账号
             * url : #
             * logo : #
             * code_type : 2
             * code_ip_proxy_agent_url : 123
             * code_ip_proxy_usename : 123
             * code_ip_proxy_password : 123
             * code_ip_proxy_ip : 122.114.112.242:16818
             * code_login_password : 123
             * code_login_username : 123
             * is_online : 1
             * code_id : 3334
             * bank_id : 29
             * auto_login : 1
             */

            private String bank_name;
            private String code_name;
            private String url;
            private String logo;
            private int code_type;
            private String code_ip_proxy_agent_url;
            private String code_ip_proxy_usename;
            private String code_ip_proxy_password;
            private String code_ip_proxy_ip;
            private String code_login_password;
            private String code_login_username;
            private String is_online;
            private int code_id;
            private String bank_id;
            private String auto_login;

            public String getBank_name() {
                return bank_name;
            }

            public void setBank_name(String bank_name) {
                this.bank_name = bank_name;
            }

            public String getCode_name() {
                return code_name;
            }

            public void setCode_name(String code_name) {
                this.code_name = code_name;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public int getCode_type() {
                return code_type;
            }

            public void setCode_type(int code_type) {
                this.code_type = code_type;
            }

            public String getCode_ip_proxy_agent_url() {
                return code_ip_proxy_agent_url;
            }

            public void setCode_ip_proxy_agent_url(String code_ip_proxy_agent_url) {
                this.code_ip_proxy_agent_url = code_ip_proxy_agent_url;
            }

            public String getCode_ip_proxy_usename() {
                return code_ip_proxy_usename;
            }

            public void setCode_ip_proxy_usename(String code_ip_proxy_usename) {
                this.code_ip_proxy_usename = code_ip_proxy_usename;
            }

            public String getCode_ip_proxy_password() {
                return code_ip_proxy_password;
            }

            public void setCode_ip_proxy_password(String code_ip_proxy_password) {
                this.code_ip_proxy_password = code_ip_proxy_password;
            }

            public String getCode_ip_proxy_ip() {
                return code_ip_proxy_ip;
            }

            public void setCode_ip_proxy_ip(String code_ip_proxy_ip) {
                this.code_ip_proxy_ip = code_ip_proxy_ip;
            }

            public String getCode_login_password() {
                return code_login_password;
            }

            public void setCode_login_password(String code_login_password) {
                this.code_login_password = code_login_password;
            }

            public String getCode_login_username() {
                return code_login_username;
            }

            public void setCode_login_username(String code_login_username) {
                this.code_login_username = code_login_username;
            }

            public String getIs_online() {
                return is_online;
            }

            public void setIs_online(String is_online) {
                this.is_online = is_online;
            }

            public int getCode_id() {
                return code_id;
            }

            public void setCode_id(int code_id) {
                this.code_id = code_id;
            }

            public String getBank_id() {
                return bank_id;
            }

            public void setBank_id(String bank_id) {
                this.bank_id = bank_id;
            }

            public String getAuto_login() {
                return auto_login;
            }

            public void setAuto_login(String auto_login) {
                this.auto_login = auto_login;
            }
        }
    }
}
