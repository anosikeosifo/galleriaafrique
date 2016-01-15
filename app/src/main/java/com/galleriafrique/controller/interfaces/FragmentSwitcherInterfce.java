package com.galleriafrique.controller.interfaces;

import com.galleriafrique.model.post.Post;
import com.galleriafrique.model.user.User;

/**
 * Created by osifo on 8/23/15.
 */
public interface FragmentSwitcherInterfce {
    void showSearchView();
//    void showPostDetails(Post post);
    void showPostDetails(Post post);
    void showUserProfile(User user);
    void showFollowers();
    void showFollowing();
    void showPostFeed();
    void showImageGallery();
}
