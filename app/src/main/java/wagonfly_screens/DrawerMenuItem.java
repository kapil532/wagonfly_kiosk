package wagonfly_screens;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.wagonfly.R;
import com.wagonfly.todolist.TodoListScreen;

import kartify_base.CommonClass;
import kartify_base.NetworkUrls;
import kartify_base.ProfilePrimeEditActivity;
import past_orders.PastOrderScreen;


@Layout(R.layout.drawer_item)
public class DrawerMenuItem implements NetworkUrls{

    public static final int DRAWER_MENU_ITEM_PROFILE = 1;
    public static final int DRAWER_MENU_ITEM_REQUESTS = 2;
    public static final int DRAWER_MENU_ITEM_GROUPS = 3;
    public static final int DRAWER_MENU_ITEM_MESSAGE = 4;
    public static final int DRAWER_MENU_ITEM_NOTIFICATIONS = 5;
    public static final int DRAWER_MENU_ITEM_SETTINGS = 6;
    public static final int DRAWER_MENU_ITEM_TERMS = 7;
    public static final int DRAWER_MENU_ITEM_LOGOUT = 8;
    public static final int DRAWER_MENU_ITEM_TODO = 9;

     int mMenuPosition;
     Context mContext;
     DrawerCallBack mCallBack;

    @View(R.id.itemNameTxt)
     TextView itemNameTxt;

    @View(R.id.itemIcon)
     ImageView itemIcon;

    public DrawerMenuItem(Context context, int menuPosition) {
        mContext = context;
        mMenuPosition = menuPosition;
    }

    @Resolve
    public void onResolved() {
        switch (mMenuPosition){
            case DRAWER_MENU_ITEM_PROFILE:
                itemNameTxt.setText("My Account");
                  itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.account));
                break;
            case DRAWER_MENU_ITEM_REQUESTS:
                itemNameTxt.setText("Privacy Policy");
                  itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_amonestation));
                break;
            case DRAWER_MENU_ITEM_GROUPS:
                itemNameTxt.setText("");
              itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_discuss_issue));
                break;
            case DRAWER_MENU_ITEM_MESSAGE:
                itemNameTxt.setText("My Orders");
             itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.order));
                break;
            case DRAWER_MENU_ITEM_NOTIFICATIONS:
                itemNameTxt.setText("Contact Us");
                itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.chat));
                break;
           /* case DRAWER_MENU_ITEM_SETTINGS:
                itemNameTxt.setText("Settings");
               // itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_settings_black_dp));
                break;*/
            case DRAWER_MENU_ITEM_TERMS:
                //itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_book_black_dp));
               itemNameTxt.setText("Terms");
                break;
//            case DRAWER_MENU_ITEM_LOGOUT:
//                itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_exit_to_app_black_dp));
//                itemNameTxt.setText("Logout");
//                break;
            case DRAWER_MENU_ITEM_TODO:
                itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.notifications));
                itemNameTxt.setText("ToDo List");
                break;
        }
    }

    @Click(R.id.mainView)
    public void onMenuItemClick(){
        switch (mMenuPosition){
            case DRAWER_MENU_ITEM_PROFILE:
//                Toast.makeText(mContext, "Profile", Toast.LENGTH_SHORT).show();
                Intent  mxt = new Intent(mContext, ProfilePrimeEditActivity.class);
                mContext.startActivity(mxt);
                if(mCallBack != null)mCallBack.onProfileMenuSelected();
                break;
            case DRAWER_MENU_ITEM_REQUESTS:
                AboutUsScreen.url = PRIVACY_POLICY;
                AboutUsScreen.title_text="Privacy Policy";
                Intent  mxats = new Intent(mContext, AboutUsScreen.class);
                mContext.startActivity(mxats);
                if(mCallBack != null)mCallBack.onRequestMenuSelected();
                break;
            case DRAWER_MENU_ITEM_GROUPS:
//                AboutUsScreen.url = "http://static.wagonfly.com/faq.html";
                AboutUsScreen.url = "https://www.wagonfly.com/privacy.html";
                AboutUsScreen.title_text="FAQ";
                Intent  mxatsa = new Intent(mContext, AboutUsScreen.class);
                mContext.startActivity(mxatsa);
                if(mCallBack != null)mCallBack.onGroupsMenuSelected();
                break;
            case DRAWER_MENU_ITEM_MESSAGE:
                Intent  mxat = new Intent(mContext, PastOrderScreen.class);
                mContext.startActivity(mxat);
//                Toast.makeText(mContext, "Messages", Toast.LENGTH_SHORT).show();
                if(mCallBack != null)mCallBack.onMessagesMenuSelected();
                break;
            case DRAWER_MENU_ITEM_NOTIFICATIONS:

                Intent  mxta = new Intent(mContext, CustomSupportScreenActivity.class);
                mContext.startActivity(mxta);
                /*Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto: contactus@wagonfly.com"));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "wagonfly lab Support");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");
                mContext.startActivity(Intent.createChooser(emailIntent, "Contact Us"));*/
                /*final Intent intent = new Intent(Intent.ACTION_SEND, Uri.parse("mailto:"));
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"contactus@wagonfly.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "wagonfly lab Support");
                intent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");
                mContext.startActivity(intent);*/
                if(mCallBack != null)mCallBack.onNotificationsMenuSelected();
                break;
            case DRAWER_MENU_ITEM_SETTINGS:
                Toast.makeText(mContext, "Settings", Toast.LENGTH_SHORT).show();
                if(mCallBack != null)mCallBack.onSettingsMenuSelected();
                break;
            case DRAWER_MENU_ITEM_TERMS:
                Toast.makeText(mContext, "Terms", Toast.LENGTH_SHORT).show();
                if(mCallBack != null)mCallBack.onTermsMenuSelected();
                break;
            case DRAWER_MENU_ITEM_LOGOUT:
               // Toast.makeText(mContext, "Logout", Toast.LENGTH_SHORT).show();
                CommonClass.clearData("divrt_user_authtoken", "USER", mContext);
                if(mCallBack != null)mCallBack.onLogoutMenuSelected();
                break;

            case DRAWER_MENU_ITEM_TODO:
                // Toast.makeText(mContext, "Logout", Toast.LENGTH_SHORT).show();
                Intent  mxtaa = new Intent(mContext, TodoListScreen.class);
                mContext.startActivity(mxtaa);
                if(mCallBack != null)mCallBack.onToDOMenuSelected();
                break;
        }
    }

    public void setDrawerCallBack(DrawerCallBack callBack)
    {
        mCallBack = callBack;
    }

    public interface DrawerCallBack{
        void onProfileMenuSelected();
        void onRequestMenuSelected();
        void onGroupsMenuSelected();
        void onMessagesMenuSelected();
        void onNotificationsMenuSelected();
        void onSettingsMenuSelected();
        void onTermsMenuSelected();
        void onLogoutMenuSelected();
        void onToDOMenuSelected();
    }
}