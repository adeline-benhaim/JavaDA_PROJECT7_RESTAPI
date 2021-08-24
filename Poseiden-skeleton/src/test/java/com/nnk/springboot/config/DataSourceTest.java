package com.nnk.springboot.config;

import com.nnk.springboot.domain.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class DataSourceTest {

    /**
     * Mock BidList
     */
    List<BidList> bidListListMocked = new ArrayList<>();

    public void clearBidListMocked() {
        bidListListMocked.clear();
    }

    public void createBidListMocked() {
        BidList bidList1 = BidList.builder().bidListId(1).account("account1").type("type1").bidQuantity(1d).build();
        BidList bidList2 = BidList.builder().bidListId(2).account("account2").type("type2").bidQuantity(2d).build();
        BidList bidList3 = BidList.builder().bidListId(3).account("account3").type("type3").bidQuantity(3d).build();
        bidListListMocked.addAll(Arrays.asList(bidList1, bidList2, bidList3));
    }

    /**
     * Mock CurvePoint
     */
    List<CurvePoint> curvePointListMocked = new ArrayList<>();

    public void clearCurvePointListMocked() {
        curvePointListMocked.clear();
    }

    public void createCurvePointListMocked() {
        CurvePoint curvePoint1 = CurvePoint.builder().id(1).curveId(1).term(1d).value(1d).build();
        CurvePoint curvePoint2 = CurvePoint.builder().id(2).curveId(2).term(2d).value(2d).build();
        CurvePoint curvePoint3 = CurvePoint.builder().id(3).curveId(3).term(3d).value(3d).build();
        curvePointListMocked.addAll(Arrays.asList(curvePoint1, curvePoint2, curvePoint3));
    }

    /**
     * Mock Rating
     */
    List<Rating> ratingListMocked = new ArrayList<>();

    public void clearRatingListMocked() {
        ratingListMocked.clear();
    }

    public void createRatingListMocked() {
        Rating rating1 = Rating.builder().id(1).moodysRating("moodysRating1").sandPRating("sandPRating1").fitchRating("fitchRating1").orderNumber(1).build();
        Rating rating2 = Rating.builder().id(2).moodysRating("moodysRating2").sandPRating("sandPRating2").fitchRating("fitchRating2").orderNumber(2).build();
        Rating rating3 = Rating.builder().id(3).moodysRating("moodysRating3").sandPRating("sandPRating3").fitchRating("fitchRating3").orderNumber(3).build();
        ratingListMocked.addAll(Arrays.asList(rating1, rating2, rating3));
    }

    /**
     * Mock RuleName
     */
    List<RuleName> ruleNameListMocked = new ArrayList<>();

    public void clearRuleNameMocked() {
        ruleNameListMocked.clear();
    }

    public void createRuleNameListMocked() {
        RuleName ruleName1 = RuleName.builder().id(1).name("name1").description("description1").json("json1").template("template1").sqlStr("sqlStr1").sqlPart("sqlPart1").build();
        RuleName ruleName2 = RuleName.builder().id(2).name("name2").description("description2").json("json2").template("template2").sqlStr("sqlStr2").sqlPart("sqlPart2").build();
        RuleName ruleName3 = RuleName.builder().id(3).name("name3").description("description3").json("json3").template("template3").sqlStr("sqlStr3").sqlPart("sqlPart3").build();
        ruleNameListMocked.addAll(Arrays.asList(ruleName1, ruleName2, ruleName3));
    }

    /**
     * Mock Trade
     */
    List<Trade> tradeListMocked = new ArrayList<>();

    public void clearTradeListMocked() {
        tradeListMocked.clear();
    }

    public void createTradeListMocked () {
        Trade trade1 = Trade.builder().tradeId(1).account("account1").type("type1").buyQuantity(1d).build();
        Trade trade2 = Trade.builder().tradeId(2).account("account2").type("type2").buyQuantity(2d).build();
        Trade trade3 = Trade.builder().tradeId(3).account("account3").type("type3").buyQuantity(3d).build();
        tradeListMocked.addAll(Arrays.asList(trade1, trade2, trade3));
    }

}
