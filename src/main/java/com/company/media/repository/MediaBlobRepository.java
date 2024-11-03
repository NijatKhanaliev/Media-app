package com.company.media.repository;

import com.company.media.model.MediaBlob;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaBlobRepository extends JpaRepository<MediaBlob, Long> {
}