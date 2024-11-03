package com.company.media.service.mediaBlob;

import com.company.media.exceptions.ResourceNotFoundException;
import com.company.media.model.MediaBlob;
import com.company.media.model.MediaPost;
import com.company.media.repository.MediaBlobRepository;
import com.company.media.repository.MediaPostRepository;
import com.company.media.service.mediaPost.IMediaPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MediaBlobService implements IMediaBlobService {
    private final MediaBlobRepository blobRepository;
    private final MediaPostRepository postRepository;
    private final IMediaPostService postService;

    @Override
    public void addMediaBlob(Long mediaId, MultipartFile file) {
        MediaPost post = postService.findById(mediaId);
        try {
            MediaBlob blob = new MediaBlob();
            blob.setFileName(file.getOriginalFilename());
            blob.setFileType(file.getContentType());
            blob.setMedia(new SerialBlob(file.getBytes()));
            blob.setMediaPost(post);

            String buildDownloadUrl = "/api/v1/files/file/download/";
            String downloadUrl = buildDownloadUrl + blob.getId();
            blob.setDownloadUrl(downloadUrl);

            MediaBlob savedBlob = blobRepository.save(blob);

            savedBlob.setDownloadUrl(buildDownloadUrl + savedBlob.getId());

            postRepository.save(post);
            blobRepository.save(savedBlob);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public MediaBlob findById(Long id) {
        return blobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("image or video not found!"));
    }

}
