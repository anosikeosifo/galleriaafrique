package com.galleriafrique.model.post;

import com.galleriafrique.model.GeneralStatusResponse;

/**
 * Created by osifo on 8/3/15.
 */
public class LikeResponse extends GeneralStatusResponse {
    private Like data;

    public Like getLike(){ return data != null ? data : new Like(); }

    public void setLike(Like data) { this.data = data; }

    public class Like {
        private int count;

        private boolean isLiked;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public boolean isLiked() {
            return isLiked;
        }

        public void setIsLiked(boolean isLiked) {
            this.isLiked = isLiked;
        }
    }
}
