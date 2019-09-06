package wagonfly_screens;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.wagonfly.R;

import kartify_base.CommonClass;
import kartify_base.ConstantValues;
import kartify_base.NetworkUrls;
import smart_shopping_choose_option.SelectionScreen;


/**
 * Created by kapil on 19/08/17.
 */
@NonReusable
@Layout(R.layout.drawer_header)
public class DrawerHeader implements ConstantValues, NetworkUrls
{
    Context ctx;
    @View(R.id.profileImageView)
     ImageView profileImage;

    @View(R.id.nameTxt)
     TextView nameTxt;

    @View(R.id.emailTxt)
     TextView emailTxt;
    public DrawerHeader(Context ctx)
    {
        this.ctx=ctx;
    }
    @View(R.id.setting)
     ImageView setting;


   @Resolve
    public void onResolved()
    {


        if(CommonClass.returnGenericData( NAME, NAME_PREF,ctx ).length()>4)
        {
            nameTxt.setText(CommonClass.returnGenericData( NAME, NAME_PREF,ctx ));

        }
        else
        {
            nameTxt.setVisibility(android.view.View.GONE);
        }

        emailTxt.setText(CommonClass.returnGenericData( EMAIL, EMAIL_PREF,ctx ));


         /* setting.setOnClickListener(new android.view.View.OnClickListener() {
              @Override
              public void onClick(android.view.View view) {

              }
          });*/

    }


   /*// @Click(R.id.setting)
    private void onClick(View view)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent setting = new Intent(ctx, SelectionScreen.class);
                ctx.startActivity(setting);
            }
        }).start();

    }*/
}
