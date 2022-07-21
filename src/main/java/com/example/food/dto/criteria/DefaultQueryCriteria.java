package com.example.food.dto.criteria;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Data
public class DefaultQueryCriteria implements Serializable {
    public static final String SORT_DELIMITER = ":";
    public static final String SORT_DESC = "desc";

    @Schema(description = "Size mặc định là 25")
    private int size = 25;
    @Schema(description = "Page mặc định là 0")
    @Min(0)
    private int page = 0;
    @Schema(description = "Nếu k truyền sort thì mặc định là unsorted, nếu truyền vào 'sort=username:desc' hoặc 'sort=username:asc'")
    private String sort;

    @JsonIgnore
    public Sort getSortable() {
        Sort s = Sort.unsorted();

        if (StringUtils.hasText(sort)) {
            Set<String> sortParams = StringUtils.commaDelimitedListToSet(sort);
            List<Sort.Order> orders = new LinkedList<>();

            for (String it : sortParams) {
                String[] params = StringUtils.split(it, SORT_DELIMITER);
                if (params != null && params.length > 0) {
                    if (SORT_DESC.equalsIgnoreCase(params[1]))
                        orders.add(Sort.Order.desc(params[0]));
                    else
                        orders.add(Sort.Order.asc(params[0]));
                } else
                    orders.add(Sort.Order.asc(it));
            }
            if (!orders.isEmpty())
                s = Sort.by(orders);
        }
        return s;
    }
}