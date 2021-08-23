package com.nnk.springboot.Mapper;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.Dto.BidListDto;
import com.nnk.springboot.domain.Dto.CurvePointDto;
import com.nnk.springboot.domain.Dto.RatingDto;
import com.nnk.springboot.domain.Rating;
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
}
