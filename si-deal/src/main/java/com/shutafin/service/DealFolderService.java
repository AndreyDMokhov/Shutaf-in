package com.shutafin.service;

import com.shutafin.model.entities.DealFolder;
import com.shutafin.model.web.DealFolderWeb;

public interface DealFolderService {

    DealFolder addDealFolder(DealFolderWeb dealFolderWeb);
    DealFolder getDealFolder(Long dealFolderId, Long userId);
    DealFolder renameDealFolder(Long dealFolderId, Long userId, String newTitle);
    void deleteDealFolder(Long dealFolderId, Long userId);

}
