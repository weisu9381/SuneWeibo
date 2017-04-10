package weibo.suwei.com.suneweibo.ui.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;
import com.sina.weibo.sdk.openapi.models.Status;
import com.sina.weibo.sdk.openapi.models.User;

import java.util.Date;

import weibo.suwei.com.suneweibo.R;
import weibo.suwei.com.suneweibo.utils.DateUtils;

/**
 * 填充 微博信息流的内容：从上至下：1.标题栏(头像，用户名，发布时间，来自什么手机)
 * 2.转发微博内容 + 原创微博内容
 * 3.显示的图片
 * 4.底部栏（转发，评论，点赞）
 * Created by suwei on 2017/4/4.
 */

public class FillContent {

    /**
     * 使用 universal-Image-Loader的图片缓存策略 DisplayImageOptions
     */
    private static DisplayImageOptions mAvatarOptions = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.avator_default)//设置图片在下载期间显示的图片
            .showImageForEmptyUri(R.drawable.avator_default)//设置图片Uri为空或是Uri错误的时候显示的图片
            .showImageOnFail(R.drawable.avator_default)//设置图片加载/解码过程中错误时候显示的图片
            .cacheInMemory(true)//设置下载的图片是否缓存在内存中
            .cacheOnDisk(true)//设置下载的图片是否缓存在SD卡中
            .imageScaleType(ImageScaleType.EXACTLY)//EXACTLY :图像将完全按比例缩小的目标大小
            .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型
            //默是ARGB_8888，使用RGB_565会比使用ARGB_8888少消耗2倍的内存
            .displayer(new CircleBitmapDisplayer(Color.WHITE,5))//图片加载好后渐入的动画时间，可能会出现闪动
            //参数，边框颜色，边框宽度
            .build();//构建完成

    /**
     * 填充 用户头像
     * @param profile_img 用户头像
     * @param profile_img_verified  认证加V图片
     */
    public static void fillProfileImg(Context context, User user, ImageView profile_img,
                                      ImageView profile_img_verified){
        //如果是 认证用户 ,添加 头像 左下角的 v
        if (user.verified){
            switch (user.verified_type){
                case 0: // vip
                    profile_img_verified.setImageResource(R.drawable.avatar_vip);
                    break;
                case 1:
                case 2:
                case 3: //企业级 vip
                    profile_img_verified.setImageResource(R.drawable.avatar_enterprise_vip);
                    break;
                case 220: //基层群众
                    profile_img_verified.setImageResource(R.drawable.avatar_grassroot);
                    break;
            }
        }else{
            //设置Invisible,不可见，但还占用空间，设置gone,隐藏，控件不占空间
            profile_img_verified.setVisibility(View.GONE);
        }
        //显示 用户高清头像
        ImageLoader.getInstance().displayImage(user.avatar_hd,profile_img,mAvatarOptions);
    }

    /**
     *
     *
     */
    public static void setWeiboName(TextView textView,User user){
        //user.remark 用户备注名
        if(user.remark!=null&&user.remark.length()>0){
            textView.setText(user.remark);
        }else{
            textView.setText(user.name);
        }
    }
    public static void setWeiboTime(Context context, TextView textView, Status status){
        Date date =DateUtils.parseDate(status.created_at,DateUtils.WeiBo_ITEM_DATE_FORMAT);
        textView.setText(date+"    ");
    }
    public static void setWeiboComeFrom(TextView textView,Status status){
        if(status==null){
            textView.setText("");
            return;
        }
        if(!TextUtils.isEmpty(status.source)){
            textView.setText("来自"+status.source);
        }else{
            textView.setText("");
        }
    }

    /**
     * 前面几个的方法  都是为了 完成 titlebar :
     *
     */
    public static void fillTitleBar(Context context, Status status, ImageView profile_img,
                                    ImageView profile_img_verified, TextView profile_name,
                                    TextView profile_time, TextView profile_comfrom){
        //使用 status.user
        fillProfileImg(context,status.user,profile_img,profile_img_verified);
        setWeiboName(profile_name,status.user);
        setWeiboTime(context,profile_time,status);
        setWeiboComeFrom(profile_comfrom,status);
    }
}
