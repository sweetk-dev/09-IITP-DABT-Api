package com.sweetk.iitp.api.service.poi;

import com.sweetk.iitp.api.repository.poi.MvPoiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MvPoiReadService {
    private MvPoiRepository mvPoiRepos;


    /*
     @Override
    public List<Poi> findAll() {
        return poiRepository.findAllByActiveTrue();
    }

    @Override
    public Page<Poi> findAll(Pageable pageable) {
        return poiRepository.findAllByActiveTrue(pageable);
    }

    @Override
    public Optional<Poi> findById(Long id) {
        return poiRepository.findByIdAndActiveTrue(id);
    }

    @Override
    @Transactional
    public Poi save(Poi poi) {
        return poiRepository.save(poi);
    }

    @Override
    @Transactional
    public Poi update(Long id, Poi poi) {
        return poiRepository.findByIdAndActiveTrue(id)
                .map(existingPoi -> {
                    poi.setId(id);
                    return poiRepository.save(poi);
                })
                .orElseThrow(() -> new ApiException(ErrorCode.ENTITY_NOT_FOUND.getCode(), "POI not found"));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        poiRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void softDelete(Long id) {
        poiRepository.softDelete(id);
    }

    @Override
    public Page<Poi> findByType(String type, Pageable pageable) {
        return poiRepository.findByType(type, pageable);
    }

    @Override
    public Page<Poi> findByNameContaining(String name, Pageable pageable) {
        return poiRepository.findByNameContaining(name, pageable);
    }

    @Override
    public Page<Poi> findByLocationWithinRadius(Double latitude, Double longitude, Double radius, Pageable pageable) {
        return poiRepository.findByLocationWithinRadius(latitude, longitude, radius, pageable);
    }
     */
}
