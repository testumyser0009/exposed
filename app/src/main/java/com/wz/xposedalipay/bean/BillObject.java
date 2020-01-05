package com.wz.xposedalipay.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
     * 账单信息对象
     */
    public class BillObject implements Parcelable {
        public String bizInNo;
        public String bizStateDesc;
        public String bizSubType;
        public String canDelete;
        public String bizType;
        public String consumeFee;
        public String consumeStatus;
        public String consumeTitle;
        public String createDesc;
        public String createTime;
        public String destinationUrl;
        public String gmtCreate;
        public String isAggregatedRec;
        public String memo;
        public String month;
        public String oppositeLogo;
        public String oppositeMemGrade;
        public String sceneId;
        public String categoryName;
 
        public BillObject() {
 
        }
 
        protected BillObject(Parcel in) {
            bizInNo = in.readString();
            bizStateDesc = in.readString();
            bizSubType = in.readString();
            canDelete = in.readString();
            bizType = in.readString();
            consumeFee = in.readString();
            consumeStatus = in.readString();
            consumeTitle = in.readString();
            createDesc = in.readString();
            createTime = in.readString();
            destinationUrl = in.readString();
            gmtCreate = in.readString();
            isAggregatedRec = in.readString();
            memo = in.readString();
            month = in.readString();
            oppositeLogo = in.readString();
            oppositeMemGrade = in.readString();
            sceneId = in.readString();
            categoryName = in.readString();
        }
 
        public static final Creator<BillObject> CREATOR = new Creator<BillObject>() {
            @Override
            public BillObject createFromParcel(Parcel in) {
                return new BillObject(in);
            }
 
            @Override
            public BillObject[] newArray(int size) {
                return new BillObject[size];
            }
        };
 
        @Override
        public String toString() {
            return "bizInNo:" + bizInNo + "，" +
                    "bizStateDesc:" + bizStateDesc + "，" +
                    "bizSubType:" + bizSubType + "，" +
                    "canDelete:" + canDelete + "，" +
                    "bizType:" + bizType + "，" +
                    "consumeFee:" + consumeFee + "，" +
                    "consumeStatus:" + consumeStatus + "，" +
                    "consumeTitle:" + consumeTitle + "，" +
                    "createDesc:" + createDesc + "，" +
                    "createTime:" + createTime + "，" +
                    "destinationUrl:" + destinationUrl + "，" +
                    "gmtCreate:" + gmtCreate + "，" +
                    "isAggregatedRec:" + isAggregatedRec + "，" +
                    "memo:" + memo + "，" +
                    "month:" + month + "，" +
                    "oppositeLogo:" + oppositeLogo + "，" +
                    "oppositeMemGrade:" + oppositeMemGrade + "，" +
                    "sceneId:" + sceneId + "，" +
                    "categoryName:" + categoryName + "\n";
        }
 
        @Override
        public int describeContents() {
            return 0;
        }
 
        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(bizInNo);
            dest.writeString(bizStateDesc);
            dest.writeString(bizSubType);
            dest.writeString(canDelete);
            dest.writeString(bizType);
            dest.writeString(consumeFee);
            dest.writeString(consumeStatus);
            dest.writeString(consumeTitle);
            dest.writeString(createDesc);
            dest.writeString(createTime);
            dest.writeString(destinationUrl);
            dest.writeString(gmtCreate);
            dest.writeString(isAggregatedRec);
            dest.writeString(memo);
            dest.writeString(month);
            dest.writeString(oppositeLogo);
            dest.writeString(oppositeMemGrade);
            dest.writeString(sceneId);
            dest.writeString(categoryName);
        }
    }