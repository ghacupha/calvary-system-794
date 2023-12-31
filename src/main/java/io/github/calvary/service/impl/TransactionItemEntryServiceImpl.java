package io.github.calvary.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import io.github.calvary.domain.TransactionItemEntry;
import io.github.calvary.repository.TransactionItemEntryRepository;
import io.github.calvary.repository.search.TransactionItemEntrySearchRepository;
import io.github.calvary.service.TransactionItemEntryService;
import io.github.calvary.service.dto.TransactionItemEntryDTO;
import io.github.calvary.service.mapper.TransactionItemEntryMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TransactionItemEntry}.
 */
@Service
@Transactional
public class TransactionItemEntryServiceImpl implements TransactionItemEntryService {

    private final Logger log = LoggerFactory.getLogger(TransactionItemEntryServiceImpl.class);

    private final TransactionItemEntryRepository transactionItemEntryRepository;

    private final TransactionItemEntryMapper transactionItemEntryMapper;

    private final TransactionItemEntrySearchRepository transactionItemEntrySearchRepository;

    public TransactionItemEntryServiceImpl(
        TransactionItemEntryRepository transactionItemEntryRepository,
        TransactionItemEntryMapper transactionItemEntryMapper,
        TransactionItemEntrySearchRepository transactionItemEntrySearchRepository
    ) {
        this.transactionItemEntryRepository = transactionItemEntryRepository;
        this.transactionItemEntryMapper = transactionItemEntryMapper;
        this.transactionItemEntrySearchRepository = transactionItemEntrySearchRepository;
    }

    @Override
    public TransactionItemEntryDTO save(TransactionItemEntryDTO transactionItemEntryDTO) {
        log.debug("Request to save TransactionItemEntry : {}", transactionItemEntryDTO);
        TransactionItemEntry transactionItemEntry = transactionItemEntryMapper.toEntity(transactionItemEntryDTO);
        transactionItemEntry = transactionItemEntryRepository.save(transactionItemEntry);
        TransactionItemEntryDTO result = transactionItemEntryMapper.toDto(transactionItemEntry);
        transactionItemEntrySearchRepository.index(transactionItemEntry);
        return result;
    }

    @Override
    public TransactionItemEntryDTO update(TransactionItemEntryDTO transactionItemEntryDTO) {
        log.debug("Request to update TransactionItemEntry : {}", transactionItemEntryDTO);
        TransactionItemEntry transactionItemEntry = transactionItemEntryMapper.toEntity(transactionItemEntryDTO);
        transactionItemEntry = transactionItemEntryRepository.save(transactionItemEntry);
        TransactionItemEntryDTO result = transactionItemEntryMapper.toDto(transactionItemEntry);
        transactionItemEntrySearchRepository.index(transactionItemEntry);
        return result;
    }

    @Override
    public Optional<TransactionItemEntryDTO> partialUpdate(TransactionItemEntryDTO transactionItemEntryDTO) {
        log.debug("Request to partially update TransactionItemEntry : {}", transactionItemEntryDTO);

        return transactionItemEntryRepository
            .findById(transactionItemEntryDTO.getId())
            .map(existingTransactionItemEntry -> {
                transactionItemEntryMapper.partialUpdate(existingTransactionItemEntry, transactionItemEntryDTO);

                return existingTransactionItemEntry;
            })
            .map(transactionItemEntryRepository::save)
            .map(savedTransactionItemEntry -> {
                transactionItemEntrySearchRepository.save(savedTransactionItemEntry);

                return savedTransactionItemEntry;
            })
            .map(transactionItemEntryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransactionItemEntryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TransactionItemEntries");
        return transactionItemEntryRepository.findAll(pageable).map(transactionItemEntryMapper::toDto);
    }

    public Page<TransactionItemEntryDTO> findAllWithEagerRelationships(Pageable pageable) {
        return transactionItemEntryRepository.findAllWithEagerRelationships(pageable).map(transactionItemEntryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TransactionItemEntryDTO> findOne(Long id) {
        log.debug("Request to get TransactionItemEntry : {}", id);
        return transactionItemEntryRepository.findOneWithEagerRelationships(id).map(transactionItemEntryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TransactionItemEntry : {}", id);
        transactionItemEntryRepository.deleteById(id);
        transactionItemEntrySearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransactionItemEntryDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TransactionItemEntries for query {}", query);
        return transactionItemEntrySearchRepository.search(query, pageable).map(transactionItemEntryMapper::toDto);
    }
}
