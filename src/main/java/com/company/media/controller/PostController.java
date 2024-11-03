package com.company.media.controller;

import com.company.media.exceptions.ResourceNotFoundException;
import com.company.media.model.MediaBlob;
import com.company.media.model.MediaPost;
import com.company.media.service.mediaBlob.IMediaBlobService;
import com.company.media.service.mediaPost.IMediaPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;

@RequiredArgsConstructor
@Controller
public class PostController {
    private final IMediaPostService postService;
    private final IMediaBlobService blobService;

    @PostMapping(value = "/create")
    public String createPost(@RequestParam String name,
                             @RequestParam String description,
                             @RequestParam MultipartFile file){
        try {
            MediaPost post = postService.createPost(name,description);
            blobService.addMediaBlob(post.getId(),file);

            return "redirect:/";
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @GetMapping("/create")
    public String createPost(){
        return "createPost";
    }

    @GetMapping("/api/v1/files/file/download/{postId}")
    public ResponseEntity<ByteArrayResource> getFile(@PathVariable Long postId) throws SQLException {
       MediaBlob blob = blobService.findById(postId);
       ByteArrayResource resource = new ByteArrayResource(blob.getMedia().getBytes(1,(int) blob.getMedia().length()));

       return ResponseEntity.ok().contentType(MediaType.parseMediaType(blob.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename=\"" + blob.getFileName() + "\"")
                .body(resource);
    }

}
