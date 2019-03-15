package object.p2pipcam.nativecaller.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.saiyi.naideanlock.R;
import com.sandy.guoguo.babylib.ui.BaseActivity;
import com.sandy.guoguo.babylib.utils.ResourceUtil;

import object.p2pipcam.nativecaller.BridgeService;
import object.p2pipcam.nativecaller.CommonUtil;
import object.p2pipcam.nativecaller.ContentCommon;
import object.p2pipcam.nativecaller.MyRender;
import object.p2pipcam.nativecaller.NativeCaller;

public class RemoteCameraActivity extends BaseActivity implements BridgeService.IpcamClientInterface,BridgeService.SHIXCOMMONInterface{
    private GLSurfaceView myGlSurfaceView = null;
    private MyRender myRender = null;
    String did;
    //    private final String testDid = "IKB000103CMVHF";
    private final String testDid = "IKB000102JVKSJ";
    private TextView tvShow;
    private TextView tvShowTrans;
    private StringBuffer strShow;



    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {
            Bundle bd = msg.getData();
            int msgParam = bd.getInt(STR_MSG_PARAM);
            int msgType = msg.what;
            did = bd.getString(STR_DID);
            switch (msg.what){
                case 110:
                    myRender.writeSample(videoData, nVideoWidth, nVideoHeight);
                    break;
                case ContentCommon.PPPP_MSG_TYPE_PPPP_STATUS:
                    tvShow.setText(getStrStatue(msgParam));
                    if (msgParam == ContentCommon.PPPP_STATUS_INVALID_ID
                            || msgParam == ContentCommon.PPPP_STATUS_CONNECT_FAILED
                            || msgParam == ContentCommon.PPPP_STATUS_DEVICE_NOT_ON_LINE
                            || msgParam == ContentCommon.PPPP_STATUS_CONNECT_TIMEOUT
                            || msgParam == ContentCommon.PPPP_STATUS_CONNECT_ERRER
                            || msgParam == ContentCommon.PPPP_STATUS_USER_LOGIN
                            || msgParam == ContentCommon.PPPP_STATUS_PWD_CUO
                            || msgParam == ContentCommon.PPPP_STATUS_DISCONNECT) {
                        NativeCaller.StopPPPP(did);
                    }
                    break;
            }

        }

    };


    private int getStrStatue(int status){
        int resid;
        switch (status) {
            case ContentCommon.PPPP_STATUS_CONNECTING:
                resid = R.string.pppp_status_connecting;
                break;
            case ContentCommon.PPPP_STATUS_CONNECT_FAILED:
                resid = R.string.pppp_status_connect_failed;
                break;
            case ContentCommon.PPPP_STATUS_DISCONNECT:
                resid = R.string.pppp_status_disconnect;
                break;
            case ContentCommon.PPPP_STATUS_INITIALING:
                resid = R.string.pppp_status_initialing;
                break;
            case ContentCommon.PPPP_STATUS_INVALID_ID:
                resid = R.string.pppp_status_invalid_id;
                break;
            case ContentCommon.PPPP_STATUS_ON_LINE:
                resid = R.string.pppp_status_online;
                break;
            case ContentCommon.PPPP_STATUS_DEVICE_NOT_ON_LINE:
                resid = R.string.device_not_on_line;
                break;
            case ContentCommon.PPPP_STATUS_CONNECT_TIMEOUT:
                resid = R.string.pppp_status_connect_timeout;
                break;
            case ContentCommon.PPPP_STATUS_CONNECT_ERRER:
                resid = R.string.pppp_status_connect_log_errer;
                break;
            case ContentCommon.PPPP_STATUS_USER_LOGIN:

                resid = R.string.pppp_status_connect_user_login;
                break;
            case ContentCommon.PPPP_STATUS_PWD_CUO:
                resid = R.string.pppp_status_connect_pwd_cuo;
                break;
            default:
                resid = R.string.pppp_status_unknown;
                break;
        }
        return  resid;
    }

    private byte[] videoData = null;
    private int videoDataLen = 0;
    private int nVideoWidth = 0;
    private int nVideoHeight = 0;



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myRender != null) {
            myRender.destroyShaders();
        }
        Intent intent = new Intent();
        intent.setClass(this, BridgeService.class);
        stopService(intent);
    }
    private static final String STR_DID = "did";
    private static final String STR_MSG_PARAM = "msgparam";
    @Override
    public void BSMsgNotifyData(String did, int type, int param) {
        Bundle bd = new Bundle();
        Message msg = mHandler.obtainMessage();
        msg.what = type;
        bd.putInt(STR_MSG_PARAM, param);
        bd.putString(STR_DID, did);
        msg.setData(bd);
        mHandler.sendMessage(msg);
    }

    @Override
    public void BSSnapshotNotify(String did, byte[] bImage, int len) {

    }

    @Override
    public void callBaceVideoData(String did, byte[] videobuf, int h264Data, int len, int width, int height) {
        videoData = videobuf;
        videoDataLen = len;
        nVideoWidth = width;
        nVideoHeight = height;
        mHandler.sendEmptyMessage(110);
    }

    @Override
    public void callBackMessageNotify(String did, int msgType, int param) {

    }

    @Override
    public void callBackAudioData(byte[] pcm, int len) {

    }

    @Override
    public void callBackH264Data(String did, byte[] h264, int type, int size) {

    }

    @Override
    public void CallBackSHIXJasonCommon(String did, String jason) {
        strShow.append(jason);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvShowTrans.setText(strShow);
            }
        });
    }


    @Override
    protected int getTitleResId() {
        return R.string.remote_camera;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_new_remote_camera;
    }

    @Override
    protected void initViewAndControl() {
        initNav();

        myGlSurfaceView =  findView(R.id.myhsurfaceview);
        myRender = new MyRender(myGlSurfaceView);
        myGlSurfaceView.setRenderer(myRender);
        tvShow = findView(R.id.tv_show);
        tvShowTrans = findView(R.id.tv_show_trans);
        strShow = new StringBuffer();
        BridgeService.setIpcamClientInterface(this);
        BridgeService.setSHIXCOMMONInterface(this);
        /**
         * BridgeService是我动态库回调的方法，启动SDK要保持BridgeService运行
         * **/
        Intent intent = new Intent();
        intent.setClass(this, BridgeService.class);
        startService(intent);


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    /**
                     * 以下是初始化SDK的方法，启动我的SDK 先调用
                     * **/
                    NativeCaller.PPPPInitial("");
                    NativeCaller.PPPPNetworkDetect();
                    NativeCaller.Init();
                } catch (Exception e) {

                }
            }
        }).start();

        findView(R.id.buttonConnet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtil.Log(1,"SHIXCONNET StartPPPP ICB000103CMVHF");
                // String did, String user, String pwd,String server
                /**
                 * StartPPPP 连接设备
                 * did:设备ID
                 * user：用户名
                 * pwd：密码
                 * server：随便填入，不能为null
                 * **/
                NativeCaller.StartPPPP(testDid,"admin","","");
            }
        });

        findView(R.id.buttonVideo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * StartPPPPLivestream 请求视频，参数固定为以下参数，testDid可变，为设备ID
                 * **/
                NativeCaller.StartPPPPLivestream(testDid, 10, "",
                        15);
            }
        });
        findView(R.id.buttonCloseVideo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * StopPPPPLivestream 停止视频  testDid为设备ID
                 * **/
                NativeCaller.StopPPPPLivestream(testDid);
            }
        });

        findView(R.id.buttonTEST).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * TransferMessage1 透传接口，应用层和设备端透传json协议，具体协议参照json协议文档
                 * **/
                NativeCaller.TransferMessage1(testDid,CommonUtil.getUsersParms("admin",""),0);
            }
        });
        findView(R.id.buttonHEAT).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * TransferMessage1 透传接口， demo心跳，正常连接上设备，如果没有请求视频，设备10s会自动休眠，如果不请求视频的情况下，需要保持设备在线，请启用线程发送心跳包，建议间隔5s
                 * **/
                NativeCaller.TransferMessage1(testDid,CommonUtil.SHIX_Heat("admin",""),0);
            }
        });
    }

    private void initNav() {
        TextView tvLeft = findView(R.id.toolbarLeft);
        tvLeft.setVisibility(View.VISIBLE);
        ResourceUtil.setCompoundDrawable(tvLeft, R.drawable.dr_ic_back, 0, 0, 0);
    }

}
