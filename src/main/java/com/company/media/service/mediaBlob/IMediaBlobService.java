package com.company.media.service.mediaBlob;

import com.company.media.model.MediaBlob;
import org.springframework.web.multipart.MultipartFile;

public interface IMediaBlobService {
    void addMediaBlob(Long mediaId, MultipartFile file);
    MediaBlob findById(Long id);
}
