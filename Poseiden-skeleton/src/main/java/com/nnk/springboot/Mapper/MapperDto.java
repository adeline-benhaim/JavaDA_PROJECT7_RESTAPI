package com.nnk.springboot.Mapper;

import com.nnk.springboot.domain.*;
import com.nnk.springboot.domain.Dto.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MapperDto {

    public static BidListDto convertToBidListDto(BidList bidList) {
        return BidListDto.builder()
                .bidListId(bidList.getBidListId())
                .account(bidList.getAccount())
                .type(bidList.getType())
                .bidQuantity(bidList.getBidQuantity())
                .build();
    }

    public static CurvePointDto convertToCurvePointDto(CurvePoint curvePoint) {
        return CurvePointDto.builder()
                .id(curvePoint.getId())
                .curveId(curvePoint.getCurveId())
                .term(curvePoint.getTerm())
                .value(curvePoint.getValue())
                .build();
    }

    public static RatingDto convertToRatingDto(Rating rating) {
        return RatingDto.builder()
                .id(rating.getId())
                .moodysRating(rating.getMoodysRating())
                .sandPRating(rating.getSandPRating())
                .fitchRating(rating.getFitchRating())
                .orderNumber(rating.getOrderNumber())
                .build();
    }

    public static RuleNameDto convertToRuleNameDto(RuleName ruleName) {
        return RuleNameDto.builder()
                .id(ruleName.getId())
                .name(ruleName.getName())
                .description(ruleName.getDescription())
                .json(ruleName.getJson())
                .template(ruleName.getTemplate())
                .sqlStr(ruleName.getSqlStr())
                .sqlPart(ruleName.getSqlPart())
                .build();
    }

    public static TradeDto convertToTradeDto(Trade trade) {
        return TradeDto.builder()
                .tradeId(trade.getTradeId())
                .account(trade.getAccount())
                .type(trade.getType())
                .buyQuantity(trade.getBuyQuantity())
                .build();
    }
}
