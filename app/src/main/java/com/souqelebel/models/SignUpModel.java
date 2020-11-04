package com.souqelebel.models;

import android.content.Context;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

import com.souqelebel.BR;
import com.souqelebel.R;


public class SignUpModel extends BaseObservable {
    private String name;
    private String phone_code;
    private String phone;
    private String address;
    private String latitude;
    private String longitude;
    private String image_url;
    public ObservableField<String> error_name = new ObservableField<>();
    public ObservableField<String> error_address = new ObservableField<>();


    public boolean isDataValid(Context context) {
        if (!name.trim().isEmpty()&&
                !address.trim().isEmpty()

        ) {
            error_name.set(null);
            error_address.set(null);

            return true;
        } else {
            if (name.trim().isEmpty()) {
                error_name.set(context.getString(R.string.field_required));

            } else {
                error_name.set(null);

            }
            if (address.trim().isEmpty()) {
                error_address.set(context.getString(R.string.field_required));

            }  else {
                error_address.set(null);
            }

            return false;
        }
    }

    public SignUpModel() {
        setName("");
        setLatitude("");
        setLongitude("");
        setAddress("");

    }


    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);

    }

    public String getPhone_code() {
        return phone_code;
    }

    public void setPhone_code(String phone_code) {
        this.phone_code = phone_code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
