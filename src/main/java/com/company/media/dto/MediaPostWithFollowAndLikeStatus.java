package com.company.media.dto;

import com.company.media.model.MediaPost;
import lombok.Data;

@Data
public class MediaPostWithFollowAndLikeStatus {
    private MediaPost mediaPost;
    private boolean isFollow;
    private boolean isLiked;

    public MediaPostWithFollowAndLikeStatus(MediaPost mediaPost, boolean isFollow,boolean isLiked){
        this.mediaPost = mediaPost;
        this.isFollow = isFollow;
        this.isLiked = isLiked;
    }

}
