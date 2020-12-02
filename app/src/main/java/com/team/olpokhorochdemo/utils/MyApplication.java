package com.team.olpokhorochdemo.utils;

import android.app.Application;
import com.parse.Parse;
/**
 * Created by Amit on 02,December,2020
 */
public class MyApplication extends Application {

    // Initializes Parse SDK as soon as the application is created
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("t6waHChtpZlm2J02bP6CxfBIslVQf6FZhDTk3wf9")
                .clientKey("tukwpvKx8I5wITFdyEn3MTMI0bo8UroapasmRhIB")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
