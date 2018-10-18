package com.zhao.livegiftanimdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout ll_double_hit;//动画容器

    private int mGift1Num1Count;
    private int mGift2Num1Count;
    private int mGift3Num1Count;
    private int mGift1Num2Count;
    private int mGift2Num2Count;
    private int mGift3Num2Count;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ll_double_hit = findViewById(R.id.ll_double_hit);

        DoubleHitManager.init(this);
        DoubleHitManager.addGiftContainer(ll_double_hit);

        findViewById(R.id.gift1_num1).setOnClickListener(this);
        findViewById(R.id.gift2_num1).setOnClickListener(this);
        findViewById(R.id.gift3_num1).setOnClickListener(this);
        findViewById(R.id.gift1_num2).setOnClickListener(this);
        findViewById(R.id.gift2_num2).setOnClickListener(this);
        findViewById(R.id.gift3_num2).setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        DoubleHitManager.release();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.gift1_num1:
                mGift1Num1Count++;
                addGift(GiftConstant.GIFT_ID_1, 1, mGift1Num1Count);
                break;
            case R.id.gift2_num1:
                mGift2Num1Count++;
                addGift(GiftConstant.GIFT_ID_2, 1, mGift2Num1Count);
                break;
            case R.id.gift3_num1:
                mGift3Num1Count++;
                addGift(GiftConstant.GIFT_ID_3, 1, mGift3Num1Count);
                break;
            case R.id.gift1_num2:
                mGift1Num2Count++;
                addGift(GiftConstant.GIFT_ID_1, 10, mGift1Num2Count);
                break;
            case R.id.gift2_num2:
                mGift2Num2Count++;
                addGift(GiftConstant.GIFT_ID_2, 10, mGift2Num2Count);
                break;
            case R.id.gift3_num2:
                mGift3Num2Count++;
                addGift(GiftConstant.GIFT_ID_3, 10, mGift3Num2Count);
                break;
        }
    }

    private void addGift(String giftId, int giftNum, int doubleHitNum){
        GiftInfoBean infoBean = new GiftInfoBean();
        infoBean.setUserId("1");
        infoBean.setUserName("波多野结衣");
        infoBean.setUserIcon("");
        infoBean.setGiftNum(giftNum);
        infoBean.setGiftId(giftId);
        String giftName = "";
        if(GiftConstant.GIFT_ID_1.equals(giftId)){
            giftName = "红钻";
        }else if(GiftConstant.GIFT_ID_2.equals(giftId)){
            giftName = "蓝钻";
        }else{
            giftName = "星星";
        }
        infoBean.setGiftName(giftName);
        infoBean.setDoubleHitNum(doubleHitNum);

        DoubleHitManager.addGiftMessage(infoBean);
    }
}
