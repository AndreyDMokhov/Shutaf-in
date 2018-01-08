package com.shutafin.service.impl;

import com.shutafin.model.entities.Deal;
import com.shutafin.model.entities.DealDocument;
import com.shutafin.model.entities.DealPanel;
import com.shutafin.model.entities.DealSnapshot;
import com.shutafin.model.exception.exceptions.ResourceNotFoundException;
import com.shutafin.model.types.DealStatus;
import com.shutafin.model.types.DealUserPermissionType;
import com.shutafin.model.types.DocumentType;
import com.shutafin.model.util.DealSnapshotInfo;
import com.shutafin.model.web.deal.DealDocumentWeb;
import com.shutafin.model.web.deal.DealPanelResponse;
import com.shutafin.model.web.deal.DealResponse;
import com.shutafin.model.web.deal.InternalDealWeb;
import com.shutafin.repository.DealPanelRepository;
import com.shutafin.repository.DealPanelUserRepository;
import com.shutafin.repository.DealSnapshotRepository;
import com.shutafin.service.DealPanelService;
import com.shutafin.service.DealSnapshotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class DealSnapshotServiceImpl implements DealSnapshotService {

    private DealSnapshotRepository dealSnapshotRepository;
    private DealPanelRepository dealPanelRepository;
    private DealPanelUserRepository dealPanelUserRepository;
    private DealPanelService dealPanelService;

    @Autowired
    public DealSnapshotServiceImpl(DealSnapshotRepository dealSnapshotRepository,
                                   DealPanelRepository dealPanelRepository,
                                   DealPanelUserRepository dealPanelUserRepository,
                                   DealPanelService dealPanelService) {
        this.dealSnapshotRepository = dealSnapshotRepository;
        this.dealPanelRepository = dealPanelRepository;
        this.dealPanelUserRepository = dealPanelUserRepository;
        this.dealPanelService = dealPanelService;
    }

    @Override
    public void saveDealSnapshot(Deal deal, Long userId) {
        Optional<DealSnapshot> dealSnapshotOptional = dealSnapshotRepository.findAllByUserId(userId).stream()
                .filter(dealSnapshot -> dealSnapshot.getDealSnapshotInfo().getDealId().equals(deal.getId()))
                .findFirst();

        DealSnapshot dealSnapshot;
        if (dealSnapshotOptional.isPresent()) {
            dealSnapshot = dealSnapshotOptional.get();
        } else {
            dealSnapshot = new DealSnapshot();
        }
        dealSnapshot.setUserId(userId);

        DealSnapshotInfo dealSnapshotInfo = new DealSnapshotInfo();
        dealSnapshotInfo.setDealId(deal.getId());
        dealSnapshotInfo.setTitle(deal.getTitle());
        dealSnapshotInfo.setStatusId(deal.getDealStatus().getCode());

        List<DealPanel> dealPanels = dealPanelRepository.findAllByDealId(deal.getId());
        if (!dealPanels.isEmpty()) {
            dealPanels = dealPanels.stream()
                    .filter(dealPanel -> dealPanelUserRepository
                            .findByDealPanelIdAndUserId(dealPanel.getId(), userId)
                            .getDealUserPermissionType() != DealUserPermissionType.NO_READ)
                    .collect(Collectors.toList());

            dealSnapshotInfo.setDealPanels(dealPanels.stream()
                    .map(dealPanel -> getDealPanelResponse(userId, dealPanel))
                    .collect(Collectors.toList()));
        }

        dealSnapshot.setDealSnapshotInfo(dealSnapshotInfo);
        dealSnapshotRepository.save(dealSnapshot);
    }

    @Override
    public DealResponse getDealSnapshot(Long dealId, Long userId) {
        Optional<DealSnapshot> dealSnapshotOptional = dealSnapshotRepository.findAllByUserId(userId)
                .stream().filter(dealSnapshot -> dealSnapshot.getDealSnapshotInfo().getDealId().equals(dealId))
                .findFirst();
        if (dealSnapshotOptional.isPresent()) {
            return getDealResponse(dealSnapshotOptional.get().getDealSnapshotInfo());
        }
        throw new ResourceNotFoundException(String.format("Deal with ID %d was not found for user ID %d", dealId, userId));
    }

    @Override
    public InternalDealWeb renameDealSnapshot(Long dealId, Long userId, String newTitle) {
        Optional<DealSnapshot> dealSnapshotOptional = dealSnapshotRepository.findAllByUserId(userId)
                .stream().filter(snapshot -> snapshot.getDealSnapshotInfo().getDealId().equals(dealId))
                .findFirst();
        if (dealSnapshotOptional.isPresent()) {
            DealSnapshot dealSnapshot = dealSnapshotOptional.get();
            dealSnapshot.getDealSnapshotInfo().setTitle(newTitle);
            return new InternalDealWeb(dealId, null, dealSnapshot.getDealSnapshotInfo().getTitle(), null);
        } else {
            log.warn("Deal snapshot of Deal ID {} for User ID {} was not found", dealId, userId);
            throw new ResourceNotFoundException(String.format("Deal snapshot of Deal ID %d for User ID %d was not found",
                    dealId, userId));
        }
    }

    @Override
    public DealPanelResponse getDealPanelFromSnapshot(Long dealPanelId, Long userId) {
        Optional<DealPanelResponse> dealPanelOptional = dealSnapshotRepository.findAllByUserId(userId)
                .stream()
                .flatMap(snapshot -> snapshot.getDealSnapshotInfo().getDealPanels().stream())
                .filter(panelResponse -> panelResponse.getPanelId().equals(dealPanelId))
                .findFirst();
        if (dealPanelOptional.isPresent()) {
            return dealPanelOptional.get();
        } else {
            log.warn("Cannot find Deal Panel with ID {} for user ID {}", dealPanelId, userId);
            throw new ResourceNotFoundException(String.format("Cannot find Deal Panel with ID %d for user ID %d",
                    dealPanelId, userId));
        }
    }

    @Override
    public DealPanelResponse renameDealPanelInSnapshot(Long dealPanelId, Long userId, String newTitle) {
        DealPanelResponse dealPanelResponse = getDealPanelFromSnapshot(dealPanelId, userId);
        dealPanelResponse.setTitle(newTitle);
        return dealPanelResponse;
    }

    @Override
    public void deleteDealPanelFromSnapshot(Long dealPanelId, Long userId) {
        Optional<DealSnapshot> dealSnapshotOptional = dealSnapshotRepository.findAllByUserId(userId)
                .stream()
                .filter(snapshot -> {
                    for (DealPanelResponse dealPanelResponse : snapshot.getDealSnapshotInfo().getDealPanels()) {
                        if (dealPanelResponse.getPanelId().equals(dealPanelId)) {
                            return true;
                        }
                    }
                    return false;
                }).findFirst();
        if (dealSnapshotOptional.isPresent()) {
            DealSnapshot dealSnapshot = dealSnapshotOptional.get();
            dealSnapshot.getDealSnapshotInfo().getDealPanels()
                    .removeIf(dealPanelResponse -> dealPanelResponse.getPanelId().equals(dealPanelId));
        } else {
            log.warn("Cannot find Deal Panel with ID {} for user ID {}", dealPanelId, userId);
            throw new ResourceNotFoundException(String.format("Cannot find Deal Panel with ID %d for user ID %d",
                    dealPanelId, userId));
        }
    }

    @Override
    public DealDocument getDealDocumentFromSnapshot(DealDocument dealDocument, Long userId) {
        DealDocumentWeb dealDocumentWeb = getDealDocumentWeb(dealDocument.getId(), userId);
        return getDealDocumentSnapshotAsDealDocument(dealDocument, dealDocumentWeb);
    }

    private DealDocument getDealDocumentSnapshotAsDealDocument(DealDocument dealDocument, DealDocumentWeb dealDocumentWeb) {
        DealDocument dealDocumentCopy = new DealDocument(DocumentType.getById(dealDocumentWeb.getDocumentType()),
                dealDocument.getDocumentStorage(), dealDocumentWeb.getTitle(), false);
        dealDocumentCopy.setDealPanel(dealDocument.getDealPanel());
        dealDocumentCopy.setId(dealDocument.getId());
        dealDocumentCopy.setCreatedDate(dealDocument.getCreatedDate());
        return dealDocumentCopy;
    }

    private DealDocumentWeb getDealDocumentWeb(Long dealDocumentId, Long userId) {
        Optional<DealDocumentWeb> dealDocumentWebOptional = dealSnapshotRepository.findAllByUserId(userId)
                .stream()
                .flatMap(snapshot -> snapshot.getDealSnapshotInfo().getDealPanels().stream())
                .flatMap(dealPanel -> dealPanel.getDocuments().stream())
                .filter(doc -> doc.getDocumentId().equals(dealDocumentId))
                .findFirst();
        if (dealDocumentWebOptional.isPresent()) {
            DealDocumentWeb dealDocumentWeb = dealDocumentWebOptional.get();
            return dealDocumentWeb;
        } else {
            log.warn("Cannot find Deal Document with ID {} for user ID {}", dealDocumentId, userId);
            throw new ResourceNotFoundException(String.format("Cannot find Deal Document with ID %d for user ID %d",
                    dealDocumentId, userId));
        }
    }

    @Override
    public DealDocument renameDealDocumentInSnapshot(DealDocument dealDocument, Long userId, String newTitle) {
        DealDocumentWeb dealDocumentWeb = getDealDocumentWeb(dealDocument.getId(), userId);
        dealDocumentWeb.setTitle(newTitle);
        return getDealDocumentSnapshotAsDealDocument(dealDocument, dealDocumentWeb);
    }

    @Override
    public void deleteDealDocumentFromSnapshot(Long dealDocumentId, Long userId) {
        Optional<DealSnapshot> dealSnapshotOptional = dealSnapshotRepository.findAllByUserId(userId)
                .stream()
                .filter(snapshot -> {
                    for (DealPanelResponse dealPanelResponse : snapshot.getDealSnapshotInfo().getDealPanels()) {
                        for (DealDocumentWeb dealDocumentWeb : dealPanelResponse.getDocuments()) {
                            if (dealDocumentWeb.getDocumentId().equals(dealDocumentId)) {
                                return true;
                            }
                        }
                    }
                    return false;
                }).findFirst();
        if (dealSnapshotOptional.isPresent()) {
            DealSnapshot dealSnapshot = dealSnapshotOptional.get();
            dealSnapshot.getDealSnapshotInfo().getDealPanels().forEach(dealPanel -> dealPanel
                    .getDocuments()
                    .removeIf(dealDocumentWeb -> dealDocumentWeb.getDocumentId().equals(dealDocumentId))
            );
        } else {
            log.warn("Cannot find Deal Document with ID {} for user ID {}", dealDocumentId, userId);
            throw new ResourceNotFoundException(String.format("Cannot find Deal Document with ID %d for user ID %d",
                    dealDocumentId, userId));
        }
    }

    private DealPanelResponse getDealPanelResponse(Long userId, DealPanel dealPanel) {
        DealPanelResponse dealPanelResponse = new DealPanelResponse();
        dealPanelResponse.setPanelId(dealPanel.getId());
        dealPanelResponse.setTitle(dealPanel.getTitle());
        dealPanelResponse.setDocuments(dealPanelService.getDealPanelDocuments(dealPanel.getId(), userId));
        return dealPanelResponse;
    }

    public DealResponse getDealResponse(DealSnapshotInfo dealSnapshotInfo) {
        DealResponse dealResponse = new DealResponse();
        dealResponse.setDealId(dealSnapshotInfo.getDealId());
        dealResponse.setTitle(dealSnapshotInfo.getTitle());
        dealResponse.setStatusId(DealStatus.ARCHIVE.getCode());

        List<DealPanelResponse> dealPanels = dealSnapshotInfo.getDealPanels();
        if (dealPanels != null && !dealPanels.isEmpty()) {
            dealResponse.setFirstPanel(dealPanels.get(0));
            dealResponse.setPanels(dealPanels.stream()
                    .collect(Collectors.toMap(DealPanelResponse::getPanelId, DealPanelResponse::getTitle)));
        } else {
            dealResponse.setPanels(new HashMap<>());
        }

        return dealResponse;
    }

}
