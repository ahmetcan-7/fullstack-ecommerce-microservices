package com.ahmetcan7.productservice.enumeration;

import lombok.Getter;
import org.elasticsearch.search.sort.SortOrder;

@Getter
public enum Sort {
    DATE_DESC("createdDate", SortOrder.DESC),
    DATE_ASC("createdDate",SortOrder.ASC),
    PRICE_DESC("unitPrice",SortOrder.DESC),
    PRICE_ASC("unitPrice",SortOrder.ASC);

    private final String field;
    private final SortOrder order;
    Sort(String field,SortOrder order){
        this.field = field;
        this.order = order;
    }

}
