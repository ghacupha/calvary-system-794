package io.github.calvary.repository.search;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import io.github.calvary.domain.EventType;
import io.github.calvary.repository.EventTypeRepository;
import java.util.List;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.elasticsearch.search.sort.SortBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring Data Elasticsearch repository for the {@link EventType} entity.
 */
public interface EventTypeSearchRepository extends ElasticsearchRepository<EventType, Long>, EventTypeSearchRepositoryInternal {}

interface EventTypeSearchRepositoryInternal {
    Page<EventType> search(String query, Pageable pageable);

    Page<EventType> search(Query query);

    void index(EventType entity);
}

class EventTypeSearchRepositoryInternalImpl implements EventTypeSearchRepositoryInternal {

    private final ElasticsearchRestTemplate elasticsearchTemplate;
    private final EventTypeRepository repository;

    EventTypeSearchRepositoryInternalImpl(ElasticsearchRestTemplate elasticsearchTemplate, EventTypeRepository repository) {
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.repository = repository;
    }

    @Override
    public Page<EventType> search(String query, Pageable pageable) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(queryStringQuery(query));
        return search(nativeSearchQuery.setPageable(pageable));
    }

    @Override
    public Page<EventType> search(Query query) {
        SearchHits<EventType> searchHits = elasticsearchTemplate.search(query, EventType.class);
        List<EventType> hits = searchHits.map(SearchHit::getContent).stream().collect(Collectors.toList());
        return new PageImpl<>(hits, query.getPageable(), searchHits.getTotalHits());
    }

    @Override
    public void index(EventType entity) {
        repository.findById(entity.getId()).ifPresent(elasticsearchTemplate::save);
    }
}
