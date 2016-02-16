package com.galleriafrique.util.helpers;

import com.galleriafrique.Constants;
import com.galleriafrique.model.user.User;
import com.galleriafrique.util.CommonUtils;
import com.galleriafrique.util.repo.LoginRepo;
import com.galleriafrique.util.tools.Strings;

/**
 * Created by osifo on 10/5/15.
 */
public class AccountManager {
    private LoginRepo loginRepo;
    private static User user;

    public static User getUser() {

        createUser();
        if (user == null) {
           return new User();
        } else {
            return user;
        }
    }

    public static void updateUser(User user) {
        AccountManager.user = user;
        saveUser(user);
    }

    private static User createUser() {
        if (user == null) {
          String userJsonData = PreferenceManager.getStringPreference(Constants.USER_DATA);
          user = CommonUtils.getGson().fromJson(userJsonData, User.class);;
          saveUser(getUser());
        }
        return user;
    }

    public static void saveUser(User user) {
        String userData = CommonUtils.getGson().toJson(user);
        PreferenceManager.saveStringPreference(Constants.USER_DATA, userData);
    }

    public static boolean isUserLoggedIn() {
        return !PreferenceManager.getStringPreference(Constants.USER_DATA).equals(Strings.EMPTY);
    }

    private static void saveUser() {
        PreferenceManager.saveStringPreference(Constants.USER_DATA, CommonUtils.getGson().toJson(user));
    }

    public static void saveUser(String userData) {
        PreferenceManager.saveStringPreference(Constants.USER_DATA, userData);
    }


    public static void signout() {
        PreferenceManager.removeStringPreference(Constants.USER_DATA);
    }

}
