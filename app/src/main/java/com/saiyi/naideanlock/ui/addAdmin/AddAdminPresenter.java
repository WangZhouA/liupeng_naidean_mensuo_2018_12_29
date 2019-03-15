package com.saiyi.naideanlock.ui.addAdmin;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.saiyi.naideanlock.bean.AddMemberBean;

import java.util.ArrayList;

/**
 * 描述：
 * 创建作者：Fanjianchang
 * 创建时间：2017/9/30 11:43
 * 登录业务逻辑处理
 */

public class AddAdminPresenter implements AddAdminContract.AddAdminPresenter {

    private AddAdminContract.AddAdminView mAddAdminView;

    public AddAdminPresenter(AddAdminContract.AddAdminView mAddAdminView) {
        this.mAddAdminView = mAddAdminView;
        mAddAdminView.setPresenter(this);
    }

    @Override
    public void mailList(final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String name;
                String phoneNum;
                ArrayList<AddMemberBean> contactList = new ArrayList<>();
                Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
                String sortOrder = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
                Cursor cursor = context.getContentResolver().query(uri, projection, null, null, sortOrder);
                AddMemberBean addMemberBean;
                while (cursor.moveToNext()) {
                    addMemberBean = new AddMemberBean();
                    name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    phoneNum = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    phoneNum = String.valueOf(phoneNum.trim().replace(" ", "").replace("+", ""));
                    addMemberBean.setName(name);
                    contactList.add(addMemberBean);
                }
                cursor.close();
                mAddAdminView.GainMailList(contactList);

            }
        }).start();
    }
}
