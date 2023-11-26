package io.github.calvary.service;

import io.github.calvary.domain.*; // for static metamodels
import io.github.calvary.domain.SalesReceipt;
import io.github.calvary.repository.SalesReceiptRepository;
import io.github.calvary.repository.search.SalesReceiptSearchRepository;
import io.github.calvary.service.criteria.SalesReceiptCriteria;
import io.github.calvary.service.dto.SalesReceiptDTO;
import io.github.calvary.service.mapper.SalesReceiptMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link SalesReceipt} entities in the database.
 * The main input is a {@link SalesReceiptCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SalesReceiptDTO} or a {@link Page} of {@link SalesReceiptDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SalesReceiptQueryService extends QueryService<SalesReceipt> {

    private final Logger log = LoggerFactory.getLogger(SalesReceiptQueryService.class);

    private final SalesReceiptRepository salesReceiptRepository;

    private final SalesReceiptMapper salesReceiptMapper;

    private final SalesReceiptSearchRepository salesReceiptSearchRepository;

    public SalesReceiptQueryService(
        SalesReceiptRepository salesReceiptRepository,
        SalesReceiptMapper salesReceiptMapper,
        SalesReceiptSearchRepository salesReceiptSearchRepository
    ) {
        this.salesReceiptRepository = salesReceiptRepository;
        this.salesReceiptMapper = salesReceiptMapper;
        this.salesReceiptSearchRepository = salesReceiptSearchRepository;
    }

    /**
     * Return a {@link List} of {@link SalesReceiptDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SalesReceiptDTO> findByCriteria(SalesReceiptCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SalesReceipt> specification = createSpecification(criteria);
        return salesReceiptMapper.toDto(salesReceiptRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SalesReceiptDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SalesReceiptDTO> findByCriteria(SalesReceiptCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SalesReceipt> specification = createSpecification(criteria);
        return salesReceiptRepository.findAll(specification, page).map(salesReceiptMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SalesReceiptCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SalesReceipt> specification = createSpecification(criteria);
        return salesReceiptRepository.count(specification);
    }

    /**
     * Function to convert {@link SalesReceiptCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SalesReceipt> createSpecification(SalesReceiptCriteria criteria) {
        Specification<SalesReceipt> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SalesReceipt_.id));
            }
            if (criteria.getSalesReceiptTitle() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getSalesReceiptTitle(), SalesReceipt_.salesReceiptTitle));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), SalesReceipt_.description));
            }
            if (criteria.getTransactionClassId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTransactionClassId(),
                            root -> root.join(SalesReceipt_.transactionClass, JoinType.LEFT).get(TransactionClass_.id)
                        )
                    );
            }
            if (criteria.getDealerId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getDealerId(), root -> root.join(SalesReceipt_.dealer, JoinType.LEFT).get(Dealer_.id))
                    );
            }
            if (criteria.getTransactionItemAmountId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTransactionItemAmountId(),
                            root -> root.join(SalesReceipt_.transactionItemAmounts, JoinType.LEFT).get(TransactionItemAmount_.id)
                        )
                    );
            }
        }
        return specification;
    }
}