package com.galleriafrique.model.post;

import com.galleriafrique.model.GeneralStatusResponse;
import com.google.gson.annotations.SerializedName;

/**
 * Created by osifo on 8/3/15.
 */

public class FavoriteResponse extends GeneralStatusResponse {
    private Favorite data;

    public Favorite getFavorite(){ return data != null ? data : new Favorite(); }

    public void setFavorite(Favorite data) { this.data = data; }

    public class Favorite {

        @SerializedName("favorites_count")
        private int count;

        @SerializedName("is_favorite")
        private boolean isFavorite;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public boolean isFavorite() {
            return isFavorite;
        }

        public void setIsFavorite(boolean isFavorite) {
            this.isFavorite = isFavorite;
        }
    }
}
