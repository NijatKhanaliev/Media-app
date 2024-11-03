package com.company.media.service.mediaPost;

import com.company.media.model.MediaPost;

import java.util.List;

public interface IMediaPostService {
    List<MediaPost> getAllPost();
    MediaPost createPost(String name,String description);
    MediaPost findById(Long id);
    List<MediaPost> searchByName(String name);
}
