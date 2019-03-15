package com.saiyi.naideanlock.data.http;


import com.saiyi.naideanlock.data.api.DeviceService;
import com.sandy.guoguo.babylib.entity.MdlBaseHttpResp;
import com.sandy.guoguo.babylib.http.BaseHttpData;
import com.sandy.guoguo.babylib.http.HttpManager;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2018/4/17.
 */

public class HttpDataDevice extends BaseHttpData {
    private static class SingletonHolder {
        private static final HttpDataDevice INSTANCE = new HttpDataDevice();
    }

    public static HttpDataDevice getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private DeviceService deviceService = HttpManager.getInstance().getRetrofit().create(DeviceService.class);


    public void getAllDeviceByType(Map params, Observer<MdlBaseHttpResp> observer) {
        Observable observable = deviceService.getAllDeviceByType(params);
        setSubscribe(observable, observer);
    }
    public void updateDeviceName(Map params, Observer<MdlBaseHttpResp> observer) {
        Observable observable = deviceService.updateDeviceName(params);
        setSubscribe(observable, observer);
    }
    public void delDeviceBinding(Map params, Observer<MdlBaseHttpResp> observer) {
        Observable observable = deviceService.delDeviceBinding(params);
        setSubscribe(observable, observer);
    }
    public void addUnlockRecord(Map params, Observer<MdlBaseHttpResp> observer) {
        Observable observable = deviceService.addUnlockRecord(params);
        setSubscribe(observable, observer);
    }

    public void getPhotoList(Map params, Observer<MdlBaseHttpResp> observer) {
        Observable observable = deviceService.getPhotoList(params);
        setSubscribe(observable, observer);
    }
    public void setUnmannedMode(Map params, Observer<MdlBaseHttpResp> observer) {
        Observable observable = deviceService.setUnmannedMode(params);
        setSubscribe(observable, observer);
    }
    public void setTamperAlert(Map params, Observer<MdlBaseHttpResp> observer) {
        Observable observable = deviceService.setTamperAlert(params);
        setSubscribe(observable, observer);
    }
    public void setLowPower(Map params, Observer<MdlBaseHttpResp> observer) {
        Observable observable = deviceService.setLowPower(params);
        setSubscribe(observable, observer);
    }
    public void setUnlockPwd(Map params, Observer<MdlBaseHttpResp> observer) {
        Observable observable = deviceService.setUnlockPwd(params);
        setSubscribe(observable, observer);
    }
    public void bindDevice(Map params, Observer<MdlBaseHttpResp> observer) {
        Observable observable = deviceService.bindDevice(params);
        setSubscribe(observable, observer);
    }
    public void getUnlockRecord(Map params, Observer<MdlBaseHttpResp> observer) {
        Observable observable = deviceService.getUnlockRecord(params);
        setSubscribe(observable, observer);
    }
    public void deleteAllUnlockRecord(Map params, Observer<MdlBaseHttpResp> observer) {
        Observable observable = deviceService.deleteAllUnlockRecord(params);
        setSubscribe(observable, observer);
    }
    public void getAuthManagerList(Map params, Observer<MdlBaseHttpResp> observer) {
        Observable observable = deviceService.getAuthManagerList(params);
        setSubscribe(observable, observer);
    }
    public void renameAuthUser(Map params, Observer<MdlBaseHttpResp> observer) {
        Observable observable = deviceService.renameAuthUser(params);
        setSubscribe(observable, observer);
    }
    public void deleteNanny(Map params, Observer<MdlBaseHttpResp> observer) {
        RequestBody body = GenJsonParamRequestBody(params);
        Observable observable = deviceService.deleteNanny(body);
        setSubscribe(observable, observer);
    }
    public void addNanny(Map params, Observer<MdlBaseHttpResp> observer) {
        RequestBody body = GenJsonParamRequestBody(params);
        Observable observable = deviceService.addNanny(body);
        setSubscribe(observable, observer);
    }

}
