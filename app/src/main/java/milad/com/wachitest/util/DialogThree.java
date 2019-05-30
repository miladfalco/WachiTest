package milad.com.wachitest.util;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import milad.com.wachitest.R;

public class DialogThree {
    private Activity activity;
    private ImageView img_one, img_two;

    public DialogThree(Activity activity) {
        this.activity = activity;
    }

    public Dialog dialogBuild() {
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.result_dialog_three);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        img_one = dialog.findViewById(R.id.img_dialog_one);
        img_two = dialog.findViewById(R.id.img_dialog_two);
        img_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        return dialog;
    }
}

