package io.github.calvary.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import io.github.calvary.domain.SalesReceipt;
import io.github.calvary.repository.SalesReceiptRepository;
import io.github.calvary.repository.search.SalesReceiptSearchRepository;
import io.github.calvary.service.SalesReceiptService;
import io.github.calvary.service.dto.SalesReceiptDTO;
import io.github.calvary.service.mapper.SalesReceiptMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SalesReceipt}.
 */
@Service
@Transactional
public class SalesReceiptServiceImpl implements SalesReceiptService {

    private final Logger log = LoggerFactory.getLogger(SalesReceiptServiceImpl.class);

    private final SalesReceiptRepository salesReceiptRepository;

    private final SalesReceiptMapper salesReceiptMapper;

    private final SalesReceiptSearchRepository salesReceiptSearchRepository;

    public SalesReceiptServiceImpl(
        SalesReceiptRepository salesReceiptRepository,
        SalesReceiptMapper salesReceiptMapper,
        SalesReceiptSearchRepository salesReceiptSearchRepository
    ) {
        this.salesReceiptRepository = salesReceiptRepository;
        this.salesReceiptMapper = salesReceiptMapper;
        this.salesReceiptSearchRepository = salesReceiptSearchRepository;
    }

    @Override
    public SalesReceiptDTO save(SalesReceiptDTO salesReceiptDTO) {
        log.debug("Request to save SalesReceipt : {}", salesReceiptDTO);
        SalesReceipt salesReceipt = salesReceiptMapper.toEntity(salesReceiptDTO);
        salesReceipt = salesReceiptRepository.save(salesReceipt);
        SalesReceiptDTO result = salesReceiptMapper.toDto(salesReceipt);
        salesReceiptSearchRepository.index(salesReceipt);
        return result;
    }

    @Override
    public SalesReceiptDTO update(SalesReceiptDTO salesReceiptDTO) {
        log.debug("Request to update SalesReceipt : {}", salesReceiptDTO);
        SalesReceipt salesReceipt = salesReceiptMapper.toEntity(salesReceiptDTO);
        salesReceipt = salesReceiptRepository.save(salesReceipt);
        SalesReceiptDTO result = salesReceiptMapper.toDto(salesReceipt);
        salesReceiptSearchRepository.index(salesReceipt);
        return result;
    }

    @Override
    public Optional<SalesReceiptDTO> partialUpdate(SalesReceiptDTO salesReceiptDTO) {
        log.debug("Request to partially update SalesReceipt : {}", salesReceiptDTO);

        return salesReceiptRepository
            .findById(salesReceiptDTO.getId())
            .map(existingSalesReceipt -> {
                salesReceiptMapper.partialUpdate(existingSalesReceipt, salesReceiptDTO);

                return existingSalesReceipt;
            })
            .map(salesReceiptRepository::save)
            .map(savedSalesReceipt -> {
                salesReceiptSearchRepository.save(savedSalesReceipt);

                return savedSalesReceipt;
            })
            .map(salesReceiptMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SalesReceiptDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SalesReceipts");
        return salesReceiptRepository.findAll(pageable).map(salesReceiptMapper::toDto);
    }

    public Page<SalesReceiptDTO> findAllWithEagerRelationships(Pageable pageable) {
        return salesReceiptRepository.findAllWithEagerRelationships(pageable).map(salesReceiptMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SalesReceiptDTO> findOne(Long id) {
        log.debug("Request to get SalesReceipt : {}", id);
        return salesReceiptRepository.findOneWithEagerRelationships(id).map(salesReceiptMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SalesReceipt : {}", id);
        salesReceiptRepository.deleteById(id);
        salesReceiptSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SalesReceiptDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SalesReceipts for query {}", query);
        return salesReceiptSearchRepository.search(query, pageable).map(salesReceiptMapper::toDto);
    }
}
