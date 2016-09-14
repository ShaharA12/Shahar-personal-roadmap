package test.test.com.myapplication.widgets;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.view.Display;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;
import android.animation.Animator.AnimatorListener;
import android.app.Activity;
import android.graphics.Point;

public class Animations {

    private static final int FLOATING_DURATION = 1000;
    private static final int FLOATING_DIFF = 20;

    public static AnimatorSet floatingAnimation(View view, float currentY) {
        ObjectAnimator anim = ObjectAnimator.ofFloat(view, "y", currentY + FLOATING_DIFF, currentY - FLOATING_DIFF);
        anim.setDuration(FLOATING_DURATION);
        anim.setRepeatCount(Animation.INFINITE);
        anim.setRepeatMode(Animation.REVERSE);
        AnimatorSet out = new AnimatorSet();
        out.play(anim);
        out.start();
        return out;
    }

    private static final float HEARTBEAT_FROM = 1.0f;
    private static final float HEARTBEAT_TO = 1.05f;

    public static AnimatorSet heartBeatAnimation(View view, int speed, long delay, int repeat) {
        ObjectAnimator outX = ObjectAnimator.ofFloat(view, "scaleX", HEARTBEAT_FROM, HEARTBEAT_TO);
        ObjectAnimator outY = ObjectAnimator.ofFloat(view, "scaleY", HEARTBEAT_FROM, HEARTBEAT_TO);
        outX.setDuration(speed);
        outY.setDuration(speed);
        outX.setRepeatCount(repeat);
        outX.setRepeatMode(Animation.REVERSE);
        outY.setRepeatCount(repeat);
        outY.setRepeatMode(Animation.REVERSE);
        AnimatorSet out = new AnimatorSet();
        out.setStartDelay(delay);
        out.play(outX).with(outY);
        out.start();
        return out;
    }

    public static AnimatorSet rotationAnimation(View view, long duration, float startAngle, float endAngle) {
        ObjectAnimator rotation = ObjectAnimator.ofFloat(view, "rotation", startAngle, endAngle);
        rotation.setDuration(duration);
        rotation.setInterpolator(new LinearInterpolator());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(rotation);
        animatorSet.start();
        return animatorSet;
    }

    public static void scaleXAnimation(View view, float startValue, float endValue, int speed, boolean interpolator, final Runnable runAtStart, final Runnable runAtEnd) {
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleX", startValue, endValue);
        scaleY.setDuration(speed);
        if (interpolator) {
            scaleY.setInterpolator(new BounceInterpolator());
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleY);
        if (runAtEnd != null || runAtStart != null) {
            animatorSet.addListener(new AnimatorListener() {

                @Override
                public void onAnimationStart(Animator animation) {
                    if (runAtStart != null) {
                        runAtStart.run();
                    }
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (runAtEnd != null) {
                        runAtEnd.run();
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    // TODO Auto-generated method stub

                }
            });
        }
        animatorSet.start();
    }

    public static void scaleYAnimation(View view, float startValue, float endValue, int speed,boolean interpolator, final Runnable runAtEnd) {
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", startValue, endValue);
        scaleY.setDuration(speed);
        scaleY.setInterpolator(new BounceInterpolator());
        AnimatorSet animatorSet = new AnimatorSet();
        if (interpolator) {
            scaleY.setInterpolator(new BounceInterpolator());
        }
        if (runAtEnd != null) {
            animatorSet.addListener(new AnimatorListener() {

                @Override
                public void onAnimationStart(Animator animation) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (runAtEnd != null) {
                        runAtEnd.run();
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    // TODO Auto-generated method stub

                }
            });
        }
        animatorSet.start();
    }

    public static AnimatorSet scaleAnimation(View view, float startValue, float endValue, int speed, int repeat, long delay, TimeInterpolator interpolator, final Runnable runAtEnd) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", startValue, endValue);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", startValue, endValue);
        scaleX.setDuration(speed);
        scaleY.setDuration(speed);
        if (interpolator != null) {
            scaleX.setInterpolator(interpolator);
            scaleY.setInterpolator(interpolator);
        }
        scaleX.setRepeatCount(repeat);
        scaleY.setRepeatCount(repeat);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setStartDelay(delay);
        animatorSet.play(scaleX).with(scaleY);

