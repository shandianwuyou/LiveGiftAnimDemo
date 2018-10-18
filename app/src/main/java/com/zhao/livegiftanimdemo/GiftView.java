package com.zhao.livegiftanimdemo;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 创建者 ：赵鹏   时间：2018/10/5
 */
public class GiftView extends RelativeLayout{

    private GiftInfoBean mBean;
    private TextView hitNumView;
    private TextView giftNumView;
    private LinearLayout numContainer;
    private Animation mGiftInAnim;

    private boolean isNeedFast;

    public GiftView(Context context, GiftInfoBean bean) {
        super(context, null);
        this.mBean = bean;
        init();
    }

    public GiftView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GiftView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.view_room_gift, this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(lp);

        numContainer = findViewById(R.id.double_num_container);
        CircleImageView userIcon = findViewById(R.id.gift_user_icon);
        TextView userName = findViewById(R.id.gift_user_name);
        TextView content = findViewById(R.id.gift_content);
        hitNumView = findViewById(R.id.tv_double_hie_num);
        giftNumView = findViewById(R.id.tv_gift_num);
        ImageView giftImageView = findViewById(R.id.iv_gift_icon);

        userIcon.setImageResource(R.mipmap.icon_user);

        userName.setText(mBean.getUserName());
        content.setText(getContext().getResources().getString(R.string.send_gift_view_content, String.valueOf(mBean.getGiftNum()), mBean.getGiftName()));
        String giftId = mBean.getGiftId();
        if("1000".equals(giftId)){
            giftImageView.setImageResource(R.mipmap.icon_zuan_da);
        }else if("1001".equals(giftId)){
            giftImageView.setImageResource(R.mipmap.icon_lanzuan_da);
        }else if("1003".equals(giftId)){
            giftImageView.setImageResource(R.mipmap.icon_xing_da);
        }
        hitNumView.setTag(1);

        mBean.setUpdateTime(System.currentTimeMillis());
        setTag(mBean);

        mGiftInAnim = new AlphaAnimation(0.0f, 1.0f);
        mGiftInAnim.setDuration(300);
        mGiftInAnim.setFillAfter(true);
        mGiftInAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startDoubleHitAnim();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public void startLayoutInAnim(){
        startAnimation(mGiftInAnim);
    }

    public void setDoubleHitNum(int num){
        if(hitNumView != null){
            hitNumView.setText(String.valueOf(num));
            hitNumView.setTag(num);
            invalidate();
        }
    }

    public int getDoubleHitNum(){
        if(hitNumView != null){
            return (int) hitNumView.getTag();
        }else{
            return 0;
        }
    }

    public void setGiftNum(String num){
        if(giftNumView != null){
            giftNumView.setText(num);
            invalidate();
        }
    }

    public void setIsFast(boolean isFast){
        this.isNeedFast = isFast;
    }

    /**
     * 连击动画
     *
     * @param
     */
    public  void startDoubleHitAnim() {
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(numContainer, "scaleX", isNeedFast?1.3f:1.6f, 1.0f);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(numContainer, "scaleY", isNeedFast?1.3f:1.6f, 1.0f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(isNeedFast?100:300);
        animSet.setInterpolator(new OvershootInterpolator());
        animSet.playTogether(anim1, anim2);
        animSet.start();
        ((GiftInfoBean) getTag()).setDoubleHitAnimOver(false);
        animSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ((GiftInfoBean) getTag()).setUpdateTime(System.currentTimeMillis());//设置时间标记
                ((GiftInfoBean) getTag()).setDoubleHitAnimOver(true);
                if(getDoubleHitNum() < ((GiftInfoBean) getTag()).getDoubleHitNum()){
                    setDoubleHitNum(getDoubleHitNum() + 1);
                    startDoubleHitAnim();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }


}
