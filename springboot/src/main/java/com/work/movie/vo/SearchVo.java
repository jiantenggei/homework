package com.work.movie.vo;

import lombok.Data;

@Data
public class SearchVo {
    private Integer pageSize=8;
    private Integer pageNo=1;
    private String movieName;
}
