package com.fd.xposed.param;

import java.util.List;


public class SubmitData {
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String trading_time;
        private String order_money;
        private String transfer_type;
        private String reciprocal_name;

        public String getTrading_time() {
            return trading_time;
        }

        public void setTrading_time(String trading_time) {
            this.trading_time = trading_time;
        }

        public String getOrder_money() {
            return order_money;
        }

        public void setOrder_money(String order_money) {
            this.order_money = order_money;
        }

        public String getTransfer_type() {
            return transfer_type;
        }

        public void setTransfer_type(String transfer_type) {
            this.transfer_type = transfer_type;
        }

        public String getReciprocal_name() {
            return reciprocal_name;
        }

        public void setReciprocal_name(String reciprocal_name) {
            this.reciprocal_name = reciprocal_name;
        }
    }
}
