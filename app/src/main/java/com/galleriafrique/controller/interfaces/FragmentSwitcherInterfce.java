package com.galleriafrique.controller.interfaces;

import com.galleriafrique.model.post.Post;

/**
 * Created by osifo on 8/23/15.
 */
public interface FragmentSwitcherInterfce {
    void showSearchView();
    void showPostDetails(Post post);
    void showUserProfile();
    void showFollowers();
    void showFollowing();
    void showPostFeed();
}
