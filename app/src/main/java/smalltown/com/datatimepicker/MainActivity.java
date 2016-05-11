package smalltown.com.datatimepicker;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
    private Button bt1;
    private Button bt2;
    private DateDialog dialogDate;
    private TimeDialog dialogTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        bt1 = (Button) findViewById(R.id.bt1);
        bt2 = (Button) findViewById(R.id.bt2);
        dialogDate = new DateDialog(this, R.style.picture_dialog);
        dialogTime = new TimeDialog(this, R.style.picture_dialog);

        dialogDate.setOnClickListener(new DateDialog.OnClickListener() {
            @Override
            public void clickOk(String ok) {
                bt1.setText(ok);
            }

            @Override
            public void clickCancle(String cancle) {
                bt1.setText(cancle);
            }
        });
        dialogTime.setOnClickListener(new TimeDialog.OnClickListener() {
            @Override
            public void clickOk(String ok) {
                bt2.setText(ok);
            }

            @Override
            public void clickCancle(String cancle) {
                bt2.setText(cancle);
            }
        });
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDate.show();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogTime.show();
                dialogTime.setTittle("请选择");
            }
        });
    }


}
