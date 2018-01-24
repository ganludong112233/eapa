package com.tcl.ep.admin;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import com.tcl.ep.admin.utils.MD5Utils;

/**
 * Created by panmin on 8/10/16.
 */
public class LoginTest {
    public static void main(String[] args)
    {
        String password = "";
        try {

            password = MD5Utils.getEncryptedPwd("Aa123456");
            System.out.println(password);
           // System.out.println("check:"+checkEmail("sdkjfdks@sdfsd.com"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
   
}
