package io.github.calvary.service.mapper;

import io.github.calvary.domain.SalesReceipt;
import io.github.calvary.domain.TransactionItem;
import io.github.calvary.domain.TransactionItemEntry;
import io.github.calvary.service.dto.SalesReceiptDTO;
import io.github.calvary.service.dto.TransactionItemDTO;
import io.github.calvary.service.dto.TransactionItemEntryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TransactionItemEntry} and its DTO {@link TransactionItemEntryDTO}.
 */
@Mapper(componentModel = "spring")
public interface TransactionItemEntryMapper extends EntityMapper<TransactionItemEntryDTO, TransactionItemEntry> {
    @Mapping(target = "transactionItem", source = "transactionItem", qualifiedByName = "transactionItemItemName")
    @Mapping(target = "salesReceipt", source = "salesReceipt", qualifiedByName = "salesReceiptId")
    TransactionItemEntryDTO toDto(TransactionItemEntry s);

    @Named("transactionItemItemName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "itemName", source = "itemName")
    TransactionItemDTO toDtoTransactionItemItemName(TransactionItem transactionItem);

    @Named("salesReceiptId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SalesReceiptDTO toDtoSalesReceiptId(SalesReceipt salesReceipt);
}
