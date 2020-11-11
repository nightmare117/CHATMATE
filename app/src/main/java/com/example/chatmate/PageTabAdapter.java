package com.example.chatmate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PageTabAdapter extends FragmentPagerAdapter {
    public PageTabAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position==0){
            RequestFragment requestFragment=new RequestFragment();
            return requestFragment;
        }
        else if(position==1){
            ChatsFragment chatsFragment=new ChatsFragment();
            return chatsFragment;
        }
        else if(position==2){
            FriendsFragment friendsFragment=new FriendsFragment();
            return friendsFragment;
        }
        else {
            return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0){
            return "REQUESTS";
        }
        else if(position==1){
            return "CHATS";
        }
        else if(position==2){
            return "FRIENDS";
        }
        else{
            return null;
        }
    }
}
