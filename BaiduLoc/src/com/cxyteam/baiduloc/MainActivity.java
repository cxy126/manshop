package com.cxyteam.baiduloc;


import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {

	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();
	private TextView text = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		text = (TextView) this.findViewById(R.id.show);

		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
		option.setScanSpan(5000);// 设置发起定位请求的间隔时间为5000ms
		option.setIsNeedAddress(true);// 返回的定位结果包含地址信息
		option.setNeedDeviceDirect(true);// 返回的定位结果包含手机机头的方向

		mLocationClient = new LocationClient(getApplicationContext()); // 声明LocationClient类
		mLocationClient.setLocOption(option);
		mLocationClient.registerLocationListener(myListener); // 注册监听函数
		mLocationClient.start();

		// 发起离线定位请求
		// 发起离线定位请求。请求过程是异步的，定位结果在上面的监听函数onReceiveLocation中获取。
		// getLocTypte = BDLocation.TypteOfflineLocation ||
		// BDLocation.TypeOfflineLocationFail
		// 表示是离线定位请求返回的定位结果
		// if (mLocClient != null && mLocClient.isStarted())
		// mLocClient.requestOfflineLocation();

	}

	@Override
	protected void onResume() {
		super.onResume();
		if (mLocationClient != null && mLocationClient.isStarted())
			mLocationClient.requestLocation();
		else
			text.setText("locClient is null or not started");
	}

	public class MyLocationListener implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;

			String city = location.getCity();
			double lat = location.getLatitude();
			double lng = location.getLongitude();
			text.setText(city + "\n" + lat + " \n" + lng);

		}
	}

}
