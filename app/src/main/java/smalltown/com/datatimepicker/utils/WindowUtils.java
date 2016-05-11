package smalltown.com.datatimepicker.utils;

import android.util.DisplayMetrics;
import android.view.Window;

/**
 * Window Util
 * 
 * @author <a href="#" target="_blank">Kyle</a> 2015-11-04
 */
public class WindowUtils {

	/**
	 * 窗口信息，包括densityDpi\density\widthPixels\heightPixels
	 * 
	 * @param activity
	 * @return
	 */
	public static DisplayMetrics getDisplayMetrics(Window window) {
		DisplayMetrics localDisplayMetrics = new DisplayMetrics();
		window.getWindowManager().getDefaultDisplay()
				.getMetrics(localDisplayMetrics);
		return localDisplayMetrics;
	}

	/**
	 * 获取当前Window的宽
	 * <ul>
	 * <li> {@link #getDisplayMetrics(Window window)} </li>
	 * </ul>
	 * 
	 * @param window
	 *            当前窗口
	 * @return 当前窗口的宽度
	 */
	public static int getScreenWidth(Window window) {
		DisplayMetrics dm = getDisplayMetrics(window);
		return dm.widthPixels;
	}

	/**
	 * 获取当前Window的高
	 * <ul>
	 * <li> {@link #getDisplayMetrics(Window window)} </li>
	 * </ul>
	 * 
	 * @param window
	 *            当前窗口
	 * @return 当前窗口的高度
	 */
	public static int getScreenHeight(Window window) {
		DisplayMetrics dm = getDisplayMetrics(window);
		return dm.heightPixels;
	}

	/**
	 * 获取当前Window的像素密度
	 * <ul>
	 * <li> {@link #getDisplayMetrics(Window window)} </li>
	 * </ul>
	 * 
	 * @param window
	 *            当前窗口
	 * @return 当前Window的像素密度
	 */
	public static int getScreenDensityDpi(Window window) {
		DisplayMetrics dm = getDisplayMetrics(window);
		return dm.densityDpi;
	}

	/**
	 * 获取当前Window的屏幕密度
	 * <ul>
	 * <li> {@link #getDisplayMetrics(Window window)} </li>
	 * </ul>
	 * <strong>
	 * 	density = densityDpi / 160
	 * </strong>
	 * 
	 * @param window
	 *            当前窗口
	 * @return 当前Window的屏幕密度
	 */
	public static float getScreenDensity(Window window) {
		DisplayMetrics dm = getDisplayMetrics(window);
		return dm.density;
	}

}
