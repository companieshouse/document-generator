package uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import uk.gov.companieshouse.api.model.accounts.smallfull.stocks.CurrentPeriod;
import uk.gov.companieshouse.api.model.accounts.smallfull.stocks.PreviousPeriod;
import uk.gov.companieshouse.document.generator.accounts.mapping.smallfull.model.ixbrl.stocks.StocksNote;

@Mapper
public interface ApiToStocksMapper {

    ApiToStocksMapper INSTANCE = Mappers.getMapper(ApiToStocksMapper.class);

    @Mappings({
            @Mapping(source = "stocksCurrentPeriod.stocks",
                    target = "stocks.currentAmount"),
            @Mapping(source = "stocksPreviousPeriod.stocks",
                    target = "stocks.previousAmount"),

            @Mapping(source = "stocksCurrentPeriod.paymentsOnAccount",
                    target = "paymentsOnAccount.currentAmount"),
            @Mapping(source = "stocksPreviousPeriod.paymentsOnAccount",
                    target = "paymentsOnAccount.previousAmount"),

            @Mapping(source = "stocksCurrentPeriod.total",
                    target = "total.currentAmount"),
            @Mapping(source = "stocksPreviousPeriod.total",
                    target = "total.previousAmount"),
    })
    StocksNote apiToStocks(CurrentPeriod stocksCurrentPeriod, PreviousPeriod stocksPreviousPeriod);

}
