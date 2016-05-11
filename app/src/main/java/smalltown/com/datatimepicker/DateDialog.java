package smalltown.com.datatimepicker;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.Calendar;

import smalltown.com.datatimepicker.utils.WindowUtils;

/**
 * Created by cxz on 2015/12/21 0021.
 */
public class DateDialog extends Dialog {
    // 结果
    private TextView tvOk, tvCancle;
    private View view;
    private MyDatePickerDia year, month, day;
    private Calendar calendar = Calendar.getInstance();
    private TextView tvDate;
    private OnClickListener onClickListener;
    String format = "%02d";

    public DateDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_date, null);
        year = (MyDatePickerDia) view.findViewById(R.id.date_year);
        month = (MyDatePickerDia) view.findViewById(R.id.date_month);
        day = (MyDatePickerDia) view.findViewById(R.id.date_day);
        tvDate = (TextView) view.findViewById(R.id.tv_date);
        tvOk = (TextView) view.findViewById(R.id.ok);
        tvOk.setClickable(true);
        tvCancle = (TextView) view.findViewById(R.id.cancel);
        tvCancle.setClickable(true);
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.clickOk(tittleShow());
                }
                dismiss();
            }
        });
        tvCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    String now = String.valueOf(calendar.get(Calendar.YEAR)) + "年-"
                            + String.format(format, calendar.get(Calendar.MONTH) + 1) + "月-"
                            + String.format(format, calendar.get(Calendar.DAY_OF_MONTH)) + "日";
                    onClickListener.clickCancle(now);
                }
                dismiss();
            }
        });
        setContentView(view);
        resize();
    }

    /**
     * 不能放到onCreate中，放到onCreate 中再次打开日期不会更新
     */
    @Override
    protected void onStart() {
        super.onStart();
        yearSetting();
        monthSetting();
        daySetting();
        tittleShow();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    /**
     * 年的设置
     */
    private void yearSetting() {
        year.setInputType("年");
        year.setInputLenth(4);
        year.setEdInput(String.valueOf(calendar.get(Calendar.YEAR)));
        year.setOnClickListener(new MyDatePickerDia.OnClickListener() {
            @Override
            public void add(String inputStr) {
                yearAdd(inputStr);
                tittleShow();
            }

            @Override
            public void minus(String inputStr) {
                yearMinus(inputStr);
                tittleShow();
            }

            @Override
            public void textChange(Editable str) {
                tvDate.setText(str);
            }
        });
    }

    /**
     * 月的设置
     */
    private void monthSetting() {
        month.setInputType("月");
        month.setInputLenth(2);
        month.setEdInput(String.format(format, calendar.get(Calendar.MONTH) + 1));
        month.setOnClickListener(new MyDatePickerDia.OnClickListener() {
            @Override
            public void add(String inputStr) {
                monthAdd(inputStr);
                tittleShow();
            }

            @Override
            public void minus(String inputStr) {
                monthMinus(inputStr);
                tittleShow();
            }

            @Override
            public void textChange(Editable str) {

            }
        });
    }


    /**
     * 天的设置
     */
    private void daySetting() {
        day.setInputType("日");
        day.setInputLenth(2);
        day.setEdInput(String.format(format, calendar.get(Calendar.DAY_OF_MONTH)));
        day.setOnClickListener(new MyDatePickerDia.OnClickListener() {
            @Override
            public void add(String inputStr) {
                dayAdd(inputStr);
                tittleShow();
            }

            @Override
            public void minus(String inputStr) {
                dayMinus(inputStr);
                tittleShow();
            }

            @Override
            public void textChange(Editable str) {

            }
        });
    }


    /**
     * 年 +
     *
     * @param add
     */
    private void yearAdd(String add) {
        int input = 0;
        if (add.length() == 0 || "".equals(add) || Integer.parseInt(add) < 1900) {
            add = "1900";
            year.setEdInput(add);
        } else {
            input = Integer.parseInt(add);
            input++;
            if (input > 2100) {
                input = 2100;
            }
            yearChange(input);
            year.setEdInput(String.valueOf(input));
        }

    }

    /**
     * 年 -
     *
     * @param minus
     */
    private void yearMinus(String minus) {
        int input = 0;
        if (minus.length() == 0 || "".equals(minus) || Integer.parseInt(minus) < 1900) {
            minus = "1900";
            year.setEdInput(minus);
        } else {
            input = Integer.parseInt(minus);
            if (input > 2101) {
                input = 2101;
            }
            input--;
            if (input < 1900) {
                input = 1900;
            }
            yearChange(input);
            year.setEdInput(String.valueOf(input));
        }
    }

    /**
     * 月 +
     *
     * @param add
     */
    private void monthAdd(String add) {
        int input = 0;
        if ((add.length() == 0) || "".equals(add) || (Integer.parseInt(add) > 12) || (Integer.parseInt(add) < 1)) {
            add = String.format(format, calendar.get(Calendar.MONTH));
            month.setEdInput(add);
        } else {
            input = Integer.parseInt(add);
            if (input == 12) {
                input = 1;
                //月份变化对年的影响 +1
                yearAdd(year.getEdInput());
            } else {
                input++;
                //月份变化对日的影响
                monthChange(input);
            }
            month.setEdInput(String.format(format, input));
        }
    }

    /**
     * 月 -
     *
     * @param minus
     */
    private void monthMinus(String minus) {
        int input = 0;
        if ((minus.length() == 0) || "".equals(minus) || (Integer.parseInt(minus) > 12) || (Integer.parseInt(minus) < 1)) {
            minus = String.format(format, calendar.get(Calendar.MONTH));
            month.setEdInput(minus);
            return;
        }
        input = Integer.parseInt(minus);
        if (input == 1) {
            input = 12;
            //月份变化对年的影响 -1
            yearMinus(year.getEdInput());
        } else {
            input--;
            //月份变化对日的影响
            monthChange(input);
        }
        month.setEdInput(String.format(format, input));
    }


    /**
     * 日 +
     *
     * @param add
     */
    private void dayAdd(String add) {
        int y = Integer.parseInt(year.getEdInput());
        int m = Integer.parseInt(month.getEdInput());
        int d = Integer.parseInt(add);
        if ("".equals(add) || add.length() == 0 || d > 31 || d < 1) {
            month.setEdInput(String.format(format, calendar.get(Calendar.MONTH) + 1));
            day.setEdInput(String.format(format, calendar.get(Calendar.DAY_OF_MONTH)));
        } else if (d == 31) {
            monthAdd(String.format(format, m));
            day.setEdInput(String.format(format, 1));
        } else if (d == 30) {
            if (m == 1 || m == 3 || m == 5 || m == 7 || m == 8 || m == 10 || m == 12) {
                d++;
                day.setEdInput(String.format(format, d));
            } else {
                monthAdd(String.format(format, m));
                day.setEdInput(String.format(format, 1));
            }

        } else if (d == 29) {
            if (m == 2) {
                monthAdd(String.format(format, m));
                day.setEdInput(String.format(format, 1));
            } else {
                d++;
                day.setEdInput(String.format(format, d));
            }

        } else if (d == 28) {
            if (m == 2) {
                if (isLeapYear(y)) {
                    d++;
                    day.setEdInput(String.format(format, d));
                } else {
                    monthAdd(String.format(format, m));
                    day.setEdInput(String.format(format, 1));
                }
            } else {
                d++;
                day.setEdInput(String.format(format, d));
            }
        } else {
            d++;
            day.setEdInput(String.format(format, d));
        }

    }

    /**
     * 日 —
     *
     * @param minus
     */
    private void dayMinus(String minus) {
        int y = Integer.parseInt(year.getEdInput());
        int m = Integer.parseInt(month.getEdInput());
        int d = Integer.parseInt(minus);
        if ("".equals(minus) || minus.length() == 0 || d > 31 || d < 1) {
            day.setEdInput(String.format(format, calendar.get(Calendar.DAY_OF_MONTH)));
        } else if (d == 1) {
            //4，6，9，11四个月是30天，上一个月一号减1，变成30号
            if (m == 5 || m == 7 || m == 10 || m == 12) {
                day.setEdInput(String.format(format, 30));
            } else if (m == 3) {
                if (isLeapYear(y)) {
                    day.setEdInput(String.format(format, 29));
                } else {
                    day.setEdInput(String.format(format, 28));
                }
            } else {
                day.setEdInput(String.format(format, 31));
            }
            monthMinus(String.format(format, m));
        } else if (d == 31) {
            if (m == 2) {
                if (isLeapYear(y)) {
                    day.setEdInput(String.format(format, 29));
                } else {
                    day.setEdInput(String.format(format, 28));
                }
            } else if (m == 4 || m == 6 || m == 9 || m == 11) {
                day.setEdInput(String.format(format, 30));
            } else {
                d--;
                day.setEdInput(String.format(format, d));
            }
        } else if (d == 30) {
            if (m == 2 && !isLeapYear(y)) {
                day.setEdInput(String.format(format, 28));
            } else {
                d--;
                day.setEdInput(String.format(format, d));
            }
        } else {
            d--;
            day.setEdInput(String.format(format, d));
        }


    }

    /**
     * 月份改变对日的影响
     *
     * @param month 月份
     */
    private void monthChange(int month) {
        //2月情况特殊，需分平年闰年，特殊处理
        if (month == 2) {
            if (isLeapYear(Integer.parseInt(year.getEdInput()))) {
                if (Integer.parseInt(day.getEdInput()) > 29) {
                    day.setEdInput("29");
                }
            } else {
                if (Integer.parseInt(day.getEdInput()) > 28) {
                    day.setEdInput("28");
                }
            }
            //4，6，9，11一个月最多三十天
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            if (Integer.parseInt(day.getEdInput()) > 30) {
                day.setEdInput("30");
            }
        }
    }

    /**
     * 年份改变对日的影响
     *
     * @param year 年份
     */
    private void yearChange(int year) {
        int m = Integer.parseInt(month.getEdInput());
        int d = Integer.parseInt(day.getEdInput());
        if (m == 2) {
            if (isLeapYear(year)) {
                if (d > 29) {
                    day.setEdInput("29");
                }
            } else {
                if (d > 28) {
                    day.setEdInput("28");
                }
            }
        } else if (m == 4 || m == 6 || m == 9 || m == 11) {
            if (d > 30) {
                day.setEdInput("30");
            }
        }
    }

    /**
     * 判断是不是闰年
     *
     * @param y 输入的年份
     * @return 返回的解或。 ture 闰年    false 平年
     */
    private boolean isLeapYear(int y) {
        if (y % 4 == 0 && y % 100 != 0 || y % 400 == 0) {
            return true;
        }
        return false;
    }

    /**
     * dialog 的宽高的设置
     */
    private void resize() {
        Window dialogWindow = this.getWindow();
        int width = WindowUtils.getScreenWidth(dialogWindow);
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.width = (int) (width * 0.85); // 宽度设置为屏幕的0.85
        dialogWindow.setAttributes(p);
    }

    /**
     * 标题的展示
     */
    private String tittleShow() {
        String Y = year.getEdInput();
        String M = month.getEdInput();
        String D = day.getEdInput();
        String date = Y + "年-" + M + "月-" + D + "日";
        String dateAll = date + "\t" + getWeek(Integer.parseInt(Y), Integer.parseInt(M), Integer.parseInt(D));
        tvDate.setText(dateAll);
        return date;

    }

    /**
     * 基姆拉尔森计算公式  Week=(Day + 2*Month + 3*(Month+1）/5 + Year + Year/4 - Year/100 + Year/400) % 7
     *
     * @param y 年
     * @param m 月  1，2月需要按13月，14日
     * @param d 日
     * @return
     */
    private String getWeek(int y, int m, int d) {
        String Week = "星期";
        if (m == 1) {
            m = 13;
        }
        if (m == 2) {
            m = 14;
        }
        int week = (d + 2 * m + 3 * (m + 1) / 5 + y + y / 4 - y / 100 + y / 400) % 7;
        switch (week) {
            case 0:
                Week += "一";
                break;
            case 1:
                Week += "二";
                break;
            case 2:
                Week += "三";
                break;
            case 3:
                Week += "四";
                break;
            case 4:
                Week += "五";
                break;
            case 5:
                Week += "六";
                break;
            case 6:
                Week += "日";
                break;
            default:
                break;
        }
        return Week;
    }

    /**
     * 接口
     */
    public interface OnClickListener {
        void clickOk(String ok);

        void clickCancle(String cancle);
    }

    /**
     * 设置取消按钮的显示内容
     *
     * @param cancle 显示的内容
     */
    public void setTextCancle(String cancle) {
        tvCancle.setText(cancle);
    }

    /**
     * 设置确定按钮的显示内容
     *
     * @param ok
     */
    public void steTextOk(String ok) {
        tvOk.setText(ok);
    }

}
