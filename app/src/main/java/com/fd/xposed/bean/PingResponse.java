package com.fd.xposed.bean;

public class PingResponse {


    /**
     * message : 请求成功
     * status : 1 //错误返回0 成功返回1  兄弟
     * result : {"page":1,"need_data":0}
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
         * page : 1
         * need_data : 0
         */

        private int page;
        private String need_data;

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public String getNeed_data() {
            return need_data;
        }

        public void setNeed_data(String need_data) {
            this.need_data = need_data;
        }
    }
}
