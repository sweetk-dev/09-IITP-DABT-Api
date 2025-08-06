package com.sweetk.iitp.api.service.poi;

import com.sweetk.iitp.api.dto.common.PageReq;
import com.sweetk.iitp.api.dto.common.PageRes;
import com.sweetk.iitp.api.dto.internal.MvPoiPageResult;
import com.sweetk.iitp.api.dto.poi.MvPoi;
import com.sweetk.iitp.api.dto.poi.MvPoiSearchCatReq;
import com.sweetk.iitp.api.dto.poi.MvPoiSearchLocReq;
import com.sweetk.iitp.api.repository.poi.MvPoiRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MvPoiReadService {
    private final MvPoiRepository mvPoiRepos;

    public Optional<MvPoi> findById(Long poiId) {
        return mvPoiRepos.findByIdWithPublished(poiId);
    }


    public List<MvPoi> getPoiByCategoryType(String categoryType) {
        return mvPoiRepos.findByCategoryType(categoryType);
    }


    public PageRes<MvPoi> getPoiByCategoryTypePaging(String categoryType, PageReq pageReq) {
        int offset = pageReq.getPage() * pageReq.getSize();
        int size = pageReq.getSize();
        
        MvPoiPageResult result = mvPoiRepos.findByCategoryTypeWithCount(categoryType, offset, size);
        return new PageRes<>(result.content, pageReq.toPageable(), result.totalCount);
    }

    public List<MvPoi> getAllPoi() {
        return mvPoiRepos.findAllPublished();
    }


    public PageRes<MvPoi> getAllPoiPaging(PageReq pageReq) {
        int offset = pageReq.getPage() * pageReq.getSize();
        int size = pageReq.getSize();
        MvPoiPageResult result = mvPoiRepos.findAllWithCount(offset, size);
        return new PageRes<>(result.content, pageReq.toPageable(), result.totalCount);
    }


    public List<MvPoi> getPoiByCategory(MvPoiSearchCatReq searchReq) {
        // 파라미터 준비
        String category = searchReq.getCategory() != null ? searchReq.getCategory().getCode() : null;
        String subCate = searchReq.getSubCate();
        String name = searchReq.getName();

        // Impl의 네이티브 쿼리 메서드 호출
        return mvPoiRepos.findByCategoryAndSubCate( category, subCate, name);
    }

    public PageRes<MvPoi> getPoiByCategoryPaging(MvPoiSearchCatReq searchReq, PageReq pageReq) {
        // 파라미터 준비
        String category = searchReq.getCategory() != null ? searchReq.getCategory().getCode() : null;
        String subCate = searchReq.getSubCate();
        String name = searchReq.getName();
        int offset = pageReq.getPage() * pageReq.getSize();
        int size = pageReq.getSize();

        // Impl의 네이티브 쿼리 메서드 호출
        MvPoiPageResult result = mvPoiRepos.findByCategoryAndSubCateWithCount(
                                        category, subCate, name, offset, size);

        return new PageRes<>(result.content, pageReq.toPageable(), result.totalCount);
    }


    public List<MvPoi> getPoiByLocation(MvPoiSearchLocReq searchReq) {

        String category = searchReq.getCategory() != null ? searchReq.getCategory().getCode() : null;
        return mvPoiRepos.findByLocation(
                category,
                searchReq.getName(),
                searchReq.getLatitude(),
                searchReq.getLongitude(),
                searchReq.getRadius()
        );
    }


    public PageRes<MvPoi> getPoiByLocationPaging(MvPoiSearchLocReq searchReq, PageReq pageReq) {
        int offset = pageReq.getPage() * pageReq.getSize();
        int size = pageReq.getSize();
        String category = searchReq.getCategory() != null ? searchReq.getCategory().getCode() : null;

        MvPoiPageResult result = mvPoiRepos.findByLocationWithCount(
                            category,
                            searchReq.getName(),
                            searchReq.getLatitude(),
                            searchReq.getLongitude(),
                            searchReq.getRadius(),
                            offset,
                            size
                        );
        return new PageRes<>(result.content, pageReq.toPageable(), result.totalCount);
    }
}
