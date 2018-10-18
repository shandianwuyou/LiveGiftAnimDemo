package com.zhao.livegiftanimdemo;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 创建者 ：赵鹏   时间：2018/10/5
 */
public class DoubleHitManager {

    private static ArrayList<GiftInfoBean> mDataList = new ArrayList<>();
    private static Context mContext;

    //礼物飞出动画
    private static Animation mGiftLayoutOutAnim;

    //父控件
    private static LinearLayout mGiftContainer;

    //礼物定时器执行间隔
    private static final int mCheckInterval = 200;

    //礼物无更新后的存在时间
    private static final int mGiftClearInterval = 4000;

    //同时存在的最大礼物数目
    private static final int mGiftMaxNumber = 2;

    public static void init(Context context){
        mContext = context;
        mGiftLayoutOutAnim = new AlphaAnimation(1.0f, 0.0f);
        mGiftLayoutOutAnim.setFillAfter(true);
    }

    public static void addGiftContainer(LinearLayout contaienr){
        mGiftContainer = contaienr;
    }

    public static void addGiftMessage(GiftInfoBean bean){
        mDataList.add(bean);
        if(mControlTimer == null && mGiftContainer != null && mContext != null){
            startTimer();
        }
    }

    private static Timer mControlTimer;

    public static void startTimer(){
        mControlTimer = new Timer();
        mControlTimer.schedule(new TimerTask() {
            @Override
            public void run() {

                ((Activity) mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        for(int i = 0; i < mGiftContainer.getChildCount(); i++){//移除过期的view
                            View view = mGiftContainer.getChildAt(i);
                            GiftInfoBean bean = (GiftInfoBean) view.getTag();
                            if(System.currentTimeMillis() - bean.getUpdateTime() >= mGiftClearInterval){
                                removeAnimalView(i);
                            }
                        }

                        if(mDataList.size() > 0){
                            GiftInfoBean bean = mDataList.get(0);
                            GiftView giftView = (GiftView) findViewByBean(bean);
                            if(giftView == null){//没有view
                                synchronized (this){
                                    if(mGiftContainer.getChildCount() < mGiftMaxNumber){//添加
                                        giftView = createGiftView(bean);
                                        mGiftContainer.addView(giftView);
                                        mGiftContainer.invalidate();
                                    }else{
                                        mGiftContainer.removeViewAt(0);
                                        giftView = createGiftView(bean);
                                        mGiftContainer.addView(giftView);
                                        mGiftContainer.invalidate();
                                    }
                                }
                            }else{//已经有view
                                giftView.setIsFast(checkIsFast());
                                GiftInfoBean tagBean = (GiftInfoBean) giftView.getTag();// 原来的礼物view的信息
                                tagBean.setDoubleHitNum(Integer.valueOf(bean.getDoubleHitNum()));
                                if (tagBean.isDoubleHitAnimOver()) {
                                    // 连击动画结束的，重启动画，
                                    giftView.setDoubleHitNum(giftView.getDoubleHitNum() + 1);
                                    giftView.startDoubleHitAnim();
                                }
                            }
                            mDataList.remove(0);
                        }
                    }
                });
            }
        }, 0, mCheckInterval);
    }

    private static GiftView createGiftView(GiftInfoBean bean){
        GiftView giftView = new GiftView(mContext, bean);
        giftView.setGiftNum(String.valueOf(bean.getGiftNum()));
        giftView.setDoubleHitNum(bean.getDoubleHitNum());
        giftView.setIsFast(checkIsFast());
        giftView.startLayoutInAnim();
        return giftView;
    }

    public static boolean checkIsFast(){
        return mDataList.size() > 5;
    }

    /**
     * 根据bean寻找view
     *
     * @param bean
     * @return
     */
    private static View findViewByBean(GiftInfoBean bean) {
        for (int i = 0; i < mGiftContainer.getChildCount(); i++) {
            GiftInfoBean giftMessage = (GiftInfoBean) mGiftContainer.getChildAt(i).getTag();
            if (giftMessage.getUserId().equals(bean.getUserId()) && giftMessage.getGiftId().equals(bean.getGiftId())
                    && giftMessage.getGiftNum() == bean.getGiftNum()) {
                return mGiftContainer.getChildAt(i);
            }
        }
        return null;
    }

    /**
     * 删除动画view
     */
    private static void removeAnimalView(final int index) {
        if (index >= mGiftContainer.getChildCount()) {
            return;
        }
        final View removeView = mGiftContainer.getChildAt(index);
        mGiftLayoutOutAnim.setDuration(300);
        mGiftLayoutOutAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        mGiftContainer.removeViewAt(index);
                        if (mDataList.size() == 0 && mGiftContainer.getChildCount() == 0 && mControlTimer != null) {
                            mControlTimer.cancel();
                            mControlTimer = null;
                        }
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        ((Activity) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(!((GiftInfoBean)removeView.getTag()).isInExitAnim()){
                    removeView.startAnimation(mGiftLayoutOutAnim);
                    ((GiftInfoBean)removeView.getTag()).setInExitAnim(true);
                }
            }
        });
    }

    public static void release(){
        if(mControlTimer != null){
            mControlTimer.cancel();
            mControlTimer = null;
        }
    }


}