        if (runAtEnd != null) {
            animatorSet.addListener(new AnimatorListener() {

                @Override
                public void onAnimationStart(Animator animation) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    runAtEnd.run();
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    // TODO Auto-generated method stub

                }
            });
        }
        animatorSet.start();
        return animatorSet;
    }

    public static AnimatorSet scaleAnimation2(final View view, final float startValue, final float endValue, final int speed, final long delay, final boolean start) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", startValue, endValue);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", startValue, endValue);
        scaleX.setDuration(speed);
        scaleY.setDuration(speed);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setStartDelay(delay);
        animatorSet.play(scaleX).with(scaleY);

        animatorSet.addListener(new AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (start) {
                    scaleAnimation2(view, endValue, startValue, speed, 100, false);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // TODO Auto-generated method stub

            }
        });
        animatorSet.start();
        return animatorSet;
    }

    public static void alphaAnimation(View view, boolean open, float startValue, float endValue, int speed, long delay, final Runnable runAtEnd) {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", startValue, endValue);
        view.setVisibility(View.VISIBLE);
        if (open) {
            alpha = ObjectAnimator.ofFloat(view, "alpha", endValue, startValue);
        }

        alpha.setDuration(speed);
        alpha.setInterpolator(new AccelerateDecelerateInterpolator());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setStartDelay(delay);
        animatorSet.play(alpha);
        if (runAtEnd != null) {
            animatorSet.addListener(new AnimatorListener() {

                @Override
                public void onAnimationStart(Animator animation) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    runAtEnd.run();

                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    // TODO Auto-generated method stub

                }
            });
        }
        animatorSet.start();
    }

    public static void setConnectingAnimation(View view) {
        RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        view.setVisibility(View.VISIBLE);
        rotate.setDuration(1000);
        rotate.setRepeatCount(100);
        rotate.setInterpolator(new LinearInterpolator());
        rotate.setFillEnabled(true);
        view.startAnimation(rotate);
    }

    public static void setTranslationAnimation(final View view, boolean open, int x, int y, int speed, long delay, TimeInterpolator interpolator, final Runnable runAtEnd) {
        int startX = 0;
        int endX = 0;
        int startY = 0;
        int endY = 0;
        if (open) {
            endY = y;
            endX = x;
        } else {
            startY = y;
            startX = x;
        }
        ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(view, "translationX", startX, endX);
        ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(view, "translationY", startY, endY);
        if (interpolator != null) {
            objectAnimatorX.setInterpolator(interpolator);
            objectAnimatorY.setInterpolator(interpolator);
        }
        objectAnimatorY.setDuration(speed);
        objectAnimatorX.setDuration(speed);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(objectAnimatorY).with(objectAnimatorX);
        if (runAtEnd != null) {
            animatorSet.addListener(new AnimatorListener() {

                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    runAtEnd.run();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }
            });
        }
        animatorSet.setStartDelay(delay);
        animatorSet.start();
    }

    public static void backgroundAnimation(boolean open, final View view, int speed) {
        Integer colorFrom = android.graphics.Color.parseColor("#070707");
        Integer colorTo = android.graphics.Color.parseColor("#2F2F2F");
        ValueAnimator colorAnimation;
        if (open)
            colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorTo, colorFrom);
        else
            colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setDuration(speed);
        colorAnimation.addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                view.setBackgroundColor((Integer) animator.getAnimatedValue());
            }
        });
        colorAnimation.start();
    }

    public static int getScreenWidth(Activity activity) {

        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    public static int getScreenHeight(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }

    public static class ResizeAnimation extends Animation {
        private View mView;
        private float mToHeight;
        private float mFromHeight;

        private float mToWidth;
        private float mFromWidth;

        public ResizeAnimation(View v, float fromWidth, float fromHeight, float toWidth, float toHeight) {
            mToHeight = toHeight;
            mToWidth = toWidth;
            mFromHeight = fromHeight;
            mFromWidth = fromWidth;
            mView = v;
            setDuration(150);
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            float height =
                    (mToHeight - mFromHeight) * interpolatedTime + mFromHeight;
            float width = (mToWidth - mFromWidth) * interpolatedTime + mFromWidth;
            android.view.ViewGroup.LayoutParams p = mView.getLayoutParams();
            p.height = (int) height;
            p.width = (int) width;
            mView.requestLayout();
        }

        public static class DecelerateAccelerateInterpolator implements Interpolator {
            private float mFactor = 1.0f;

            public DecelerateAccelerateInterpolator() {
            }

            public DecelerateAccelerateInterpolator(float factor) {
                mFactor = factor;
            }

            public float getInterpolation(float t) {
                return (float) (1 - Math.pow(1 - (2 * t), 3)) / 2;
            }
        }
    }
}
