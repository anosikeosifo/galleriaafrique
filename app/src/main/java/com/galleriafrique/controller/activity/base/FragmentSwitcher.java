package com.galleriafrique.controller.activity.base;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.galleriafrique.R;
import com.galleriafrique.controller.fragment.base.BaseFragment;
import com.galleriafrique.controller.fragment.base.ImageSelect;
import com.galleriafrique.controller.fragment.posts.AddPost;
import com.galleriafrique.controller.fragment.posts.PostDetails;
import com.galleriafrique.controller.fragment.posts.Posts;
import com.galleriafrique.controller.interfaces.FragmentSwitcherInterfce;
import com.galleriafrique.model.post.Post;

/**
 * Created by osifo on 8/23/15.
 */
public class FragmentSwitcher implements FragmentSwitcherInterfce {

    private AppCompatActivity activity;
    private FragmentManager fragmentManager;

    public FragmentSwitcher(AppCompatActivity activity, FragmentManager fragmentManager) {
        this.activity = activity;
        this.fragmentManager = fragmentManager;
    }

    private void replaceFragment(BaseFragment fragment, boolean withAnimation, int container) {
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();

        if (withAnimation) {
//            ft.setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left, R.anim.slide_in_from_left, R.anim.slide_out_to_right);
        }

        ft.replace(container, fragment, fragment.getTagText());
        ft.commit();
    }

    private void addFragment(BaseFragment fragment, boolean withAnimation, int container) {

        if (fragmentManager.findFragmentByTag(fragment.getTagText()) == null) {

            FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();

            if (withAnimation) {
//                ft.setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left, R.anim.slide_in_from_left, R.anim.slide_out_to_right);
            }

            ft.add(container, fragment, fragment.getTagText());
            ft.addToBackStack(fragment.getTagText());
            String text = fragment.getTagText();
            ft.commit();
        } else {

        }
    }

    public void showPostFeed() {
        addFragment(new Posts(), true, R.id.posts_coontainer);
    }

    @Override
    public void showSearchView() {

    }

    @Override
    public void showPostDetails(Post post) {
        addFragment(PostDetails.newInstance(post), true, R.id.container);
    }

    @Override
    public void showImageGallery() {
        addFragment(new ImageSelect(), true, R.id.container);
    }

    @Override
    public void showUserProfile() {

    }

    @Override
    public void showFollowers() {

    }

    @Override
    public void showFollowing() {

    }

    public void addPost(int viewID) {
        addFragment(AddPost.newInstance(viewID), true, R.id.container);
    }

    private void addMultipleFragments(BaseFragment fragment, boolean withAnimation, int container) {

        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();

        if (withAnimation) {
//                ft.setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left, R.anim.slide_in_from_left, R.anim.slide_out_to_right);
        }
        ft.add(container, fragment, fragment.getTagText());
        ft.addToBackStack(fragment.getTagText());
        ft.commitAllowingStateLoss();
    }
}
